package com.splitwise.service;

import com.splitwise.dao.Expense;
import com.splitwise.dao.Group;
import com.splitwise.enums.SplitType;
import com.splitwise.factory.SplitStrategyFactory;
import com.splitwise.repository.ExpenseRepository;
import com.splitwise.repository.GroupRepository;
import com.splitwise.strategy.SplitStrategy;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExpenseService {

    private final BalanceService balanceService;
    private final GroupService groupService;

    private final ExpenseRepository expenseRepo;

    public ExpenseService(BalanceService balanceService, GroupService groupService, ExpenseRepository expenseRepo ){
        this.balanceService= balanceService;
        this.groupService = groupService;
        this.expenseRepo = expenseRepo;
    }
    public void addExpense(
            String expenseName,
            String groupName,
            String paidBy,
            double amount,
            List<String> users,
            SplitType type,
            Map<String, Double> meta
    ){

        if(!groupService.validateUsersAgainstGroup(groupName,users)){
            throw  new RuntimeException("User not part of group");
        }
        ReentrantReadWriteLock.WriteLock writeLock = groupService.getWriteLockByName(groupName);
        writeLock.lock();
        try{
            SplitStrategy strategy = SplitStrategyFactory.getStrategy(type);
            Map<String, Double> splits = strategy.calculateSplits(amount,users,meta);

            balanceService.updateGroupBalances(groupName,paidBy,splits);
            Expense expense = new Expense(expenseName,
                    users,
                    paidBy,
                    paidBy,
                    LocalTime.now(),
                    LocalTime.now(),
                    type,
                    splits);
            expenseRepo.create(expense);
        }
        finally{
            writeLock.unlock();
        }

    }
}
