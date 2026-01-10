package com.vamshi.databases.repository;

import com.vamshi.databases.dto.Table;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TableRepository {
    private final Map<String, Table> tables;

    public TableRepository (){
        this.tables = new ConcurrentHashMap<>();
    }

    public void save(Table table){
        tables.put(table.getDatabaseName()+"."+table.getName(),table);
    }

    public Collection<Table> findAllTables(){
        return tables.values();
    }

    public Table findTablebyName(String databaseName, String tableName){
        return tables.get(databaseName+"."+tableName);
    }

    public void delete(String dbName, String tableName){
        tables.remove(dbName+"."+tableName);
    }

}
