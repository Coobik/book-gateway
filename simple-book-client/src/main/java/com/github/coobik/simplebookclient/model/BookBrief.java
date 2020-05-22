package com.github.coobik.simplebookclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BookBrief {

  private String bookTitle;
  private String authorName;

  public BookBrief() {}

  public BookBrief(String bookTitle, String authorName) {
    this.bookTitle = bookTitle;
    this.authorName = authorName;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

}
