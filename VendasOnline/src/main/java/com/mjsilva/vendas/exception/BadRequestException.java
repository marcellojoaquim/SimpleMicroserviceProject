package com.mjsilva.vendas.exception;

public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -8891861550877766883L;

	public BadRequestException(String msg) {
		super(msg);
	}
}
