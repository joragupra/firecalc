package com.joragupra.gui;

public class MandatoryFieldValidator extends BaseValidator implements IValidator {
    private static final String ERROR_MESSAGE = "Field must not be empty";

    @Override
    protected boolean isValid(String fieldValue) {
        return !"".equals(fieldValue.trim());
    }

    @Override
    protected String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
