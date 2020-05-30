package com.centraltech.calculator.services;

public class Devide implements Arithmatic {

	@Override
	public double calculate(double a, double b) {
		if (b == 0) {
			throw new ArithmeticException("Cannot divide by zero");
		} else{
			return a / b;
		}
	}

}
