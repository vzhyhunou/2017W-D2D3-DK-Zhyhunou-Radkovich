package com.epam.core;

public class Resource {

  private long resourceId;
  private String description;

  private String rights;

  private long groupId;
  private long ownerId;

  public Resource(long resourceId, String description, String rights, long groupId, long ownerId) {
    this.resourceId = resourceId;
    this.description = description;
    this.rights = rights;
    this.groupId = groupId;
    this.ownerId = ownerId;
  }

  public long getResourceId() {
    return resourceId;
  }

  public void setResourceId(long resourceId) {
    this.resourceId = resourceId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRights() {
    return rights;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }

  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }
}
