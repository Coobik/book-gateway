package com.github.coobik.simplebookclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.coobik.simplebookclient.client")
public class SimpleBookClientApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleBookClientApplication.class);

  public static void main(String[] args) {
    LOGGER.info("starting simple book client...");

    SpringApplication.run(SimpleBookClientApplication.class, args);
  }

}
