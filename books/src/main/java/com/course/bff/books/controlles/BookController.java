package com.course.bff.books.controlles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.bff.books.models.Book;
import com.course.bff.books.redis.RedisPublisher;
import com.course.bff.books.requests.CreateBookCommand;
import com.course.bff.books.responses.BookResponse;
import com.course.bff.books.services.BookService;

@RestController
@RequestMapping("api/v1/books")
@CrossOrigin
public class BookController {

    private final static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private RedisPublisher redisPublisher;

    @GetMapping()
    public Collection<BookResponse> getBooks() {
        logger.info("Get book list");
        List<BookResponse> bookResponses = new ArrayList<>();
        this.bookService.getBooks().forEach(book -> {
            BookResponse authorResponse = createBookResponse(book);
            bookResponses.add(authorResponse);
        });

        return bookResponses;
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable UUID id) {
        logger.info(String.format("Find book by id %s", id));
        Optional<Book> bookSearch = this.bookService.findById(id);
        if (bookSearch.isEmpty()) {
            throw new RuntimeException("Book isn't found");
        }


        return createBookResponse(bookSearch.get());
    }

    @PostMapping()
    public BookResponse createBook(@RequestBody CreateBookCommand createBookCommand) {
        logger.info("Create book");
        Book book = this.bookService.create(createBookCommand);
        BookResponse bookResponse = createBookResponse(book);
        this.sendPushNotification(bookResponse);
        return bookResponse;
    }

    private void sendPushNotification(BookResponse bookResponse) {
        redisPublisher.publish(bookResponse);
    }

    private BookResponse createBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setAuthorId(book.getAuthorId());
        bookResponse.setPages(book.getPages());
        bookResponse.setTitle(book.getTitle());
        return bookResponse;
    }
}
