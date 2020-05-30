package com.centraltech.calculator.services;

import java.util.Stack;
import org.springframework.stereotype.Service;
import com.centraltech.calculator.dtos.CalculateRequestDTO;
import com.centraltech.calculator.enums.Operator;

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
		Stack<Operator> operators = new Stack<>();

		for (int i = 0; i < sequence.length; i++) {
			int number = parseNumber(expression, i);
			numbers.push((double) number);

			i += Integer.toString(number).length();
			if (i >= expression.length()) {
				break;
			}

			Operator op = parseOperator(expression, i);
			calculateTop(numbers, operators, op);
			operators.push(op);
		}

		calculateTop(numbers, operators, Operator.BLANK);
        if(numbers.size() == 1 && operators.isEmpty()){
            return numbers.pop();
        }

		return 0;
	}
	
    private void calculateTop(Stack<Double> numberStack, Stack<Operator> operatorStack, Operator futureTop){
        while(numberStack.size() >= 2 && operatorStack.size() >= 1){
            if(priorityOfOperator(futureTop) <= priorityOfOperator(operatorStack.peek())){
                double second = numberStack.pop();
                double first = numberStack.pop();
                Operator op = operatorStack.pop();
                double result = operate(first, op, second);
                numberStack.push(result);
            } else{
                break;
            }
        }
    }
	
	private int parseNumber(String sequence, int offset) {
		StringBuilder sb = new StringBuilder();
		while (offset < sequence.length() && Character.isDigit(sequence.charAt(offset))) {
			sb.append(sequence.charAt(offset++));
		}
		return Integer.parseInt(sb.toString());
	}
	
	private Operator parseOperator(String sequence, int offset) {
		if (offset < sequence.length()) {
			char op = sequence.charAt(offset);
			switch (op) {
			case '+':
				return Operator.ADD;
			case '-':
				return Operator.SUBTRACT;
			case '*':
				return Operator.MULTIPLY;
			case '/':
				return Operator.DIVIDE;
			}
		}
		return Operator.BLANK;
	}
	
   private int priorityOfOperator(Operator op){
        switch (op){
            case ADD: return 1;
            case SUBTRACT: return 1;
            case MULTIPLY: return 2;
            case DIVIDE: return 2;
            case BLANK: return 0;
        }
        return 0;
    }

	public double operate(double left, Operator op, double right) {		
		Arithmatic arithmatic = arithmaticStrategyFactory.getArithmatic(op);
		return arithmatic.calculate(left, right);
	}


}
