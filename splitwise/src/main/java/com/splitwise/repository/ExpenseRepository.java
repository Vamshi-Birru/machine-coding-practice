package com.splitwise.repository;

import com.splitwise.dao.Expense;

import java.util.HashMap;
import java.util.Map;

public class ExpenseRepository {
    Map<String, Expense> collection;

    public  ExpenseRepository(){
        this.collection= new HashMap<>();
    }
    public void create(Expense expense){
        collection.put(expense.getExpenseName(), expense);
    }
}
