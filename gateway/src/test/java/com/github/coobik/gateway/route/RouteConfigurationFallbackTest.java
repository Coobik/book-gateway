package com.github.coobik.gateway.route;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "authors.url=http://localhost:1",
        "books.url=http://localhost:1",
        "bookdetails.url=http://localhost:1"
    })
public class RouteConfigurationFallbackTest {

  private static final String JSON_EMPTY = "[]";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private AuthTokenProvider authTokenProvider;

  @Test
  public void testAuthorsRouteFallback() {
    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_AUTHORS,
        JSON_EMPTY,
        authTokenProvider.getAuthToken());
  }

  @Test
  public void testBooksRouteFallback() {
    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_BOOKS,
        JSON_EMPTY,
        authTokenProvider.getAuthToken());
  }

  @Test
  public void testDetailsRouteFallback() {
    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_DETAILS,
        JSON_EMPTY,
        authTokenProvider.getAuthToken());
  }

  @Test
  public void testHealthRoutesFallback() {
    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_HEALTH_AUTHORS,
        RouteTestUtils.STATUS_DOWN);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_HEALTH_BOOKS,
        RouteTestUtils.STATUS_DOWN);
  }

}
