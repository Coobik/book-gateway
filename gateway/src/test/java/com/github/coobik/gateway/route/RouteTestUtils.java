package com.github.coobik.gateway.route;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.coobik.gateway.filter.AuthorizationBearerFilter;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;


public abstract class RouteTestUtils {

  public static final String STATUS_UP = "{\"status\":\"UP\"}";
  public static final String STATUS_DOWN = "{\"status\":\"DOWN\"}";

  private RouteTestUtils() {
    // static class
  }

  public static void checkGetRoute(WebTestClient webTestClient, String uri, String expectedJson) {
    webTestClient
        .get().uri(uri)
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(expectedJson);
  }

  public static void checkGetRoute(
      WebTestClient webTestClient,
      String uri,
      String expectedJson,
      String bearerToken) {
    if (bearerToken == null) {
      checkGetRoute(webTestClient, uri, expectedJson);
      return;
    }

    webTestClient
        .get().uri(uri)
        .header(HttpHeaders.AUTHORIZATION, AuthorizationBearerFilter.BEARER_PREFIX + bearerToken)
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(expectedJson);
  }

  public static void checkUnauthorized(WebTestClient webTestClient, String uri) {
    webTestClient
        .get().uri(uri)
        .exchange()
        .expectStatus().isUnauthorized();
  }

  public static void checkForbidden(WebTestClient webTestClient, String uri) {
    String bearerToken = RandomStringUtils.randomAlphanumeric(8);

    webTestClient
        .get().uri(uri)
        .header(HttpHeaders.AUTHORIZATION, AuthorizationBearerFilter.BEARER_PREFIX + bearerToken)
        .exchange()
        .expectStatus().isForbidden();
  }

  public static void stubGetEndpoint(String path, String body) {
    ResponseDefinitionBuilder responseBuilder =
        WireMock
            .aResponse()
            .withBody(body)
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    WireMock.stubFor(
        WireMock
            .get(path)
            .willReturn(responseBuilder));
  }

}
