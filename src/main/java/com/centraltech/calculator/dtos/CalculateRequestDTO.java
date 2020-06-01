package com.centraltech.calculator.dtos;

import java.util.List;

import com.centraltech.calculator.annotations.ListNotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequestDTO {
	
	@ListNotEmpty(message = "Numbers may not be empty")
	private List<Integer> numbers;
	
	@ListNotEmpty(message = "Operators may not be empty")
	private List<String> operators;

}
