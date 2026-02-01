package com.splitwise.service;

import com.splitwise.dao.Group;
import com.splitwise.dao.User;
import com.splitwise.repository.GroupRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BalanceService {
    private final Map<String,Map<String, Map<String,Double>>> groupBalances = new HashMap<>();
    private final GroupService groupService;

    public BalanceService(GroupService groupService){
        this.groupService = groupService;
    }
    public void updateGroupBalances(String groupName, String paidBy, Map<String,Double> splits){
        groupBalances.putIfAbsent(groupName,new HashMap<>());

        Map<String,Map<String,Double>> balances = groupBalances.get(groupName);
        for(String user: splits.keySet()){
            if(user.equals(paidBy)) continue;
            double amount= splits.get(user);
            balances.computeIfAbsent(user, k->new HashMap<>())
                    .merge(paidBy, amount, Double::sum);

            balances.computeIfAbsent(user, k-> new HashMap<>())
                    .merge(user, -amount, Double::sum);
        }
    }

    public void showGroupBalances(String groupName){

        ReentrantReadWriteLock.ReadLock read = groupService.getReadLockByName(groupName);
        read.lock();
        try {
            Map<String,Map<String,Double>> balances = groupBalances.get(groupName);
            if (balances == null || balances.isEmpty()) {
                System.out.println("No balances for group " + groupName);
                return;
            }

            for(String u1 : balances.keySet()){
                for(String u2:balances.get(u1).keySet()){
                    double amount = balances.get(u1).get(u2);

                    if(amount>0){
                        System.out.println(u1 + " owes "+ u2+": "+ amount);
                    }
                }
            }
        } finally {
            read.unlock();
        }

    }

    public void showUserBalance(String groupName,String user){

        ReentrantReadWriteLock.ReadLock read = groupService.getReadLockByName(groupName);
        read.lock();
        try{
            Map<String,Double> userMap = groupBalances.get(groupName).get(user);

            if(userMap == null) return;

            for(String other: userMap.keySet()){
                double amt = userMap.get(other);
                if(amt>0){
                    System.out.println(user + " owes "+ other+": "+ amt);
                }

            }
        }
        finally {
            read.unlock();
        }

    }
    public void showOverallBalances(String user) {
        Map<String, Double> net = new HashMap<>();

        for (String groupId : groupBalances.keySet()) {
            Map<String, Map<String, Double>> balances = groupBalances.get(groupId);
            Map<String, Double> userMap = balances.get(user);

            if (userMap == null) continue;

            for (String other : userMap.keySet()) {
                net.merge(other, userMap.get(other), Double::sum);
            }
        }

        for (String other : net.keySet()) {
            if (net.get(other) > 0) {
                System.out.println(
                        user + " owes " + other+ " : " + net.get(other)
                );
            }
        }
    }
}
