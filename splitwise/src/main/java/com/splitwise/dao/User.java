package com.splitwise.dao;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String userName;

    public List<String> getGroups() {
        return groups;
    }

    public List<String> groups;
    public double balance;

    public List<String> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<String> expenses) {
        this.expenses = expenses;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> expenses;

    public User(String userName){
        this.userName = userName;
        this.balance = 0.0;
        this.expenses = new ArrayList<>();
        this.groups = new ArrayList<>();
    }
}
