package com.epam.cdp.spring.jdbc;

import com.epam.cdp.spring.configuration.InMemoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryConfiguration.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = {"h2", "cacheble", "inmemory"})
public class EmbeddedH2Test {

  @Autowired
  private DataSource dataSource;

  @Test
  public void metadataCheck() throws Exception {
    Set<String> requiredTables = new HashSet<>();
    requiredTables.add("CUSTOMERS");
    requiredTables.add("FLIGHTS");
    requiredTables.add("TICKETS");

    Set<String> actualTables = new HashSet<>();
    DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
    ResultSet tables = metaData.getTables(null, null, "%", null);
    while (tables.next()) {
      if ("TABLE".equals(tables.getString("TABLE_TYPE")))
        actualTables.add(tables.getString("TABLE_NAME"));
    }
    assertEquals(requiredTables, actualTables);
  }
}
