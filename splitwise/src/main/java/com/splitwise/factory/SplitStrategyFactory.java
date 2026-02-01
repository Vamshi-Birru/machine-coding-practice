package com.splitwise.factory;

import com.splitwise.enums.SplitType;
import com.splitwise.strategy.EqualSplitStrategy;
import com.splitwise.strategy.ExactSplitStrategy;
import com.splitwise.strategy.PercentSplitStrategy;
import com.splitwise.strategy.SplitStrategy;

public class SplitStrategyFactory {
    public static SplitStrategy getStrategy (SplitType type){
        switch(type){
            case EQUAL:
                return new EqualSplitStrategy();
            case EXACT:
                return new ExactSplitStrategy();
            case PERCENT:
                return new PercentSplitStrategy();
            default:
                throw new RuntimeException("Unsupported split type");
        }
    }
}
