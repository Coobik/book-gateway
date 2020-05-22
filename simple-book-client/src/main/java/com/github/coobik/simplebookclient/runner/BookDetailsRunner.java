package com.github.coobik.simplebookclient.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.coobik.simplebookclient.client.BookDetailsClient;
import com.github.coobik.simplebookclient.model.BookDetails;
import com.github.coobik.simplebookclient.service.BookLogger;


@Component
public class BookDetailsRunner implements ApplicationRunner {

  private static final String OPTION_FULL = "full";

  private static final Logger LOGGER = LoggerFactory.getLogger(BookDetailsRunner.class);

  @Autowired
  private BookDetailsClient client;

  @Autowired
  private BookLogger bookLogger;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!args.containsOption(OPTION_FULL)) {
      return;
    }

    LOGGER.info("loading books with details...");

    List<BookDetails> booksWithDetails = client.getBooksWithDetails();

    if (CollectionUtils.isEmpty(booksWithDetails)) {
      LOGGER.info("no books loaded");
      return;
    }

    bookLogger.logBooks(booksWithDetails);
  }

}
