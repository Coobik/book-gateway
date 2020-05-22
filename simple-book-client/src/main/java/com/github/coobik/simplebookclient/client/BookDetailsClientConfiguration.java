package com.github.coobik.simplebookclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;


/**
 * Configuration for {@link BookDetailsClient} - NOT annotated with {@link Configuration} and not
 * loaded at start up
 */
public class BookDetailsClientConfiguration {

  @Value("${bookdetails.auth.token}")
  private String bookDetailsAuthToken;

  @Bean
  public RequestInterceptor bookDetailsAuthRequestInterceptor() {
    return new BearerTokenRequestInterceptor(bookDetailsAuthToken);
  }

}
