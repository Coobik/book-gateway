package com.github.coobik.frontend.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.coobik.frontend.client.AuthorClient;
import com.github.coobik.frontend.client.BookClient;
import com.github.coobik.frontend.model.AuthorResponse;
import com.github.coobik.frontend.model.BookDetails;
import com.github.coobik.frontend.model.BookResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class DetailsService {

  @Autowired
  private BookClient bookClient;

  @Autowired
  private AuthorClient authorClient;

  public Flux<BookDetails> getBooksWithDetails() {
    Flux<BookResponse> bookFlux = bookClient.getBooks();

    Mono<List<String>> authorIdsMono = extractAuthorIdsList(bookFlux);
    Mono<Map<UUID, AuthorResponse>> authorsByIdMono = getAuthorsByIdsMap(authorIdsMono);

    // https://stackoverflow.com/questions/51603075/project-reactor-how-to-combine-a-mono-and-a-flux
    return authorsByIdMono.flatMapMany(
        authorsByIds -> bookFlux.map(
            book -> new BookDetails(book, extractAuthorForBook(authorsByIds, book))));
  }

  private Mono<List<String>> extractAuthorIdsList(Flux<BookResponse> bookFlux) {
    Flux<String> authorIdFlux =
        bookFlux
            .map(book -> book.getAuthorId().toString())
            .distinct();

    return authorIdFlux.collectList();
  }

  private AuthorResponse extractAuthorForBook(
      Map<UUID, AuthorResponse> authorsByIds,
      BookResponse book) {
    UUID authorId = book.getAuthorId();

    if (authorId == null) {
      return null;
    }

    return authorsByIds.get(authorId);
  }

  private Mono<Map<UUID, AuthorResponse>> getAuthorsByIdsMap(Mono<List<String>> authorIdsMono) {
    Flux<AuthorResponse> authorFlux =
        authorIdsMono.flatMapMany(
            authorIds -> getAuthors(authorIds));

    return authorFlux.collectMap(author -> author.getId());
  }

  private Flux<AuthorResponse> getAuthors(List<String> authorIds) {
    if (CollectionUtils.isEmpty(authorIds)) {
      return Flux.empty();
    }

    return authorClient.getAuthors(authorIds);
  }

}
