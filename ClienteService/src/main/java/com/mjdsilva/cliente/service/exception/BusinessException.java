package com.mjdsilva.cliente.service.exception;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = 5606561473034160164L;

	public BusinessException(String msg) {
		super(msg);
	}

}
