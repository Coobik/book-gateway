package com.github.coobik.mobilelistener.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.coobik.mobilelistener.model.BookDetails;


public interface BookLogger {

  void logBook(BookDetails bookDetails);

  void logBook(BookDetails bookDetails, String messagePrefix);

  public static abstract class BookLoggerBase implements BookLogger {

    @Autowired
    private ObjectMapper objectMapper;

    protected abstract Logger getLogger();

    @Override
    public void logBook(BookDetails bookDetails) {
      logBook(bookDetails, "Book");
    }

    @Override
    public void logBook(BookDetails bookDetails, String messagePrefix) {
      if (bookDetails == null) {
        return;
      }

      String bookAsString = bookToString(bookDetails);
      getLogger().info("{}: {}", messagePrefix, bookAsString);
    }

    protected String bookToString(BookDetails bookDetails) {
      return toJson(bookDetails);
    }

    protected String toJson(Object value) {
      try {
        return objectMapper.writeValueAsString(value);
      }
      catch (JsonProcessingException ex) {
        getLogger().error("error writing json: {}", ex.getMessage());
        return "";
      }
    }

  }

}
