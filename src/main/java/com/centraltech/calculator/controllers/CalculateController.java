package com.centraltech.calculator.controllers;

import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centraltech.calculator.dtos.CalculateRequestDTO;
import com.centraltech.calculator.services.CalculateService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculateController {

	private final CalculateService calculateService;

	@ApiOperation("API to evaluate an arithmetic string expression following BODMAS principle"
			+ " and returns double result on successfull attempt")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfull calculation"),
			@ApiResponse(code = 500, message = "Invalid symbol or operation") })
	@PostMapping
	public ResponseEntity<Double> calculate(@RequestBody @Valid 
			CalculateRequestDTO calculateRequestDTO) {

		log.info("Evaluating arithmetic expression for {}", calculateRequestDTO);
		return new ResponseEntity<>(calculateService.calculate(calculateRequestDTO), 
				HttpStatus.CREATED);
	}

}
