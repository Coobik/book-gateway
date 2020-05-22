package com.github.coobik.simplebookclient.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class BookLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookLogger.class);

  @Autowired
  private ObjectMapper objectMapper;

  public void logBooks(Collection<?> books) {
    if (StringUtils.isEmpty(books)) {
      return;
    }

    books.stream().forEach(this::logBook);
    LOGGER.info("{} books loaded", books.size());
  }

  public void logBook(Object book) {
    if (book == null) {
      return;
    }

    String bookAsString = toJson(book);
    LOGGER.info("book: {}", bookAsString);
  }

  private String toJson(Object value) {
    try {
      return objectMapper.writeValueAsString(value);
    }
    catch (JsonProcessingException ex) {
      LOGGER.error("error writing json: {}", ex.getMessage());
      return "";
    }
  }

}
