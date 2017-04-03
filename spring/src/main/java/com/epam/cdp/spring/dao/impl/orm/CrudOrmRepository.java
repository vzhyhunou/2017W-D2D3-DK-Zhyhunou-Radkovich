package com.epam.cdp.spring.dao.impl.orm;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.epam.cdp.spring.dao.impl.orm.MetaObjectMapper.fillPreparedStatement;
import static com.epam.cdp.spring.dao.impl.orm.MetaObjectMapper.mapResultSet;
import static java.lang.String.join;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;

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
      MetaObject resultMetaObject = metaObject.copy();
      while (resultSet.next()) {
        mapResultSet(resultSet, resultMetaObject);
        result.add(resultMetaObject.restore(objectType));
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
    List<String> conditions = createConditions(conditionMeta);
    String updateSql = generateUpdateQuery(conditionMeta.getTableName(), setExpressions, conditions);
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(updateSql)) {
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
    List<String> conditions = createConditions(metaObject);
    String deleteSql = generateDeleteQuery(metaObject, conditions);
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(deleteSql)) {
      if (!conditions.isEmpty()) fillPreparedStatement(metaObject, ps);
      int update = ps.executeUpdate();
      System.out.println("Updated " + update + " rows.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private String generateUpdateQuery(String tableName, List<String> setExpressions, List<String> conditions) {
    String newColumnValues = join(", ", setExpressions);
    String updateSql = String.format("UPDATE %s SET %s", tableName, newColumnValues);
    if (!conditions.isEmpty()) {
      updateSql += " WHERE " + join(" AND ", conditions);
    }
    return updateSql;
  }

  private String generateDeleteQuery(MetaObject metaObject, List<String> conditions) {
    String deleteSql = String.format("DELETE FROM %s", metaObject.getTableName());
    if (!conditions.isEmpty()) {
      deleteSql += " WHERE " + join(" AND ", conditions);
    }
    return deleteSql;
  }

  private String generateInsertQuery(String tableName, Set<String> columns) {
    return String.format("INSERT INTO %s (%s) VALUES (%s);",
        tableName, join(", ", columns), join(", ", nCopies(columns.size(), "?")));
  }

  private String generateSelectQuery(MetaObject metaObject) {
    String sql = "SELECT * FROM %s";
    List<String> conditions = createConditions(metaObject);
    if (!conditions.isEmpty())
      sql += "\nWHERE " + join(" AND ", conditions);
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
