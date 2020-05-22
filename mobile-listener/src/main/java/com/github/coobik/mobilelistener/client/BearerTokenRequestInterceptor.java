package com.github.coobik.mobilelistener.client;

import org.springframework.http.HttpHeaders;

import feign.RequestInterceptor;
import feign.RequestTemplate;


public class BearerTokenRequestInterceptor implements RequestInterceptor {

  private static final String BEARER_PREFIX = "Bearer ";

  private final String fullAuthToken;

  public BearerTokenRequestInterceptor(String authToken) {
    this.fullAuthToken = BEARER_PREFIX + authToken;
  }

  @Override
  public void apply(RequestTemplate template) {
    template.header(HttpHeaders.AUTHORIZATION, fullAuthToken);
  }

}
