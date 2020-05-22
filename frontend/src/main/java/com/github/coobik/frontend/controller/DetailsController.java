package com.github.coobik.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.coobik.frontend.model.BookDetails;
import com.github.coobik.frontend.service.DetailsService;

import reactor.core.publisher.Flux;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/details", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailsController {

  @Autowired
  private DetailsService detailsService;

  @GetMapping(path = {"/", ""})
  public Flux<BookDetails> getBooksWithDetails() {
    return detailsService.getBooksWithDetails();
  }

}
