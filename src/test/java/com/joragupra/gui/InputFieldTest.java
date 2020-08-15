package com.joragupra.gui;

import static com.joragupra.gui.GUITestHelper.setHeadlessTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class InputFieldTest {

    private static final String INPUT_FIELD_LABEL = "Input: ";
    private static final String ALWAYS_FAILING_VALIDATOR_ERROR_MESSAGE = "There was an error";

    @Mock(lenient = true)
    private IValidator alwaysFailingValidator;

    @Mock(lenient = true)
    private IValidator alwaysSucceedingValidator;

    @BeforeAll
    public static void setup() {
        setHeadlessTest();
    }

    @BeforeEach
    public void initializeValidators() {
        when(alwaysFailingValidator.validate(any(TextField.class)))
                .thenReturn(
                        Optional.of(new ValidationError(ALWAYS_FAILING_VALIDATOR_ERROR_MESSAGE)));
        when(alwaysSucceedingValidator.validate(any(TextField.class))).thenReturn(Optional.empty());
    }

    @Test
    public void shouldShowErrorMessage_WhenAValidationFails() {
        InputTextField input =
                new InputTextField(
                        INPUT_FIELD_LABEL,
                        List.of(
                                alwaysSucceedingValidator,
                                alwaysFailingValidator,
                                alwaysSucceedingValidator));

        input.validate();

        assertTrue(input.isErrorVisible());
    }

    @Test
    public void shouldShowSameErrorMessageAsFirstValidatorErrorMessage_WhenAValidationFails(
            @Mock IValidator validator) {
        lenient()
                .when(validator.validate(any(TextField.class)))
                .thenReturn(Optional.of(new ValidationError("Another error message")));
        InputTextField input =
                new InputTextField(
                        INPUT_FIELD_LABEL,
                        List.of(alwaysSucceedingValidator, alwaysFailingValidator, validator));

        input.validate();

        assertEquals(ALWAYS_FAILING_VALIDATOR_ERROR_MESSAGE, input.getErrorMessage());
    }

    @Test
    public void shouldNotShowErrorMessage_BeforeValidatingField(@Mock IValidator validator) {
        InputTextField input = new InputTextField(INPUT_FIELD_LABEL, List.of(validator));

        assertFalse(input.isErrorVisible());
    }

    @Test
    public void shouldNotShowErrorMessage_WhenValidationAllValidationsSucceed() {
        InputTextField input =
                new InputTextField(
                        INPUT_FIELD_LABEL,
                        List.of(alwaysSucceedingValidator, alwaysSucceedingValidator));

        input.validate();

        assertFalse(input.isErrorVisible());
    }
}
