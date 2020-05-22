package com.course.bff.websockets.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisConfiguration {

  @Value("${redis.authors.channel}")
  private String authorsChannel;

  @Value("${redis.books.channel}")
  private String booksChannel;

  @Autowired
  private RedisConnectionFactory connectionFactory;

  @Autowired
  private RedisMessageListener redisMessageListener;

  @Bean("authorsTopic")
  public ChannelTopic authorsTopic() {
    return new ChannelTopic(authorsChannel);
  }

  @Bean("booksTopic")
  public ChannelTopic booksTopic() {
    return new ChannelTopic(booksChannel);
  }

  @Bean
  public MessageListenerAdapter messageListenerAdapter() {
    return new MessageListenerAdapter(redisMessageListener);
  }

  @Bean
  @Autowired
  public RedisMessageListenerContainer redisMessageListenerContainer(
      MessageListenerAdapter messageListenerAdapter,
      List<ChannelTopic> topics) {
    RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();

    listenerContainer.setConnectionFactory(connectionFactory);
    listenerContainer.addMessageListener(messageListenerAdapter, topics);

    return listenerContainer;
  }

}
