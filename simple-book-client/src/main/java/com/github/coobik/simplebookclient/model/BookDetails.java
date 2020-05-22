package com.github.coobik.simplebookclient.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDetails {

  private BookResponse book;
  private AuthorResponse author;

  public BookDetails() {}

  public BookDetails(BookResponse book, AuthorResponse author) {
    this.book = book;
    this.author = author;
  }

  public BookResponse getBook() {
    return book;
  }

  public void setBook(BookResponse book) {
    this.book = book;
  }

  public AuthorResponse getAuthor() {
    return author;
  }

  public void setAuthor(AuthorResponse author) {
    this.author = author;
  }

  @JsonIgnore
  public BookBrief toBrief() {
    String bookTitle = getBookTitle();
    String authorName = getAuthorName();

    return new BookBrief(bookTitle, authorName);
  }

  @JsonIgnore
  public String getBookTitle() {
    if (book == null) {
      return null;
    }

    return book.getTitle();
  }

  @JsonIgnore
  public String getAuthorName() {
    if (author == null) {
      return null;
    }

    String firstName = author.getFirstName();
    String lastName = author.getLastName();

    StringBuilder nameBuilder = new StringBuilder();

    if (StringUtils.isNotBlank(firstName)) {
      nameBuilder.append(firstName).append(' ');
    }

    if (StringUtils.isNotBlank(lastName)) {
      nameBuilder.append(lastName);
    }

    return nameBuilder.toString();
  }

}
