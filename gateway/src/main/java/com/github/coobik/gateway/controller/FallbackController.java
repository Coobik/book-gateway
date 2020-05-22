package com.github.coobik.gateway.controller;

import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin
@RequestMapping(path = "/fallback", produces = MediaType.APPLICATION_JSON_VALUE)
public class FallbackController {

  @GetMapping(path = {"/", ""})
  public Flux<Map<String, String>> fallback() {
    return Flux.just();
  }

  @GetMapping(path = "/health")
  public Mono<Health> healthFallback() {
    return Mono.just(Health.down().build());
  }

}
