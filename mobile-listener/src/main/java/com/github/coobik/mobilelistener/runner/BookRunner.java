package com.github.coobik.mobilelistener.runner;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.github.coobik.mobilelistener.model.BookDetails;
import com.github.coobik.mobilelistener.service.BookLogger;
import com.github.coobik.mobilelistener.service.BookService;


@Component
public class BookRunner implements ApplicationRunner {

  private static final String OPTION_ALL_BOOKS = "all-books";
  private static final String OPTION_BOOK_ID = "book-id";

  private static final Logger LOGGER = LoggerFactory.getLogger(BookRunner.class);

  @Autowired
  private BookService bookService;

  @Autowired
  private BookLogger bookLogger;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (args.containsOption(OPTION_ALL_BOOKS)) {
      fetchAllBooks();
    }

    List<String> bookIds = args.getOptionValues(OPTION_BOOK_ID);
    fetchBooks(bookIds);
  }

  private void fetchBooks(List<String> bookIds) {
    if (CollectionUtils.isEmpty(bookIds)) {
      return;
    }

    LOGGER.info("fetching books by IDs...");

    bookIds.stream().forEach(this::fetchBook);
  }

  private void fetchBook(String bookId) {
    if (StringUtils.isBlank(bookId)) {
      return;
    }

    try {
      BookDetails bookDetails = bookService.getBookDetails(UUID.fromString(bookId));
      logBook(bookDetails);
    }
    catch (Exception ex) {
      LOGGER.error("error fetching book: {} : {}", bookId, ex.getMessage());
    }
  }

  private void logBook(BookDetails bookDetails) {
    bookLogger.logBook(bookDetails);
  }

  private void fetchAllBooks() {
    LOGGER.info("fetching all books...");

    // List<BookDetails> bookDetailsList = bookService.getBookDetailsList();
    List<BookDetails> bookDetailsList = bookService.getBookDetailsListV2();

    if (CollectionUtils.isEmpty(bookDetailsList)) {
      LOGGER.info("no books fetched");
      return;
    }

    bookDetailsList.stream().forEach(this::logBook);
    LOGGER.info("{} books fetched", bookDetailsList.size());
  }

}
