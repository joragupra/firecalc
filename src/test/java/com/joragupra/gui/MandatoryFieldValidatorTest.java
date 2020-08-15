package com.joragupra.gui;

import static com.joragupra.gui.GUITestHelper.setHeadlessTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class MandatoryFieldValidatorTest {

    private MandatoryFieldValidator validator = new MandatoryFieldValidator();

    @BeforeAll
    public static void setup() {
        setHeadlessTest();
    }

    @Test
    public void shouldReturnNoError_WhenFieldIsNotEmpty() {
        TextField field = new TextField("abcd");

        assertTrue(validator.validate(field).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\t\t\t"})
    public void shouldReturnError_WhenFieldIsEmpty(String fieldValue) {
        TextField field = new TextField(fieldValue);

        assertTrue(validator.validate(field).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\t\t\t"})
    public void shouldReturnErrorMessage_WhenFieldIsEmpty(String fieldValue) {
        TextField field = new TextField(fieldValue);

        assertEquals("Field must not be empty", validator.validate(field).get().getErrorMessage());
    }
}
