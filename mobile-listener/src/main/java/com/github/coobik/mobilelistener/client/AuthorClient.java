package com.github.coobik.mobilelistener.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.coobik.mobilelistener.model.AuthorResponse;


@FeignClient(
    name = "authors",
    url = "${authors.url}",
    path = "/api/v1/authors",
    configuration = AuthorClientConfiguration.class,
    decode404 = true)
public interface AuthorClient {

  @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  AuthorResponse getAuthor(@PathVariable("id") UUID id);

}
