package com.joragupra.gui;

import java.util.Optional;
import javafx.scene.control.TextField;

public abstract class BaseValidator implements IValidator {
    @Override
    public Optional<ValidationError> validate(TextField field) {
        if (isValid(field.getText())) {
            return Optional.empty();
        }
        return Optional.of(new ValidationError(getErrorMessage()));
    }

    protected abstract boolean isValid(String fieldValue);

    protected abstract String getErrorMessage();
}
