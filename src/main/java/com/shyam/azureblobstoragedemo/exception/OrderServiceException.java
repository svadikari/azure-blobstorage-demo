package com.shyam.azureblobstoragedemo.exception;

public class OrderServiceException extends RuntimeException {

    public OrderServiceException(Throwable throwable) {
        super(throwable);
    }

    public OrderServiceException(String msg) {
        super(msg);
    }

    public OrderServiceException(String msg, Exception exp) {
        super(msg, exp);
    }
}
