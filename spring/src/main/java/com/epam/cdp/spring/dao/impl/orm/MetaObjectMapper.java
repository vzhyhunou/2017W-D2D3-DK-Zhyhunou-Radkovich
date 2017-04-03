package com.epam.cdp.spring.dao.impl.orm;

import org.joda.time.DateTime;

import java.sql.*;
import java.util.Map;

class MetaObjectMapper {

  static int fillPreparedStatement(MetaObject metaObject, PreparedStatement ps) throws SQLException {
    return fillPreparedStatement(metaObject, ps, 1);
  }

  static int fillPreparedStatement(MetaObject metaObject, PreparedStatement ps, int startFrom)
      throws SQLException {
    int i = startFrom;
    for (Map.Entry<String, String> entry : metaObject.getColumnTypes().entrySet()) {
      Object value = metaObject.getColumnValues().get(entry.getKey());
      if (value != null) {
        switch (entry.getValue()) {
          case "Integer":
            ps.setInt(i, (Integer) value);
            break;
          case "String":
            ps.setString(i, (String) value);
            break;
          case "DateTime":
            DateTime dateTime = (DateTime) value;
            ps.setDate(i, new Date(dateTime.getMillis()));
            break;
          case "Boolean":
            ps.setBoolean(i, (Boolean) value);
            break;
          default:
            throw new IllegalArgumentException(entry.getValue() + " is not implemented yet");
        }
        i++;
      }
    }
    return i;
  }

  static void mapResultSet(ResultSet resultSet, ResultSetMetaData metaData, Map<String, Object> columnValues)
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
        case "DATE":
          columnValues.put(columnName, new DateTime(resultSet.getDate(i)));
          break;
        case "BOOLEAN":
          columnValues.put(columnName, resultSet.getBoolean(i));
          break;
        default:
          throw new IllegalArgumentException(sqlType + " is not Implemented yet");
      }
    }
  }
}
