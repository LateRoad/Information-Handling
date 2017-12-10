package com.lateroad.informationhandling.exception;

public class TxtReaderException extends Exception{
    public TxtReaderException() {
    }

    public TxtReaderException(String message) {
        super(message);
    }

    public TxtReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TxtReaderException(Throwable cause) {
        super(cause);
    }

    public TxtReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
