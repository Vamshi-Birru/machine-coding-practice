package com.vamshi.databases.service;

import com.vamshi.databases.dto.Column;
import com.vamshi.databases.dto.Database;
import com.vamshi.databases.dto.Table;
import com.vamshi.databases.enums.Datatype;
import com.vamshi.databases.repository.DatabaseRepository;
import com.vamshi.databases.repository.TableRepository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TableService {
    private  TableRepository tableRepository;
    private   DatabaseRepository databaseRepository;
    public TableService(){
        this.tableRepository = new TableRepository();
        this.databaseRepository = new DatabaseRepository();
    }
    public void createTable(String databaseName, String tableName, String columnsSpec) throws Exception {
        Database database = databaseRepository.findDatabaseByName(databaseName);
        if(database==null) throw(new Exception("Database not found"));

        Map<String,Column> schema = new HashMap<>();

        String [] defs = columnsSpec.split(",");

        for(String d : defs){
            String [] parts = d.trim().split(" ");
            String colName = parts[0];
            Datatype dataType = Datatype.valueOf(parts[1].toUpperCase());
            boolean required = parts.length==3&&parts[2].equalsIgnoreCase("required");
            schema.put(colName,new Column(colName,dataType,required));
        }

        Table table = new Table(databaseName,tableName, schema);
        tableRepository.save(table);
    }

    public void createDb(String dbName){
        Database database = new Database(dbName);

        databaseRepository.save(database);
    }

    public void deleteTable(String databaseName, String tableName) throws Exception {
        Table table = tableRepository.findTablebyName(databaseName,tableName);
        if(table== null) throw new Exception("Table not found");
        ReentrantReadWriteLock.WriteLock write = table.getLock().writeLock();
        write.lock();
        try {
            tableRepository.delete(databaseName, tableName);
            deleteTableFromDB(databaseName, tableName);
        }
        finally {
            write.unlock();
        }
    }

    public void deleteTableFromDB(String dbName, String tableName) throws Exception {
        Database db = databaseRepository.findDatabaseByName(dbName);
        if(db==null) throw new Exception("Database is not found!!");

        Map<String, Table> tables = db.getTables();

        tables.remove(tableName);
        db.setTables(tables);

        databaseRepository.save(db);
    }

    public void deleteDatabase(String dbName) throws Exception {
        Database db = databaseRepository.findDatabaseByName(dbName);
        if(db==null) throw new Exception("Database is not found!!");

        databaseRepository.delete(dbName);

    }

    public void  printTable(String databaseName, String tableName) throws Exception {
         Table table = tableRepository.findTablebyName(databaseName, tableName);
         if(table == null) throw new Exception("Table not found!!");
        synchronized (System.out) {
            ReentrantReadWriteLock.ReadLock read = table.getLock().readLock();
            read.lock();
            try {
                table.getSchema().forEach((col, Column) -> System.out.print(col + "\t"));
                System.out.println();
                for (Map<String, Object> row : table.getRows()) {
                    for (Column col : table.getSchema().values()) {
                        Object v = row.getOrDefault(col.getName(), "null");
                        System.out.print(v + "\t");
                    }
                    System.out.println();
                }
            } finally {
                read.unlock();
            }
        }



    }

    public void selectWhere(String databaseName, String tableName, String col, String val) throws Exception {
        Table table = tableRepository.findTablebyName(databaseName,tableName);
        if(table == null) throw new Exception("Table not found!!");

        ReentrantReadWriteLock.ReadLock read = table.getLock().readLock();
        read.lock();
        try {
            table.getSchema().forEach((column, Column) -> System.out.print(column + "\t"));
            System.out.println();
            for (Map<String, Object> row : table.getRows()) {
                if (!row.get(col).equals(val)) continue;
                for (Column column : table.getSchema().values()) {
                    Object v = row.getOrDefault(column.getName(), "null");
                    System.out.print(v + "\t");
                }
                System.out.println();
            }
        }
        finally {
            read.unlock();
        }

    }

    public void insertTable(String databaseName, String tableName, Map<String, Object> rowValues) throws Exception {
        Table table = tableRepository.findTablebyName(databaseName,tableName);
        if(table == null) throw new Exception("Table is not found!!");
        ReentrantReadWriteLock.WriteLock write = table.getLock().writeLock();
        write.lock();
        try{
            List<Map<String,Object>> rows = table.getRows();
            Map<String,Object> row= new HashMap<>();
            for(Column column:table.getSchema().values()){
                if(column.isRequired()&& !rowValues.containsKey(column.getName())) throw new RuntimeException("Column is required: "+column.getName());
            }
            for(String col: rowValues.keySet()){
                Column column = table.getSchema().get(col);
                if(column==null) throw new Exception("Column is not present: "+ col);
                if(row.containsKey(col)) throw new Exception("Column is duplicated: "+ col);
                Object obj = rowValues.get(col);
                if(column.getDataType()==Datatype.INT) {
                    if(! (obj instanceof Integer)) throw new RuntimeException("Required datatype for column: " + col + " is int but provided String");
                    if((Integer)obj>1024||(Integer)obj<-1024) throw new RuntimeException("The integer can have maximum value upto 1024 and minimum value upto -1024");
                }
                if(column.getDataType()==Datatype.STRING ) {
                    if( !(obj instanceof String)) throw new RuntimeException("Required datatype for column: "+col+" is int but provided String");
                    if(obj.toString().length()>20) throw new RuntimeException("String can have maximum 20 characters!!");
                }
                if(column.isRequired()&&(obj==null||obj.toString().isBlank())) throw new RuntimeException("Column is required: "+column.getName());
                row.put(col,rowValues.get(col));
            }
            rows.add(row);
            table.setRows(rows);
            tableRepository.save(table);
        }
//        catch(RuntimeException e){
//
//        }
        finally {
            write.unlock();
        }

    }


}
