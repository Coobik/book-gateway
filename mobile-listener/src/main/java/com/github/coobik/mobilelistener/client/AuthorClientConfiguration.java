package com.github.coobik.mobilelistener.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;


/**
 * Configuration for {@link AuthorClient} - NOT annotated with {@link Configuration} and not loaded
 * at start up
 */
public class AuthorClientConfiguration {

  @Value("${authors.auth.token}")
  private String authorsAuthToken;

  @Bean
  public RequestInterceptor authorAuthRequestInterceptor() {
    return new BearerTokenRequestInterceptor(authorsAuthToken);
  }

}
