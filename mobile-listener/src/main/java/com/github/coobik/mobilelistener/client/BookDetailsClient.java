package com.github.coobik.mobilelistener.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.coobik.mobilelistener.model.BookDetails;


@FeignClient(
    name = "bookdetails",
    url = "${bookdetails.url}",
    path = "/api/v1/details",
    configuration = BookDetailsClientConfiguration.class,
    decode404 = true)
public interface BookDetailsClient {

  @GetMapping(path = "/")
  List<BookDetails> getBooksWithDetails();

}
