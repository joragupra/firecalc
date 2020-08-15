package com.joragupra.gui;

import java.util.Optional;
import java.util.stream.StreamSupport;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InputTextField {
    public Label label;
    public TextField textField;
    private Iterable<IValidator> validators;
    public Label error;

    public InputTextField(String labelText, Iterable<IValidator> validators) {
        this.label = new Label(labelText);
        this.textField = new TextField();
        this.validators = validators;
        this.error = new Label();
        this.error.setVisible(false);
    }

    public void validate() {
        error.setVisible(false);
        var maybeValidationError =
                StreamSupport.stream(validators.spliterator(), false)
                        .map(validator -> validator.validate(textField))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst();
        maybeValidationError.ifPresent(
                validationError -> {
                    error.setText(validationError.getErrorMessage());
                    error.setVisible(true);
                });
    }

    public boolean isErrorVisible() {
        return error.isVisible();
    }

    public String getErrorMessage() {
        return error.getText();
    }
}
