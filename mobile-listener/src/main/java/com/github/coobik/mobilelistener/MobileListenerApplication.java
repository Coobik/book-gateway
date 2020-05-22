package com.github.coobik.mobilelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.coobik.mobilelistener.client")
@EnableCaching
public class MobileListenerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MobileListenerApplication.class, args);
  }

}
