package com.epam.dao;

import com.epam.core.Resource;

public class ResourceInmemoryCrudRepository extends BaseInmemoryCrudRepository<Resource> {

  private static long id = 1;

  @Override
  protected long generateId() {
    return id++;
  }

  @Override
  protected long getId(Resource obj) {
    return obj.getResourceId();
  }

  @Override
  protected void setId(Resource obj, long id) {
    obj.setResourceId(id);
  }
}
