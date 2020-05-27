package com.centraltech.calculator;


import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.centraltech.calculator.dtos.CalculateRequestDTO;
import com.centraltech.calculator.services.CalculateService;
import com.centraltech.calculator.services.CalculateServiceImplementation;

@ExtendWith(MockitoExtension.class)
class CalculatorApplicationTests {

	@InjectMocks
	private CalculateService calculateService = new CalculateServiceImplementation();;

	@Test
	void ShouldCalculateResultSuccessfully() {
		
		double calculatedResult = calculateService.calculate(CalculateRequestDTO.builder()
				.expression("2-16-7*4/3+5").build());
		double expectedResult = -18.333333333333336;
		System.err.println(calculatedResult);
		
		Assertions.assertEquals(expectedResult, calculatedResult);
		
	}

	@Test
	public void shouldThrowExceptionWhenDividedByZero() {

		CalculateRequestDTO calculateRequestDTO = 
				CalculateRequestDTO.builder().expression("2-16-7*4/0+5").build();

		assertThrows(ArithmeticException.class, () -> {
			calculateService.calculate(calculateRequestDTO);
		});
	}

}
