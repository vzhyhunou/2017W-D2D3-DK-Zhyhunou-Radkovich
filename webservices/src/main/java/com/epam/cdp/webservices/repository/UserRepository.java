package com.epam.cdp.webservices.repository;

import com.epam.cdp.webservices.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {
  private Map<String, User> users = new HashMap<>();

  @PostConstruct
  public void init() {
    users.put("AAA", new User("AAA", "AAA@mail.ru"));
    users.put("BBB", new User("BBB", "BBB@mail.ru"));
    users.put("CCC", new User("CCC", "CCC@mail.ru"));
  }

  public String findEmailByName(String name) {
    return users.get(name).email;
  }
}
