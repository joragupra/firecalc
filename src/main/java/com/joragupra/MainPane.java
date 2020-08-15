package com.joragupra;

import com.joragupra.fire.IFIRESimulator;
import com.joragupra.gui.InputTextField;
import com.joragupra.gui.MandatoryFieldValidator;
import com.joragupra.gui.NumberFieldValidator;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.javamoney.moneta.Money;

class MainPane extends GridPane {

    private enum SimulationStatus {
        SUCCESSFUL,
        FAILED,
        NOT_RUN
    };

    private SimulationStatus simulationStatus;
    InputTextField retirementYearInputTextField;
    InputTextField retirementYearEndInputTextField;
    InputTextField portfolioAmountInputTextField;
    InputTextField yearlySpendingInputTextField;
    private List<InputTextField> inputTextFields;

    IFIRESimulator simulator;
    Text simulationResult = new Text();

    MainPane(IFIRESimulator simulator) {
        this.simulator = simulator;

        createInputFields();
        inputTextFields = List.of(portfolioAmountInputTextField, yearlySpendingInputTextField);

        this.add(createRunSimulationButton(simulator), 0, 4);

        this.add(simulationResult, 0, 5);

        resetSimulationStatus();
    }

    private void resetSimulationStatus() {
        simulationStatus = SimulationStatus.NOT_RUN;
        showSimulationResult();
    }

    private void createInputFields() {
        retirementYearInputTextField =
                new InputTextField("Retirement year", Collections.emptyList());
        this.add(arrangeInputField(retirementYearInputTextField), 0, 0);

        retirementYearEndInputTextField =
                new InputTextField("Retirement year end", Collections.emptyList());
        this.add(arrangeInputField(retirementYearEndInputTextField), 0, 1);

        portfolioAmountInputTextField =
                new InputTextField(
                        "Portfolio amount",
                        List.of(new MandatoryFieldValidator(), new NumberFieldValidator()));
        this.add(arrangeInputField(portfolioAmountInputTextField), 0, 2);

        yearlySpendingInputTextField =
                new InputTextField(
                        "Yearly spending",
                        List.of(new MandatoryFieldValidator(), new NumberFieldValidator()));
        this.add(arrangeInputField(yearlySpendingInputTextField), 0, 3);
    }

    private Button createRunSimulationButton(IFIRESimulator simulator) {
        var runSimulationButton = new Button("Run simulation");
        runSimulationButton.setOnAction(this::runSimulationAndUpdateResult);
        return runSimulationButton;
    }

    private void runSimulationAndUpdateResult(ActionEvent actionEvent) {
        if (validateAllInputs()) {
            runSimulation();
            showSimulationResult();
        }
    }

    private boolean validateAllInputs() {
        inputTextFields.forEach(InputTextField::validate);
        return inputTextFields.stream().noneMatch(InputTextField::isErrorVisible);
    }

    private void runSimulation() {
        if (simulator.simulate(
                Money.of(Long.parseLong(portfolioAmountInputTextField.textField.getText()), "EUR"),
                Money.of(Long.parseLong(yearlySpendingInputTextField.textField.getText()), "EUR"),
                LocalDate.of(
                        Integer.parseInt(retirementYearInputTextField.textField.getText()), 1, 1),
                LocalDate.of(
                        Integer.parseInt(retirementYearEndInputTextField.textField.getText()),
                        1,
                        1))) {
            simulationStatus = SimulationStatus.SUCCESSFUL;
        } else {
            simulationStatus = SimulationStatus.FAILED;
        }
    }

    private void showSimulationResult() {
        switch (simulationStatus) {
            case SUCCESSFUL:
                simulationResult.setText("Successful");
                simulationResult.setFill(Color.DARKGREEN);
                break;
            case FAILED:
                simulationResult.setText("Failed");
                simulationResult.setFill(Color.INDIANRED);
                break;
            case NOT_RUN:
                simulationResult.setText("");
                break;
        }
    }

    private Pane arrangeInputField(InputTextField inputTextField) {
        return new HBox(inputTextField.label, inputTextField.textField, inputTextField.error);
    }

    void simulate() {
        if (validateAllInputs()) {
            runSimulation();
            showSimulationResult();
        }
    }
}
