package com.github.coobik.mobilelistener.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.coobik.mobilelistener.model.BookResponse;


@FeignClient(
    name = "books",
    url = "${books.url}",
    path = "/api/v1/books",
    configuration = BookClientConfiguration.class,
    decode404 = true)
public interface BookClient {

  @GetMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  List<BookResponse> getBooks();

  @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  BookResponse getBook(@PathVariable("id") UUID id);

}
