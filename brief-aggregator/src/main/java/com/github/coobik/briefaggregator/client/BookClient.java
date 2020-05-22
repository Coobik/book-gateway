package com.github.coobik.briefaggregator.client;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.coobik.briefaggregator.model.BookResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(
    name = "books",
    url = "${books.url}",
    path = "/api/v1/books",
    fallback = BookClientFallback.class,
    decode404 = true)
public interface BookClient {

  @GetMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  Flux<BookResponse> getBooks();

  @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  Mono<BookResponse> getBook(@PathVariable("id") UUID id);

}
