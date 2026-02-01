package com.splitwise.strategy;

import com.splitwise.dao.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public Map<String, Double> calculateSplits(double amount, List<String> users, Map<String, Double> meta) {
        Map<String,Double> splits = new HashMap<>();
        double share = amount/(double) users.size();
        for(String u:users){
            splits.put(u,share);
        }
        return splits;
    }
}
