package com.sonsure.commons.exception;

public class SonsureBeanException extends SonsureException {

    public SonsureBeanException(String message, Throwable e) {
        super(message, e);
    }

    public SonsureBeanException(String message) {
        super(message);
    }
}
