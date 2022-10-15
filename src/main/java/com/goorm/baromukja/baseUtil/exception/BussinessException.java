package com.goorm.baromukja.baseUtil.exception;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

public class BussinessException extends RuntimeException {
    public BussinessException() {
        super();
    }

    public BussinessException(ExMessage exMessage) {
        super(exMessage.getMessage());
    }

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinessException(Throwable cause) {
        super(cause);
    }
}