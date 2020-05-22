package com.github.coobik.gateway.route;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "authors.url=http://localhost:${wiremock.server.port}",
        "books.url=http://localhost:${wiremock.server.port}",
        "bookdetails.url=http://localhost:${wiremock.server.port}"
    })
@AutoConfigureWireMock(port = 0)
public class RouteConfigurationTest {

  private static final String JSON_AUTHORS = "[{\"name\":\"wednesday author\"}]";
  private static final String JSON_BOOKS = "[{\"title\":\"wednesday book\"}]";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private AuthTokenProvider authTokenProvider;

  @Test
  public void testAuthorsRoute() {
    RouteTestUtils.stubGetEndpoint(
        RouteConfiguration.PATH_API_V1_AUTHORS,
        JSON_AUTHORS);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_AUTHORS,
        JSON_AUTHORS,
        authTokenProvider.getAuthToken());

    RouteTestUtils.checkUnauthorized(
        webTestClient,
        RouteConfiguration.PATH_API_V1_AUTHORS);

    RouteTestUtils.checkForbidden(
        webTestClient,
        RouteConfiguration.PATH_API_V1_AUTHORS);
  }

  @Test
  public void testBooksRoute() {
    RouteTestUtils.stubGetEndpoint(
        RouteConfiguration.PATH_API_V1_BOOKS,
        JSON_BOOKS);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_BOOKS,
        JSON_BOOKS,
        authTokenProvider.getAuthToken());

    RouteTestUtils.checkUnauthorized(
        webTestClient,
        RouteConfiguration.PATH_API_V1_BOOKS);

    RouteTestUtils.checkForbidden(
        webTestClient,
        RouteConfiguration.PATH_API_V1_BOOKS);
  }

  @Test
  public void testDetailsRoute() {
    RouteTestUtils.stubGetEndpoint(
        RouteConfiguration.PATH_API_V1_DETAILS,
        JSON_BOOKS);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_API_V1_DETAILS,
        JSON_BOOKS,
        authTokenProvider.getAuthToken());

    RouteTestUtils.checkUnauthorized(
        webTestClient,
        RouteConfiguration.PATH_API_V1_DETAILS);

    RouteTestUtils.checkForbidden(
        webTestClient,
        RouteConfiguration.PATH_API_V1_DETAILS);
  }

  @Test
  public void testHealthRoutes() {
    RouteTestUtils.stubGetEndpoint(
        RouteConfiguration.PATH_ACTUATOR_HEALTH,
        RouteTestUtils.STATUS_UP);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_HEALTH_AUTHORS,
        RouteTestUtils.STATUS_UP);

    RouteTestUtils.checkGetRoute(
        webTestClient,
        RouteConfiguration.PATH_HEALTH_BOOKS,
        RouteTestUtils.STATUS_UP);
  }

}
