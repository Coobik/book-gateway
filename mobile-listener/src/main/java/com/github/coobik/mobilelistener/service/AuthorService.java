package com.github.coobik.mobilelistener.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.coobik.mobilelistener.client.AuthorClient;
import com.github.coobik.mobilelistener.model.AuthorResponse;


@Service
public class AuthorService {

  public static final String CACHE_AUTHORS = "authors";

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);

  @Autowired
  private AuthorClient authorClient;

  @Autowired
  private CacheManager cacheManager;

  @Cacheable(CACHE_AUTHORS)
  public AuthorResponse getAuthor(UUID authorId) {
    if (authorId == null) {
      return null;
    }

    LOGGER.info("fetching author: {}", authorId);

    try {
      return authorClient.getAuthor(authorId);
    }
    catch (Exception ex) {
      LOGGER.error("error fetching author: {} : {}", authorId, ex.getMessage());
      return null;
    }
  }

  public void clearCache() {
    Cache authorsCache = cacheManager.getCache(CACHE_AUTHORS);

    if (authorsCache == null) {
      return;
    }

    authorsCache.clear();
  }

}
