package com.github.coobik.briefaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.coobik.briefaggregator.model.BookBrief;
import com.github.coobik.briefaggregator.service.BriefService;

import reactor.core.publisher.Flux;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/details", produces = MediaType.APPLICATION_JSON_VALUE)
public class BriefController {

  @Autowired
  private BriefService briefService;

  @GetMapping(path = {"/", ""})
  public Flux<BookBrief> getBooksWithDetails() {
    return briefService.getBooksBrief();
  }

}
