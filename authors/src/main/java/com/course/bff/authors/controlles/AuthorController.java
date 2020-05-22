package com.course.bff.authors.controlles;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.bff.authors.models.Author;
import com.course.bff.authors.redis.RedisPublisher;
import com.course.bff.authors.requests.CreateAuthorCommand;
import com.course.bff.authors.responses.AuthorResponse;
import com.course.bff.authors.services.AuthorService;

@RestController
@RequestMapping("api/v1/authors")
@CrossOrigin
public class AuthorController {
    private final static Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private RedisPublisher redisPublisher;

    @GetMapping()
    public Collection<AuthorResponse> getAuthors(
        @RequestParam(name = "ids", required = false) Set<String> ids) {
        List<AuthorResponse> authorResponses =
            authorService
                .getAuthors(ids)
                .stream()
                .map(this::createAuthorResponse)
                .collect(Collectors.toList());

        return authorResponses;
    }

    @GetMapping("/{id}")
    public AuthorResponse getById(@PathVariable UUID id) {
        logger.info(String.format("Find authors by %s", id));
        Optional<Author> authorSearch = this.authorService.findById(id);
        if (authorSearch.isEmpty()) {
            throw new RuntimeException("Author isn't found");
        }

        return createAuthorResponse(authorSearch.get());
    }

    @PostMapping()
    public AuthorResponse createAuthors(@RequestBody CreateAuthorCommand createAuthorCommand) {
        logger.info("Create authors");
        Author author = this.authorService.create(createAuthorCommand);
        AuthorResponse authorResponse = createAuthorResponse(author);
        this.sendPushNotification(authorResponse);
        return authorResponse;
    }


    private void sendPushNotification(AuthorResponse authorResponse) {
        redisPublisher.publish(authorResponse);        
    }

    private AuthorResponse createAuthorResponse(Author author) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setId(author.getId());
        authorResponse.setFirstName(author.getFirstName());
        authorResponse.setLastName(author.getLastName());
        authorResponse.setAddress(author.getAddress());
        authorResponse.setLanguage(author.getLanguage());
        return authorResponse;
    }
}
