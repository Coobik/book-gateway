package com.github.coobik.briefaggregator.client;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.coobik.briefaggregator.model.AuthorResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class AuthorClientFallback implements AuthorClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorClientFallback.class);

  @Override
  public Flux<AuthorResponse> getAuthors(List<String> ids) {
    LOGGER.warn("cannot get authors: {}", ids);
    return Flux.empty();
  }

  @Override
  public Mono<AuthorResponse> getAuthor(UUID id) {
    LOGGER.warn("cannot get author: {}", id);
    return Mono.empty();
  }

}
