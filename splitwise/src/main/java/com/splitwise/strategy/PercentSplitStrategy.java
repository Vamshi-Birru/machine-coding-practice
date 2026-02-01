package com.splitwise.strategy;

import com.splitwise.dao.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentSplitStrategy implements SplitStrategy {
    @Override
    public Map<String, Double> calculateSplits(double amount, List<String> users, Map<String, Double> meta) {
        double percentSum = meta.values().stream().mapToDouble(Double::doubleValue).sum();
        if (percentSum != 100) {
            throw new RuntimeException("Percent split must sum to 100");
        }

        Map<String, Double> splits = new HashMap<>();
        for (String u : meta.keySet()) {
            splits.put(u, amount * meta.get(u) / 100);
        }
        return splits;
    }
}
