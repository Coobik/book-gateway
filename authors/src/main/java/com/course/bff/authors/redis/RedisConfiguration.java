package com.course.bff.authors.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.course.bff.authors.responses.AuthorResponse;


@Configuration
public class RedisConfiguration {

  @Autowired
  private RedisConnectionFactory connectionFactory;

  @Bean
  @Autowired
  public RedisTemplate<String, AuthorResponse> redisTemplate() {
    RedisTemplate<String, AuthorResponse> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    redisTemplate.setDefaultSerializer(RedisSerializer.string());
    redisTemplate.setEnableDefaultSerializer(true);

    redisTemplate.setValueSerializer(
        new Jackson2JsonRedisSerializer<AuthorResponse>(AuthorResponse.class));

    return redisTemplate;
  }

}
