package com.goorm.baromukja.baseUtil.exception;

public class CustomJwtException extends SecurityException {
	public CustomJwtException() {
		super();
	}

	public CustomJwtException(ExMessage message) {
		super(message.getMessage());
	}

	public CustomJwtException(String s) {
		super(s);
	}

	public CustomJwtException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomJwtException(Throwable cause) {
		super(cause);
	}
}
