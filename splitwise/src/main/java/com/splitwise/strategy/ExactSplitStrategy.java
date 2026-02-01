package com.splitwise.strategy;

import com.splitwise.dao.User;

import java.util.List;
import java.util.Map;

public class ExactSplitStrategy  implements SplitStrategy {
    @Override
    public Map<String, Double> calculateSplits(double amount, List<String> users, Map<String, Double> meta) {
        double sum = meta.values().stream().mapToDouble(Double::doubleValue).sum();
        if (sum != amount) {
            throw new RuntimeException("Exact split sum mismatch");
        }
        return meta;
    }
}
