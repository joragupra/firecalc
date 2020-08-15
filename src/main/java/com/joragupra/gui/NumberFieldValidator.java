package com.joragupra.gui;

public class NumberFieldValidator extends BaseValidator implements IValidator {
    private static final String ERROR_MESSAGE = "Field must be a number";

    @Override
    protected boolean isValid(String fieldValue) {
        try {
            Long.parseLong(fieldValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
