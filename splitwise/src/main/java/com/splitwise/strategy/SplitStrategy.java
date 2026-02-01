package com.splitwise.strategy;

import com.splitwise.dao.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    Map<String,Double> calculateSplits(double amount, List<String> users, Map<String,Double> meta);
}
