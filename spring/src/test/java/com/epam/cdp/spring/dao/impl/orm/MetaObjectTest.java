package com.epam.cdp.spring.dao.impl.orm;

import org.joda.time.DateTime;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;

public class MetaObjectTest {

  @Entity
  @Table
  public class SampleTestEntity {
    @Column(name = "name")
    public String string;
    @Column(name = "birthdate")
    public DateTime dateTime;
    @Column(name = "amount")
    public int amount;

    public SampleTestEntity() {
    }

    public SampleTestEntity(String string, DateTime dateTime, int amount) {
      this.string = string;
      this.dateTime = dateTime;
      this.amount = amount;
    }
  }

  @Test
  public void from() throws Exception {
    MetaObject metaObject = MetaObject.from(new SampleTestEntity("AAA", DateTime.now(), 19));
  }
}