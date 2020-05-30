package com.centraltech.calculator.services;

import com.centraltech.calculator.enums.Operator;

public class ArithmaticStrategyFactory {
		
    public Arithmatic getArithmatic(Operator operator) {
        switch (operator) {
    		case ADD: return new Add();
    		case SUBTRACT: return new Subtract();
    		case MULTIPLY: return new Multiply();
    		case DIVIDE: return new Devide();
            default: throw new UnsupportedOperationException("Unreognized symbol");
        }
    }

}
