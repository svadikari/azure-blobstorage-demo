package com.shyam.azureblobstoragedemo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidInputDataException(String message) {
        super(message);
    }

    public InvalidInputDataException(String string, Exception ex) {
        super(string, ex);
    }
}
