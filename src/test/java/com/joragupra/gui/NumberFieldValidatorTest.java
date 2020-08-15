package com.joragupra.gui;

import static com.joragupra.gui.GUITestHelper.setHeadlessTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class NumberFieldValidatorTest {

    private NumberFieldValidator validator = new NumberFieldValidator();

    @BeforeAll
    public static void setup() {
        setHeadlessTest();
    }

    @Test
    public void shouldReturnNoError_WhenFieldIsANumber() {
        TextField field = new TextField("12");

        assertTrue(validator.validate(field).isEmpty());
    }

    @Test
    public void shouldReturnError_WhenFieldIsNotANumber() {
        TextField field = new TextField("abcd");

        assertTrue(validator.validate(field).isPresent());
    }

    @Test
    public void shouldReturnErrorMessage_WhenFieldIsNotANumber() {
        TextField field = new TextField("abcd");

        assertEquals("Field must be a number", validator.validate(field).get().getErrorMessage());
    }
}
