package com.github.coobik.gateway.route;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@Component
public class AuthTokenProvider {

  @Value("${auth.tokens}")
  private List<String> authTokens;

  public String getAuthToken() {
    if (CollectionUtils.isEmpty(authTokens)) {
      return null;
    }

    int index = RandomUtils.nextInt(0, authTokens.size());

    return authTokens.get(index);
  }

}
