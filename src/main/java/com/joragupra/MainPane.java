package com.joragupra;

import com.joragupra.fire.IFIRESimulator;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    TextField retirementYearInput;
    TextField retirementYearEndInput;
    TextField portfolioAmountInput;
    TextField yearlySpendingInput;

    IFIRESimulator simulator;
    Text simulationResult = new Text();

    MainPane(IFIRESimulator simulator) {
        this.simulator = simulator;

        createInputFields();

        this.add(createRunSimulationButton(simulator), 0, 4);

        this.add(simulationResult, 0, 5);

        resetSimulationStatus();
    }

    private void resetSimulationStatus() {
        simulationStatus = SimulationStatus.NOT_RUN;
        showSimulationResult();
    }

    private void createInputFields() {
        var retirementYearLabel = new Label("Retirement year");
        retirementYearInput = new TextField();

        this.add(retirementYearLabel, 0, 0);
        this.add(retirementYearInput, 1, 0);

        var retirementYearEndLabel = new Label("Retirement year end");
        retirementYearEndInput = new TextField();

        this.add(retirementYearEndLabel, 0, 1);
        this.add(retirementYearEndInput, 1, 1);

        var portfolioAmountLabel = new Label("Portfolio amount");
        portfolioAmountInput = new TextField();

        this.add(portfolioAmountLabel, 0, 2);
        this.add(portfolioAmountInput, 1, 2);

        var yearlySpendingLabel = new Label("Yearly spending");
        yearlySpendingInput = new TextField();

        this.add(yearlySpendingLabel, 0, 3);
        this.add(yearlySpendingInput, 1, 3);
    }

    private Button createRunSimulationButton(IFIRESimulator simulator) {
        var runSimulationButton = new Button("Run simulation");
        runSimulationButton.setOnAction(this::runSimulationAndUpdateResult);
        return runSimulationButton;
    }

    private void runSimulationAndUpdateResult(ActionEvent actionEvent) {
        runSimulation();
        showSimulationResult();
    }

    private void runSimulation() {
        if (simulator.simulate(
                Money.of(Long.parseLong(portfolioAmountInput.getText()), "EUR"),
                Money.of(Long.parseLong(yearlySpendingInput.getText()), "EUR"),
                LocalDate.of(Integer.parseInt(retirementYearInput.getText()), 1, 1),
                LocalDate.of(Integer.parseInt(retirementYearEndInput.getText()), 1, 1))) {
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

    void simulate() {
        runSimulation();
        showSimulationResult();
    }
}
