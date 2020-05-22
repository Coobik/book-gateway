package com.github.coobik.mobilelistener.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


@Configuration
@ConditionalOnBean(MobileStompSessionHandlerAdapter.class)
public class WebSocketConfiguration {

  @Autowired
  private MobileStompSessionHandlerAdapter stompSessionHandler;

  @Value("${websocket.url}")
  private String webSocketUrl;

  @Bean
  public WebSocketClient webSocketClient() {
    return new StandardWebSocketClient();
  }

  @Bean
  @Autowired
  public WebSocketStompClient webSocketStompClient(WebSocketClient webSocketClient) {
    WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
    // stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    stompClient.setMessageConverter(new StringMessageConverter());
    stompClient.connect(webSocketUrl, stompSessionHandler);

    return stompClient;
  }

}
