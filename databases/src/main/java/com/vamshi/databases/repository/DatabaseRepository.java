package com.vamshi.databases.repository;

import com.vamshi.databases.dto.Database;


import java.util.Collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseRepository {
    private final Map<String, Database> databases ;
    public  DatabaseRepository (){
        this.databases =  new ConcurrentHashMap<>();
    }

    public void save(Database database){
        databases.put(database.getName(),database);
    }

    public Database findDatabaseByName(String dataBaseName){
        return databases.get(dataBaseName);
    }

    public Collection<Database> findAllDatabases(){
        return databases.values();
    }

    public void delete(String databaseName){
        databases.remove(databaseName);
    }



}
