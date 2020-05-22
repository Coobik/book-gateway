package com.github.coobik.briefaggregator.client;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.coobik.briefaggregator.model.AuthorResponse;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(
    name = "authors",
    url = "${authors.url}",
    path = "/api/v1/authors",
    fallback = AuthorClientFallback.class,
    decode404 = true)
public interface AuthorClient {

  @GetMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  Flux<AuthorResponse> getAuthors(@RequestParam(name = "ids", required = false) List<String> ids);

  @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  Mono<AuthorResponse> getAuthor(@PathVariable("id") UUID id);

}
