package com.splitwise.dao;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Group {

    public String groupName;

    public List<String> getExpenseNames() {
        return expenseNames;
    }

    public void setExpenseNames(List<String> expenseNames) {
        this.expenseNames = expenseNames;
    }

    public List<String> expenseNames;


    public String createdBy;
    public String updatedBy;
    public LocalTime createdAt;
    public LocalTime updatedAt;

    public ReentrantReadWriteLock getLock() {
        return lock;
    }

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> users;



    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }



    public Group(String groupName,  List<String> expenseNames,String createdBy, String updatedBy,
                 LocalTime createdAt, LocalTime updatedAt,List<String> users){
        this.groupName = groupName;
        this.expenseNames = expenseNames;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.users= users;
    }
}
