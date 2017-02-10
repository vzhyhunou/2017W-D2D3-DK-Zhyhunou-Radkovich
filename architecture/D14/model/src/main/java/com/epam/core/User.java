package com.epam.core;

import java.util.List;

public class User {

  private long id;
  private String name;
  private List<Long> groups;

  public User(long id, String name, List<Long> groups) {
    this(name, groups);
    this.id = id;
  }

  public User(String name, List<Long> groups) {
    this.name = name;
    this.groups = groups;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Long> getGroups() {
    return groups;
  }

  public void setGroups(List<Long> groups) {
    this.groups = groups;
  }
}
