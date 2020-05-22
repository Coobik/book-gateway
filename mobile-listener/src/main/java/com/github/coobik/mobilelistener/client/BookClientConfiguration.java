package com.github.coobik.mobilelistener.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;


/**
 * Configuration for {@link BookClient} - NOT annotated with {@link Configuration} and not loaded at
 * start up
 */
public class BookClientConfiguration {

  @Value("${books.auth.token}")
  private String booksAuthToken;

  @Bean
  public RequestInterceptor bookAuthRequestInterceptor() {
    return new BearerTokenRequestInterceptor(booksAuthToken);
  }

}
