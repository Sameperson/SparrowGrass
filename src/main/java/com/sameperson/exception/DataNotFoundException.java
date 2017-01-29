package com.sameperson.exception;


public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6427491629933200598L;

    public DataNotFoundException(String message) {
        super(message);
    }


}
