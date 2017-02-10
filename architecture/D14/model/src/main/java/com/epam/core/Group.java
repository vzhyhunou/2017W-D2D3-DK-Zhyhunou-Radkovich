package com.epam.core;

public class Group {

  private long id;
  private String name;

  public Group(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Group(String name) {
    this.name = name;
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
}
