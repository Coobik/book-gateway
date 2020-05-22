package com.github.coobik.mobilelistener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(prefix = "books", name = "mode", havingValue = "full")
public class FullBookLogger extends BookLogger.BookLoggerBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(FullBookLogger.class);

  @Override
  protected Logger getLogger() {
    return LOGGER;
  }

}
