package com.course.bff.books.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.course.bff.books.responses.BookResponse;


@Service
public class RedisPublisher {

  private final static Logger LOGGER = LoggerFactory.getLogger(RedisPublisher.class);

  @Value("${redis.channel}")
  private String redisChannel;

  @Autowired
  private RedisTemplate<String, BookResponse> redisTemplate;

  public void publish(BookResponse book) {
    if (book == null) {
      return;
    }

    LOGGER.info("publishing book to redis: {}", book.getId());

    try {
      redisTemplate.convertAndSend(redisChannel, book);
    }
    catch (Exception ex) {
      LOGGER.error("error publishing book: {} to redis: {}",
          book.getId(), ex.getMessage());
    }
  }

}
