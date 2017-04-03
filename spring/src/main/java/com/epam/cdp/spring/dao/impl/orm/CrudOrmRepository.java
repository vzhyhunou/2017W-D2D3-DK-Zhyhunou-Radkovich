package com.epam.cdp.spring.dao.impl.orm;

import org.joda.time.DateTime;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

public class CrudOrmRepository {

  private DataSource dataSource;
  private Map<String, String> javaToSqlType;
  private Map<String, String> sqlToJavaType;

  public CrudOrmRepository(DataSource dataSource) {
    this.dataSource = dataSource;
    javaToSqlType = new HashMap<String, String>() {{
      put("String", "VARCHAR");
      put("int", "INTEGER");
      put("Integer", "INTEGER");
      put("DateTime", "INTEGER");
    }};

    sqlToJavaType = new HashMap<>();
    for (Map.Entry<String, String> entry : javaToSqlType.entrySet()) {
      sqlToJavaType.put(entry.getValue(), entry.getKey());
    }
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

  private void mapResultSet(ResultSet resultSet, ResultSetMetaData metaData, Map<String, Object> columnValues)
      throws SQLException {
    for (int i = 1; i <= metaData.getColumnCount(); i++) {
      String columnName = metaData.getColumnName(i);
      String sqlType = metaData.getColumnTypeName(i);
      switch (sqlType) {
        case "VARCHAR":
          columnValues.put(columnName, resultSet.getString(i));
          break;
        case "INTEGER":
          columnValues.put(columnName, resultSet.getInt(i));
          break;
        default:
          throw new IllegalArgumentException(sqlType + " is not Implemented yet");
      }
    }
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

  private int fillPreparedStatement(MetaObject metaObject, PreparedStatement ps) throws SQLException {
    return fillPreparedStatement(metaObject, ps, 1);
  }

  private int fillPreparedStatement(MetaObject metaObject, PreparedStatement ps, int startFrom)
      throws SQLException {
    int i = startFrom;
    for (Map.Entry<String, String> entry : metaObject.getColumnTypes().entrySet()) {
      Object value = metaObject.getColumnValues().get(entry.getKey());
      if (value != null) {
        switch (entry.getValue()) {
          case "int":
            ps.setInt(i, (int) value);
            break;
          case "Integer":
            ps.setInt(i, (Integer) value);
            break;
          case "String":
            ps.setString(i, (String) value);
            break;
          case "DateTime":
            DateTime dateTime = (DateTime) value;
            Date sqlDate = new Date(dateTime.getMillis());
            ps.setDate(i, sqlDate);
            break;
          default:
            throw new IllegalArgumentException(entry.getValue() + " is not implemented yet");
        }
        i++;
      }
    }
    return i;
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
