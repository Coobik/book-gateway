package com.github.coobik.briefaggregator.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.coobik.briefaggregator.model.BookResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class BookClientFallback implements BookClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookClientFallback.class);

  @Override
  public Flux<BookResponse> getBooks() {
    LOGGER.warn("cannot get books");
    return Flux.empty();
  }

  @Override
  public Mono<BookResponse> getBook(UUID id) {
    LOGGER.warn("cannot get book: {}", id);
    return Mono.empty();
  }

}
