package com.mjdsilva.cliente.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.mjdsilva.cliente.service.exception.errors.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationExceptions(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		return new ApiErrors(bindingResult);
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleBusinessException(BusinessException businessException) {
		return new ApiErrors(businessException);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity handlerResponseStatusException(ResponseStatusException exception) {
		return new ResponseEntity(new ApiErrors(exception), exception.getStatusCode());
	}
}
