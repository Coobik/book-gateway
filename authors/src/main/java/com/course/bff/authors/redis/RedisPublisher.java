package com.course.bff.authors.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.course.bff.authors.responses.AuthorResponse;


@Service
public class RedisPublisher {

  private final static Logger LOGGER = LoggerFactory.getLogger(RedisPublisher.class);

  @Value("${redis.channel}")
  private String redisChannel;

  @Autowired
  private RedisTemplate<String, AuthorResponse> redisTemplate;

  public void publish(AuthorResponse author) {
    if (author == null) {
      return;
    }

    LOGGER.info("publishing author to redis: {}", author.getId());

    try {
      redisTemplate.convertAndSend(redisChannel, author);
    }
    catch (Exception ex) {
      LOGGER.error("error publishing author: {} to redis: {}",
          author.getId(), ex.getMessage());
    }
  }

}
