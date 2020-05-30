package com.centraltech.calculator.services;

public class ArithmaticStrategyFactory {
		
    public Arithmatic getArithmatic(char operator) {
        switch (operator) {
    		case '+': return new Add();
    		case '-': return new Subtract();
    		case '*': return new Multiply();
    		case '/': return new Devide();
            default: throw new UnsupportedOperationException("Unreognized symbol");
        }
    }

}
