package com.github.coobik.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.github.coobik.gateway.filter.AuthorizationBearerFilter;


@Configuration
public class RouteConfiguration {

  private static final String FORWARD_FALLBACK = "forward:/fallback";
  private static final String FORWARD_FALLBACK_HEALTH = "forward:/fallback/health";

  public static final String PATH_API_V1_AUTHORS = "/api/v1/authors/**";
  public static final String PATH_API_V1_BOOKS = "/api/v1/books/**";
  public static final String PATH_API_V1_DETAILS = "/api/v1/details/**";

  public static final String PATH_HEALTH_AUTHORS = "/health/authors";
  public static final String PATH_HEALTH_BOOKS = "/health/books";

  public static final String PATH_ACTUATOR_HEALTH = "/actuator/health";

  @Value("${authors.url}")
  private String authorsUrl;

  @Value("${books.url}")
  private String booksUrl;

  @Value("${bookdetails.url}")
  private String bookdetailsUrl;

  @Value("${websocket.url}")
  private String websocketUrl;

  @Value("${websocket.sockjs.url}")
  private String websocketSockJsUrl;

  @Value("${websocket.path}")
  private String websocketPath;

  @Autowired
  private RedisRateLimiter redisRateLimiter;

  @Autowired
  private KeyResolver keyResolver;

  @Autowired
  private AuthorizationBearerFilter authorizationBearerFilter;

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsWebFilter(corsConfigurationSource);
  }

  @Bean
  @Autowired
  public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
    RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();

    routesBuilder = buildHealthRoutes(routesBuilder);
    routesBuilder = buildResourceRoutes(routesBuilder);
    routesBuilder = buildWebSocketRoutes(routesBuilder);
    routesBuilder = buildChatRoutes(routesBuilder);

    return routesBuilder.build();
  }

  private RouteLocatorBuilder.Builder buildResourceRoutes(
      RouteLocatorBuilder.Builder routesBuilder) {
    return routesBuilder
        .route(p -> p
            .path(PATH_API_V1_DETAILS)
            .filters(f -> f
                .requestRateLimiter(config -> config
                    .setRateLimiter(redisRateLimiter)
                    .setKeyResolver(keyResolver))
                .filter(authorizationBearerFilter)
                .hystrix(config -> config.setFallbackUri(FORWARD_FALLBACK)))
            .uri(bookdetailsUrl))
        .route(p -> p
            .path(PATH_API_V1_AUTHORS)
            .filters(f -> f
                .requestRateLimiter(config -> config
                    .setRateLimiter(redisRateLimiter)
                    .setKeyResolver(keyResolver))
                .filter(authorizationBearerFilter)
                .hystrix(config -> config.setFallbackUri(FORWARD_FALLBACK)))
            .uri(authorsUrl))
        .route(p -> p
            .path(PATH_API_V1_BOOKS)
            .filters(f -> f
                .requestRateLimiter(config -> config
                    .setRateLimiter(redisRateLimiter)
                    .setKeyResolver(keyResolver))
                .filter(authorizationBearerFilter)
                .hystrix(config -> config.setFallbackUri(FORWARD_FALLBACK)))
            .uri(booksUrl));
  }

  private RouteLocatorBuilder.Builder buildHealthRoutes(RouteLocatorBuilder.Builder routesBuilder) {
    return routesBuilder
        .route(p -> p
            .path(PATH_HEALTH_AUTHORS)
            .filters(f -> f
                .rewritePath(PATH_HEALTH_AUTHORS, PATH_ACTUATOR_HEALTH)
                .requestRateLimiter(config -> config
                    .setRateLimiter(redisRateLimiter)
                    .setKeyResolver(keyResolver))
                .hystrix(config -> config.setFallbackUri(FORWARD_FALLBACK_HEALTH)))
            .uri(authorsUrl))
        .route(p -> p
            .path(PATH_HEALTH_BOOKS)
            .filters(f -> f
                .rewritePath(PATH_HEALTH_BOOKS, PATH_ACTUATOR_HEALTH)
                .requestRateLimiter(config -> config
                    .setRateLimiter(redisRateLimiter)
                    .setKeyResolver(keyResolver))
                .hystrix(config -> config.setFallbackUri(FORWARD_FALLBACK_HEALTH)))
            .uri(booksUrl));
  }

  private RouteLocatorBuilder.Builder buildWebSocketRoutes(
      RouteLocatorBuilder.Builder routesBuilder) {
    // sockjs must go BEFORE the normal web socket
    return routesBuilder
        .route("websocket_sockjs_route",
            p -> p
                .path(websocketPath + "/info/**")
                .uri(websocketSockJsUrl))
        .route("websocket_route",
            p -> p
                .path(websocketPath + "/**")
                .uri(websocketUrl));
  }

  private RouteLocatorBuilder.Builder buildChatRoutes(RouteLocatorBuilder.Builder routesBuilder) {
    return routesBuilder
        .route("chat_route",
            p -> p
                .path("/chat.html")
                .uri(websocketSockJsUrl))
        .route("js_route",
            p -> p
                .path("/js/**")
                .uri(websocketSockJsUrl));
  }

}
