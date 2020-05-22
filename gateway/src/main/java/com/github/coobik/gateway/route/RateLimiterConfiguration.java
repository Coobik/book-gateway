package com.github.coobik.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.coobik.gateway.filter.AuthorizationBearerFilter;

import reactor.core.publisher.Mono;


@Configuration
public class RateLimiterConfiguration {

  private static final String KEY = "key";

  @Value("${redis.limit.replenishRate}")
  private int replenishRate;

  @Value("${redis.limit.burstCapacity}")
  private int burstCapacity;

  @Bean
  public RedisRateLimiter redisRateLimiter() {
    return new RedisRateLimiter(replenishRate, burstCapacity);
  }

  @Bean
  public KeyResolver keyResolver() {
    // return new PrincipalNameKeyResolver();
    // return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("api_key"));
    // return exchange ->
    // Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());

    return exchange -> {
      String bearerToken = AuthorizationBearerFilter.extractBearerToken(exchange);

      if (bearerToken == null) {
        // same key for all requests - general limit for all
        return Mono.just(KEY);
      }

      // limit per bearer token
      return Mono.just(bearerToken);
    };
  }

}
