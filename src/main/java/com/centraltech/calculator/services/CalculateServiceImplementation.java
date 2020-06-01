package com.centraltech.calculator.services;

import java.util.List;
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
	public double calculate(CalculateRequestDTO calculateRequestDTO) throws Exception {
		log.info("Initiating calculate request for : {}", calculateRequestDTO);
		
		if (calculateRequestDTO.getNumbers().size() != calculateRequestDTO.getOperators().size() + 1) {
			throw new Exception("Numbers and operator length doesn't match");
		}

		Stack<Double> numberStacks = new Stack<>();
		Stack<Operator> operatorStacks = new Stack<>();

		for (int i = 0; i < calculateRequestDTO.getNumbers().size(); i++) {
			numberStacks.push((double) calculateRequestDTO.getNumbers().get(i));

			if(i < calculateRequestDTO.getOperators().size()) {
				Operator op = parseOperator(calculateRequestDTO.getOperators(), i);
				calculateTop(numberStacks, operatorStacks, op);
				operatorStacks.push(op);
			}
		}

		calculateTop(numberStacks, operatorStacks, Operator.BLANK);
        if(numberStacks.size() == 1 && operatorStacks.isEmpty()){
            return numberStacks.pop();
        }

		return 0;
	}
	
	private void calculateTop(Stack<Double> numberStack, Stack<Operator> operatorStack, Operator futureTop) {
		while (numberStack.size() >= 2 && operatorStack.size() >= 1) {
			if (priorityOfOperator(futureTop) <= priorityOfOperator(operatorStack.peek())) {
				double second = numberStack.pop();
				double first = numberStack.pop();
				Operator op = operatorStack.pop();
				double result = operate(first, op, second);
				numberStack.push(result);
			} else {
				break;
			}
		}
	}
    
	private Operator parseOperator(List<String> operators, int offset) {
		String op = operators.get(offset);
		switch (op) {
		case "+": return Operator.ADD;
		case "-": return Operator.SUBTRACT;
		case "*": return Operator.MULTIPLY;
		case "/": return Operator.DIVIDE;
		default:  return Operator.BLANK;
		}
	}
	
	private int priorityOfOperator(Operator op) {
		switch (op) {
		case ADD: return 1;
		case SUBTRACT: return 1;
		case MULTIPLY: return 2;
		case DIVIDE: return 2;
		case BLANK: return 0;
		default: return 0;
		}
	}

	public double operate(double left, Operator op, double right) {
		Arithmatic arithmatic = arithmaticStrategyFactory.getArithmatic(op);
		return arithmatic.calculate(left, right);
	}


}
