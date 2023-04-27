package com.atu.monitor.exception;

/**
 * @author: Tom
 * @create: 2023-04-25 13:24
 * @Description:
 */
public class ParamsRuntimeException extends RuntimeException {
    public ParamsRuntimeException() {
    }

    public ParamsRuntimeException(String message) {
        super(message);
    }

    public ParamsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsRuntimeException(Throwable cause) {
        super(cause);
    }

    public ParamsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}