package com.vamshi.databases.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    public String name;
    public Map<String,Table> tables;

    public Database(String name){
        this.name = name;
        this.tables = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }




}
