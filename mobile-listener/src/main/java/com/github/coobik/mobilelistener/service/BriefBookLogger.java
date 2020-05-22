package com.github.coobik.mobilelistener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.github.coobik.mobilelistener.model.BookBrief;
import com.github.coobik.mobilelistener.model.BookDetails;


@Service
@ConditionalOnProperty(prefix = "books", name = "mode", havingValue = "brief")
public class BriefBookLogger extends BookLogger.BookLoggerBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(BriefBookLogger.class);

  @Override
  protected Logger getLogger() {
    return LOGGER;
  }

  @Override
  protected String bookToString(BookDetails bookDetails) {
    BookBrief bookBrief = bookDetails.toBrief();
    return toJson(bookBrief);
  }

}
