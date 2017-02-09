package com.epam.dao;

import com.epam.core.Group;

public class GroupInmemoryCrudRepository extends BaseInmemoryCrudRepository<Group> {

  private static long id = 1;

  @Override
  protected long generateId() {
    return id++;
  }

  @Override
  protected long getId(Group obj) {
    return obj.getId();
  }

  @Override
  protected void setId(Group obj, long id) {
    obj.setId(id);
  }
}
