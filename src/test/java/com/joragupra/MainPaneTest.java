package com.joragupra;

import static com.joragupra.gui.GUITestHelper.setHeadlessTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.joragupra.fire.IFIRESimulator;
import java.time.LocalDate;
import javax.money.MonetaryAmount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class MainPaneTest {

    @BeforeAll
    public static void setup() {
        setHeadlessTest();
    }

    @Test
    public void shouldShowSuccessMessage_WhenSimulatorReturnsTrue(@Mock IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(true);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);

        mainPane.simulate();

        assertEquals("Successful", mainPane.simulationResult.getText());
    }

    @Test
    public void shouldShowFailedMessage_WhenSimulatorReturnsFalse(@Mock IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);

        mainPane.simulate();

        assertEquals("Failed", mainPane.simulationResult.getText());
    }

    @Test
    public void shouldSimulate_WhenAllValidationsSucceed(@Mock IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);

        mainPane.simulate();

        verify(simulator, times(1))
                .simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class));
    }

    @Test
    public void shouldNotSimulate_WhenAValidationsFails(
            @Mock(lenient = true) IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);
        mainPane.portfolioAmountInputTextField.textField.setText("");

        mainPane.simulate();

        verify(simulator, times(0))
                .simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class));
    }

    @Test
    public void shouldValidatePortfolioAmountIsPresent(
            @Mock(lenient = true) IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);
        mainPane.portfolioAmountInputTextField.textField.setText("");

        mainPane.simulate();

        assertTrue(mainPane.portfolioAmountInputTextField.isErrorVisible());
    }

    @Test
    public void shouldValidatePortfolioAmountIsANumber(
            @Mock(lenient = true) IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);
        mainPane.portfolioAmountInputTextField.textField.setText("abc");

        mainPane.simulate();

        assertTrue(mainPane.portfolioAmountInputTextField.isErrorVisible());
    }

    @Test
    public void shouldValidateYearlySpendingIsPresent(
            @Mock(lenient = true) IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);
        mainPane.yearlySpendingInputTextField.textField.setText("");

        mainPane.simulate();

        assertTrue(mainPane.yearlySpendingInputTextField.isErrorVisible());
    }

    @Test
    public void shouldValidateYearlySpendingIsANumber(
            @Mock(lenient = true) IFIRESimulator simulator) {
        when(simulator.simulate(
                        any(MonetaryAmount.class),
                        any(MonetaryAmount.class),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(false);
        var mainPane = new MainPane(simulator);
        setDefaultInputValues(mainPane);
        mainPane.yearlySpendingInputTextField.textField.setText("abc");

        mainPane.simulate();

        assertTrue(mainPane.yearlySpendingInputTextField.isErrorVisible());
    }

    private void setDefaultInputValues(MainPane mainPane) {
        mainPane.portfolioAmountInputTextField.textField.setText("0");
        mainPane.yearlySpendingInputTextField.textField.setText("0");
        mainPane.retirementYearInputTextField.textField.setText("2020");
        mainPane.retirementYearEndInputTextField.textField.setText("2050");
    }
}
