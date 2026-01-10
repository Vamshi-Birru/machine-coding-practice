package com.vamshi.databases.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Table {
    public String databaseName;
    public String name;
    public Map<String,Column> schema;
    public List<Map<String,Object>> rows;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Table(String databaseName, String name, Map<String,Column> schema){
        this.databaseName = databaseName;
        this.name = name;
        this.schema = schema;
        this.rows = new ArrayList<>();
    }
    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }



    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,Column> getSchema() {
        return schema;
    }

    public void setSchema(Map<String,Column> schema) {
        this.schema = schema;
    }

    public ReentrantReadWriteLock getLock() {
        return lock;
    }



}
