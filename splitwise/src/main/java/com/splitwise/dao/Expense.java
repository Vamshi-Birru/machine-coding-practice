package com.splitwise.dao;

import com.splitwise.enums.SplitType;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;

public class Expense {
    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public String expenseName;
    public List<String> userNames;
    public String createdBy;
    public String updatedBy;
    public LocalTime createdAt;
    public LocalTime updatedAt;
    public SplitType type;
    Map<String, Double> splits;


    public Expense(String expenseName, List<String> userNames, String createdBy, String updatedBy,
                   LocalTime createdAt, LocalTime updatedAt, SplitType type,  Map<String, Double> splits){
        this.expenseName = expenseName;
        this.userNames = userNames;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.type = type;
        this.splits = splits;
    }
}
