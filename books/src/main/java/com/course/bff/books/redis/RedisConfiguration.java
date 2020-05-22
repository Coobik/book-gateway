package com.course.bff.books.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.course.bff.books.responses.BookResponse;


@Configuration
public class RedisConfiguration {

  @Autowired
  private RedisConnectionFactory connectionFactory;

  @Bean
  @Autowired
  public RedisTemplate<String, BookResponse> redisTemplate() {
    RedisTemplate<String, BookResponse> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    redisTemplate.setDefaultSerializer(RedisSerializer.string());
    redisTemplate.setEnableDefaultSerializer(true);

    redisTemplate.setValueSerializer(
        new Jackson2JsonRedisSerializer<BookResponse>(BookResponse.class));

    return redisTemplate;
  }

}
