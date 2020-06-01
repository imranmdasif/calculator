package com.centraltech.calculator.services;

import com.centraltech.calculator.dtos.CalculateRequestDTO;

public interface CalculateService {

    /**
     * 
     * This function evaluate an arithmetic string expression following BODMAS principle 
     * and returns double result.
     * 
     * @param calculateRequestDTO - string expression.
     *
     * @return - calculated double result.
     * @throws Exception 
     */
	double calculate(CalculateRequestDTO calculateRequestDTO) throws Exception;
}
