package com.github.coobik.simplebookclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;


/**
 * Configuration for {@link BookBriefClient} - NOT annotated with {@link Configuration} and not
 * loaded at start up
 */
public class BookBriefClientConfiguration {

  @Value("${bookbrief.auth.token}")
  private String bookBriefAuthToken;

  @Bean
  public RequestInterceptor bookBriefAuthRequestInterceptor() {
    return new BearerTokenRequestInterceptor(bookBriefAuthToken);
  }

}
