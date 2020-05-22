package com.github.coobik.mobilelistener.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.coobik.mobilelistener.client.BookClient;
import com.github.coobik.mobilelistener.client.BookDetailsClient;
import com.github.coobik.mobilelistener.model.AuthorResponse;
import com.github.coobik.mobilelistener.model.BookBrief;
import com.github.coobik.mobilelistener.model.BookDetails;
import com.github.coobik.mobilelistener.model.BookResponse;


@Service
public class BookService {

  @Autowired
  private AuthorService authorService;

  @Autowired
  private BookClient bookClient;

  @Autowired
  private BookDetailsClient bookDetailsClient;

  public List<BookDetails> getBookDetailsListV2() {
    List<BookDetails> booksWithDetails = bookDetailsClient.getBooksWithDetails();

    return booksWithDetails;
  }

  public List<BookDetails> getBookDetailsList() {
    List<BookResponse> books = bookClient.getBooks();

    if (CollectionUtils.isEmpty(books)) {
      return Collections.emptyList();
    }

    return books
        .stream()
        .map(bookResponse -> getBookDetails(bookResponse))
        .collect(Collectors.toList());
  }

  public List<BookBrief> getBookBriefList() {
    List<BookDetails> bookDetailsList = getBookDetailsList();

    if (CollectionUtils.isEmpty(bookDetailsList)) {
      return Collections.emptyList();
    }

    List<BookBrief> bookBriefList =
        bookDetailsList
            .stream()
            .map(bookDetails -> bookDetails.toBrief())
            .collect(Collectors.toList());

    return bookBriefList;
  }

  public BookDetails getBookDetails(UUID bookId) {
    if (bookId == null) {
      return null;
    }

    BookResponse book = bookClient.getBook(bookId);

    return getBookDetails(book);
  }

  public BookDetails getBookDetails(BookResponse book) {
    if (book == null) {
      return null;
    }

    AuthorResponse author = getAuthorFromBook(book);

    return new BookDetails(book, author);
  }

  public BookBrief getBookBrief(UUID bookId) {
    if (bookId == null) {
      return null;
    }

    BookDetails bookDetails = getBookDetails(bookId);

    if (bookDetails == null) {
      return null;
    }

    return bookDetails.toBrief();
  }

  public AuthorResponse getAuthorFromBook(BookResponse book) {
    if (book == null) {
      return null;
    }

    UUID authorId = book.getAuthorId();

    if (authorId == null) {
      return null;
    }

    AuthorResponse author = authorService.getAuthor(authorId);

    return author;
  }

}
