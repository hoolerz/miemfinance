package com.hse.miemfinance.model.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

	private String errorMessage;

	public BusinessException() {
		super();
		this.errorMessage = "";
	}

	public BusinessException withMessage(String message) {
		this.errorMessage = message;
		return this;
	}

}
