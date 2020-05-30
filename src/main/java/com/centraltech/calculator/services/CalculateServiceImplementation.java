package com.centraltech.calculator.services;

import java.util.Stack;
import org.springframework.stereotype.Service;
import com.centraltech.calculator.dtos.CalculateRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalculateServiceImplementation implements CalculateService {
	
	private final ArithmaticStrategyFactory arithmaticStrategyFactory = new ArithmaticStrategyFactory();
	
	
	@Override
	public double calculate(CalculateRequestDTO calculateRequestDTO) {
		
		String expression = calculateRequestDTO.getExpression();
		log.info("Initiating calculate request for : {}", expression);
		
		char[] sequence = expression.toCharArray();
		Stack<Double> numbers = new Stack<>();
		Stack<Character> operators = new Stack<>();

		for (int i = 0; i < sequence.length; i++) {

			if (Character.isDigit(sequence[i])) {
				int number = parseNumber(expression, i);
				numbers.push((double)number);

				i += Integer.toString(number).length() - 1;
				if (i >= expression.length()) {
					break;
				}
			} else if (sequence[i] == '+' || sequence[i] == '-' ||
					sequence[i] == '*' || sequence[i] == '/') {

				while (!operators.empty() && hasPrecedence(sequence[i], operators.peek())) {
					numbers.push(operate(operators.pop(), numbers.pop(), numbers.pop()));
				}
				operators.push(sequence[i]);
			}
		}

		while (!operators.empty()) {
			numbers.push(operate(operators.pop(), numbers.pop(), numbers.pop()));
		}
		
		double result = numbers.pop();
		log.info("Successfully calculated result {} for expression {} ",
				result, expression);

		return result;
	}
	
	private int parseNumber(String sequence, int offset) {
		StringBuilder sb = new StringBuilder();
		while (offset < sequence.length() && Character.isDigit(sequence.charAt(offset))) {
			sb.append(sequence.charAt(offset++));
		}
		return Integer.parseInt(sb.toString());
	}

	private boolean hasPrecedence(char op1, char op2) {
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
			return false;
		}
		return true;
	}

	public double operate(char op, double b, double a) {
		
		Arithmatic arithmatic = arithmaticStrategyFactory.getArithmatic(op);
		return arithmatic.calculate(a, b);
	}


}
