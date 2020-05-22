package com.github.coobik.briefaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactivefeign.spring.config.EnableReactiveFeignClients;


@SpringBootApplication
@EnableReactiveFeignClients(basePackages = "com.github.coobik.briefaggregator.client")
public class BriefAggregatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(BriefAggregatorApplication.class, args);
  }

}
