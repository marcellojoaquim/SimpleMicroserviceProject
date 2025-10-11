package com.mjsilva.vendas.exception.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import com.mjsilva.vendas.exception.BusinessException;

import lombok.Getter;

@Getter
public class ApiErrors {
	
List<String> errors;
	
	public ApiErrors(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
	}
	
	public ApiErrors(ResponseStatusException ex) {
		this.errors = Arrays.asList(ex.getReason());
	}
	
	public ApiErrors(BusinessException businessException) {
		this.errors = Arrays.asList(businessException.getMessage());
	}
}