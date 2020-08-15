package com.joragupra.gui;

import java.util.Optional;
import javafx.scene.control.TextField;

public interface IValidator {
    Optional<ValidationError> validate(TextField field);
}
