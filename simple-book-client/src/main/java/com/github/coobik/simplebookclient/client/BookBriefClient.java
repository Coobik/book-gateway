package com.github.coobik.simplebookclient.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.coobik.simplebookclient.model.BookBrief;


@FeignClient(
    name = "bookbrief",
    url = "${bookbrief.url}",
    path = "/api/v1/details",
    configuration = BookBriefClientConfiguration.class,
    decode404 = true)
public interface BookBriefClient {

  @GetMapping(path = "/")
  List<BookBrief> getBooksBrief();

}
