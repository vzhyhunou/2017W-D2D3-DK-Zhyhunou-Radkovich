package com.epam.cdp.spring.dao.impl.orm;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.epam.cdp.spring.dao.impl.orm.MetaObjectMapper.fillPreparedStatement;
import static com.epam.cdp.spring.dao.impl.orm.MetaObjectMapper.mapResultSet;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

public class CrudOrmRepository {

  private DataSource dataSource;

  public CrudOrmRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void create(Object obj) {
    MetaObject metaObject = MetaObject.from(obj);
    String tableName = metaObject.getTableName();
    Map<String, String> columnTypes = metaObject.getColumnTypes();
    String insertQuery = generateInsertQuery(tableName, columnTypes.keySet());
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(insertQuery)) {
      fillPreparedStatement(metaObject, ps);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public <T> List<T> read(T obj, Class<T> objectType) {
    MetaObject metaObject = MetaObject.from(obj);
    String selectQuery = generateSelectQuery(metaObject);
    List<T> result = new ArrayList<>();
    try (PreparedStatement ps = dataSource.getConnection().prepareStatement(selectQuery)) {
      fillPreparedStatement(metaObject, ps);
      ps.execute();
      ResultSet resultSet = ps.getResultSet();
      ResultSetMetaData metaData = resultSet.getMetaData();
      MetaObject resultSetMeta = metaObject.copy();
      while (resultSet.next()) {
        Map<String, Object> columnValues = resultSetMeta.getColumnValues();
        mapResultSet(resultSet, metaData, columnValues);
        result.add(resultSetMeta.restore(objectType));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  public void update(Object condition, Object sample) {
    MetaObject conditionMeta = MetaObject.from(condition);
    MetaObject sampleMeta = MetaObject.from(sample);
    List<String> setExpressions = createConditions(sampleMeta);
    if (setExpressions.isEmpty()) return;//no need to update
    String deleteSql = String.format("UPDATE %s SET %s", conditionMeta.getTableName(), String.join(", ", setExpressions));
    List<String> conditions = createConditions(conditionMeta);
    if (!conditions.isEmpty()) {
      deleteSql += " WHERE " + String.join(" AND ", conditions);
    }
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(deleteSql)) {
      int startFrom = fillPreparedStatement(sampleMeta, ps);
      if (!conditions.isEmpty()) fillPreparedStatement(conditionMeta, ps, startFrom);
      int update = ps.executeUpdate();
      System.out.println("Updated " + update + " rows.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void delete(Object condition) {
    MetaObject metaObject = MetaObject.from(condition);
    String deleteSql = String.format("DELETE FROM %s", metaObject.getTableName());
    List<String> conditions = createConditions(metaObject);
    if (!conditions.isEmpty()) {
      deleteSql += " WHERE " + String.join(" AND ", conditions);
    }
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(deleteSql)) {
      if (!conditions.isEmpty()) fillPreparedStatement(metaObject, ps);
      int update = ps.executeUpdate();
      System.out.println("Updated " + update + " rows.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private String generateInsertQuery(String tableName, Set<String> columns) {
    String valuesPattern = "";
    for (int i = 0; i < columns.size() - 1; i++) {
      valuesPattern += "?,";
    }
    valuesPattern += "?";
    return String.format("INSERT INTO %S (%s) VALUES (%s);",
        tableName, arrayToCommaDelimitedString(columns.toArray()), valuesPattern);
  }

  private String generateSelectQuery(MetaObject metaObject) {
    String sql = "SELECT * FROM %s";
    List<String> conditions = createConditions(metaObject);
    if (!conditions.isEmpty())
      sql += "\nWHERE " + String.join(" AND ", conditions);
    return String.format(sql, metaObject.getTableName());
  }

  private List<String> createConditions(MetaObject metaObject) {
    Predicate<Map.Entry<String, Object>> allWithValue = entry -> entry.getValue() != null;
    Function<Map.Entry<String, Object>, String> convertToSqlCondition = entry -> entry.getKey() + " =  ?";
    return metaObject.getColumnValues()
        .entrySet()
        .stream()
        .filter(allWithValue)
        .map(convertToSqlCondition)
        .collect(toList());
  }
}
