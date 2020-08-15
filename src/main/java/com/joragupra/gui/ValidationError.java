package com.joragupra.gui;

public class ValidationError {

    private String message;

    public ValidationError(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
