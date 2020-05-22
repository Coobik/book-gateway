package com.github.coobik.simplebookclient.runner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.coobik.simplebookclient.client.BookBriefClient;
import com.github.coobik.simplebookclient.model.BookBrief;
import com.github.coobik.simplebookclient.service.BookLogger;


@Component
public class BookBriefRunner implements ApplicationRunner {

  private static final String OPTION_BRIEF = "brief";

  private static final Logger LOGGER = LoggerFactory.getLogger(BookBriefRunner.class);

  @Autowired
  private BookBriefClient client;

  @Autowired
  private BookLogger bookLogger;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!args.containsOption(OPTION_BRIEF)) {
      return;
    }

    LOGGER.info("loading books with brief info...");

    List<BookBrief> booksBrief = client.getBooksBrief();

    if (CollectionUtils.isEmpty(booksBrief)) {
      LOGGER.info("no books loaded");
      return;
    }

    bookLogger.logBooks(booksBrief);
  }

}
