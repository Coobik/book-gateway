package com.github.coobik.gateway.filter;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Component
public class AuthorizationBearerFilter implements GatewayFilter {

  public static final String BEARER_PREFIX = "Bearer ";

  @Value("${auth.tokens}")
  private Set<String> authTokens;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (CollectionUtils.isEmpty(authTokens)) {
      return chain.filter(exchange);
    }

    String bearerToken = extractBearerToken(exchange);

    if (bearerToken == null) {
      return setUnauthorizedResponse(exchange);
    }

    if (!isTokenValid(bearerToken)) {
      return setForbiddenResponse(exchange);
    }

    return chain.filter(exchange);
  }

  private boolean isTokenValid(String bearerToken) {
    return authTokens.contains(bearerToken);
  }

  public static String extractBearerToken(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

    if (CollectionUtils.isEmpty(authHeaders)) {
      return null;
    }

    String authHeader = authHeaders.get(0);

    if (StringUtils.isBlank(authHeader)) {
      return null;
    }

    if (!authHeader.startsWith(BEARER_PREFIX)) {
      return null;
    }

    String bearerToken = authHeader.substring(BEARER_PREFIX.length());

    if (StringUtils.isBlank(bearerToken)) {
      return null;
    }

    return bearerToken.trim();
  }

  private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange) {
    return completeResponse(exchange, HttpStatus.UNAUTHORIZED);
  }

  private Mono<Void> setForbiddenResponse(ServerWebExchange exchange) {
    return completeResponse(exchange, HttpStatus.FORBIDDEN);
  }

  private Mono<Void> completeResponse(ServerWebExchange exchange, HttpStatus status) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(status);

    return response.setComplete();
  }

}
