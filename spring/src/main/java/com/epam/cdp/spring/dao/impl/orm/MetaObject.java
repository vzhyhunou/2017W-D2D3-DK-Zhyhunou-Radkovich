package com.epam.cdp.spring.dao.impl.orm;

import org.springframework.util.ReflectionUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.setField;

class MetaObject {
  private String tableName;
  private Map<String, String> columnTypes = new HashMap<>();
  private Map<String, Field> columnField = new HashMap<>();
  private Map<String, Object> columnValues = new HashMap<>();
  private Map<String, String> javaToSqlType;
  private Map<String, String> sqlToJavaType;

  private MetaObject(String tableName, Map<String, Field> columnField, Map<String, String> columnTypes, Map<String, Object> columnValues) {
    this.tableName = tableName;
    this.columnField = columnField;
    this.columnTypes = columnTypes;
    this.columnValues = columnValues;
    javaToSqlType = new HashMap<String, String>() {{
      put("String", "VARCHAR");
      put("int", "INTEGER");
      put("DateTime", "INTEGER");
    }};

    sqlToJavaType = new HashMap<>();
    for (Map.Entry<String, String> entry : javaToSqlType.entrySet()) {
      sqlToJavaType.put(entry.getValue(), entry.getKey());
    }
  }

  static MetaObject from(Object obj) {
    String tableName = obj.getClass().getAnnotation(Table.class).name();
    Map<String, String> columnTypes = new HashMap<>();
    Map<String, Field> columnField = new HashMap<>();
    Map<String, Object> columnValues = new HashMap<>();
    for (Field field : obj.getClass().getDeclaredFields()) {
      Column column = field.getAnnotation(Column.class);
      if (column != null) {
        columnTypes.put(column.name(), field.getType().getSimpleName());
        columnField.put(column.name(), field);
        columnValues.put(column.name(), getField(field, obj));
      }
    }
    return new MetaObject(tableName, columnField, columnTypes, columnValues);
  }

  public MetaObject copy() {
    return new MetaObject(tableName, columnField, columnTypes, new HashMap<>());
  }

  public <T> T restore(Class<T> tClass) {
    try {
      T instance = tClass.newInstance();
      String tableName = tClass.getAnnotation(Table.class).name();
      if (!this.tableName.equals(tableName)) {
        throw new RuntimeException("Table name should be compatible with original");
      }
      for (String columnName : columnField.keySet()) {
        setField(columnField.get(columnName), instance, columnValues.get(columnName.toUpperCase()));
      }
      return instance;
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Could Not Create Instance of " + tClass.getSimpleName());
  }

  public String getTableName() {
    return tableName;
  }

  public Map<String, Field> getColumnField() {
    return columnField;
  }

  public Map<String, String> getColumnTypes() {
    return columnTypes;
  }

  public Map<String, Object> getColumnValues() {
    return columnValues;
  }
}
