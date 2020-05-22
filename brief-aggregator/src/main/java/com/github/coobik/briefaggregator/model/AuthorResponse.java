package com.github.coobik.briefaggregator.model;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorResponse {

  private UUID id;
  private String firstName;
  private String lastName;
  private String address;
  private String language;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @JsonIgnore
  public String getAuthorName() {
    StringBuilder nameBuilder = new StringBuilder();

    if (StringUtils.isNotBlank(firstName)) {
      nameBuilder.append(firstName).append(' ');
    }

    if (StringUtils.isNotBlank(lastName)) {
      nameBuilder.append(lastName);
    }

    return nameBuilder.toString();
  }

}
