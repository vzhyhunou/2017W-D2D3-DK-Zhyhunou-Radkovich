package com.epam.dao;

import com.epam.core.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class UserInmemoryCrudRepositoryTest {

  private UserInmemoryCrudRepository dao;

  @Before
  public void init() throws Exception {
    dao = new UserInmemoryCrudRepository();
  }

  @Test
  public void create() throws Exception {
    User user = dao.create(user("AAA", asList(1L, 2L)));
    User readUser = dao.read(user.getId());
    assertTrue(user == readUser);
  }

  @Test
  public void read() throws Exception {
    User user1 = dao.create(user("AAA", asList(1L, 2L)));
    dao.create(user("BBB", asList(1L, 2L)));
    User readUser = dao.read(user1.getId());
    assertTrue(user1 == readUser);
  }

  @Test
  public void update() throws Exception {
    User user = dao.create(user("AAA", new ArrayList<>(asList(1L, 2L))));
    user.getGroups().add(3L);
    dao.update(user);
    User read = dao.read(user.getId());
    assertEquals(asList(1L, 2L, 3L), read.getGroups());
  }

  @Test
  public void delete() throws Exception {
    User user1 = dao.create(user("AAA", asList(1L, 2L)));
    dao.create(user("BBB", asList(1L, 2L)));

    dao.delete(user1.getId());

    User read = dao.read(user1.getId());
    assertTrue(read == null);
  }

  private User user(String name, List<Long> groupIds) {
    return new User(name, groupIds);
  }
}
