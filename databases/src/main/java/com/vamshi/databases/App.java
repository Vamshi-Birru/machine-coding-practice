package com.vamshi.databases;

import com.vamshi.databases.service.TableService;

import java.util.Map;

/**
 * Hello world!
 */
public class App {


        public static void main(String[] args) throws Exception {
            TableService ts = new TableService();

            ts.createDb("db1");
            ts.createTable("db1","students","id int required, name string required, age int");

//            ts.insertTable("db1","students", Map.of("id",1,"name","john","age",20));
//            ts.insertTable("db1","students", Map.of("id",2,"name","alice"));
//            ts.insertTable("db1","students", Map.of("id",3,"name","bob","age",22));
//
//            ts.printTable("db1","students");
//
//            ts.selectWhere("db1","students","name","alice");
            Runnable writer = () -> {
                for(int i=0;i<100;i++){
                    try {
                        ts.insertTable("db1","students", Map.of("id",i, "name","U"+i));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Runnable reader = () -> {
                for(int i=0;i<100;i++){
                    try {
                        ts.printTable("db1","students");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            Thread t1 = new Thread(writer);
            Thread t2 = new Thread(writer);
            Thread t3 = new Thread(reader);
            Thread t4 = new Thread(reader);

            t1.start();t2.start();t3.start();t4.start();
            t1.join();t2.join();t3.join();t4.join();



        }
}
