package com.course.bff.websockets.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.course.bff.websockets.controllers.PushController;


@Component
public class RedisMessageListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageListener.class);

  @Autowired
  private PushController pushController;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    if (message == null) {
      LOGGER.warn("null message received");
      return;
    }

    String body = new String(message.getBody());
    String channel = new String(message.getChannel());

    LOGGER.info("message: {}: {}", channel, body);

    pushMessage(body);
  }

  private void pushMessage(String body) {
    try {
      pushController.send(body);
    }
    catch (Exception ex) {
      LOGGER.error("error pushing message: {} : {}", body, ex.getMessage());
    }
  }

}
