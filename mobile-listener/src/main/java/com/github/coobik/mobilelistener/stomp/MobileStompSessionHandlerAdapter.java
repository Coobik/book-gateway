package com.github.coobik.mobilelistener.stomp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.coobik.mobilelistener.model.BookDetails;
import com.github.coobik.mobilelistener.model.BookResponse;
import com.github.coobik.mobilelistener.service.BookLogger;
import com.github.coobik.mobilelistener.service.BookService;


@Component
@ConditionalOnProperty(prefix = "websocket.client", name = "enabled", havingValue = "true")
public class MobileStompSessionHandlerAdapter extends StompSessionHandlerAdapter {

  private static final String TOPIC_MESSAGES = "/topic/messages";

  private static final Logger LOGGER =
      LoggerFactory.getLogger(MobileStompSessionHandlerAdapter.class);

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookService bookService;

  @Autowired
  private BookLogger bookLogger;

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    session.subscribe(TOPIC_MESSAGES, this);

    LOGGER.info("subscribed to: {}", TOPIC_MESSAGES);
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    if (payload == null) {
      return;
    }

    String payloadAsString = (String) payload;

    if (isBookPayload(payloadAsString)) {
      handleBookPayload(payloadAsString);
      return;
    }

    LOGGER.info("new author: {}", payload);
  }

  private boolean isBookPayload(String payloadAsString) {
    return payloadAsString.contains("authorId");
  }

  private void handleBookPayload(String bookPayload) {
    BookResponse book = readBookPayload(bookPayload);

    if (book == null) {
      return;
    }

    BookDetails bookDetails = bookService.getBookDetails(book);
    bookLogger.logBook(bookDetails, "new book");
  }

  private BookResponse readBookPayload(String bookPayload) {
    try {
      return objectMapper.readValue(bookPayload, BookResponse.class);
    }
    catch (IOException ex) {
      LOGGER.error("cannot read book from: {}", bookPayload);
      return null;
    }
  }

}
