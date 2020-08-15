package com.joragupra;

import static com.joragupra.gui.GUITestHelper.setHeadlessTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    private void setDefaultInputValues(MainPane mainPane) {
        mainPane.portfolioAmountInput.setText("0");
        mainPane.yearlySpendingInput.setText("0");
        mainPane.retirementYearInput.setText("2020");
        mainPane.retirementYearEndInput.setText("2050");
    }
}
