package com.epam.dao;

import com.epam.core.User;

public class UserInmemoryCrudRepository extends BaseInmemoryCrudRepository<User> {

  private static long id = 1;

  @Override
  protected long generateId() {
    return id++;
  }

  @Override
  protected long getId(User obj) {
    return obj.getId();
  }

  @Override
  protected void setId(User obj, long id) {
    obj.setId(id);
  }
}
