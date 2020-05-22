package com.course.bff.authors.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.course.bff.authors.models.Author;
import com.course.bff.authors.requests.CreateAuthorCommand;

@Component
public class AuthorService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

    private final ArrayList<Author> authors;

    public AuthorService() {
        this.authors = new ArrayList<>();
        this.authors.add(new Author()
                .withId(UUID.randomUUID())
                .withFirstName("Loreth Anne")
                .withLastName("White")
                .withLanguage("English")
                .withAddres("620 Eighth Avenue, New York"));

        this.authors.add(new Author()
                .withId(UUID.randomUUID())
                .withFirstName("Lisa")
                .withLastName("Regan").withLastName("White")
                .withLanguage("English")
                .withAddres("20, 2 Heigham Rd, East Ham, London E6 2JH"));

        this.authors.add(new Author()
                .withId(UUID.randomUUID())
                .withFirstName("Ty")
                .withLastName("Patterson")
                .withLanguage("English")
                .withAddres("1-9 Inverness Terrace, Bayswater, London W2 3JP"));
    }

    public Collection<Author> getAuthors(Set<String> ids) {
        if (ids == null) {
            LOGGER.info("get all authors");
            return authors;
        }

        if (ids.isEmpty()) {
            LOGGER.info("ids filter is empty");
            return Collections.emptyList();
        }

        LOGGER.info("get authors: {}", ids);

        return authors
            .stream()
            .filter(author -> ids.contains(author.getId().toString()))
            .collect(Collectors.toList());
    }

    public Optional<Author> findById(UUID id) {
        return this.authors.stream().filter(author -> author.getId().equals(id)).findFirst();
    }

    public Author create(CreateAuthorCommand createAuthorCommand) {
        Author author = new Author()
                .withId(UUID.randomUUID())
                .withFirstName(createAuthorCommand.getFirstName())
                .withLastName(createAuthorCommand.getLastName())
                .withLanguage(createAuthorCommand.getLanguage())
                .withAddres(createAuthorCommand.getAddress());

        this.authors.add(author);
        return author;
    }
}
