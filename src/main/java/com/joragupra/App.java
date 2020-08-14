package com.joragupra;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private enum SimulationStatus {
        SUCCESSFUL,
        FAILED,
        NOT_RUN
    };

    private SimulationStatus simulationStatus;

    private final Text simulationResult = new Text();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        var title = "F.I.R.E. calculator by joragupra";

        resetSimulationStatus();

        var mainPane = createInputFields();

        mainPane.add(createRunSimulationButton(), 0, 4);

        mainPane.add(simulationResult, 0, 5);

        var scene = new Scene(mainPane, 640, 480);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    private Button createRunSimulationButton() {
        var runSimulationButton = new Button("Run simulation");
        runSimulationButton.setOnAction(this::runSimulationAndUpdateResult);
        return runSimulationButton;
    }

    private GridPane createInputFields() {
        var inputGrid = new GridPane();

        var retirementYearLabel = new Label("Retirement year");
        var retirementYearInput = new TextField();

        inputGrid.add(retirementYearLabel, 0, 0);
        inputGrid.add(retirementYearInput, 1, 0);

        var retirementYearEndLabel = new Label("Retirement year end");
        var retirementYearEndInput = new TextField();

        inputGrid.add(retirementYearEndLabel, 0, 1);
        inputGrid.add(retirementYearEndInput, 1, 1);

        var portfolioAmountLabel = new Label("Portfolio amount");
        var portfolioAmountInput = new TextField();

        inputGrid.add(portfolioAmountLabel, 0, 2);
        inputGrid.add(portfolioAmountInput, 1, 2);

        var yearlySpendingLabel = new Label("Yearly spending");
        var yearlySpendingInput = new TextField();

        inputGrid.add(yearlySpendingLabel, 0, 3);
        inputGrid.add(yearlySpendingInput, 1, 3);

        return inputGrid;
    }

    private void resetSimulationStatus() {
        simulationStatus = SimulationStatus.NOT_RUN;
        showSimulationResult();
    }

    private void runSimulationAndUpdateResult(ActionEvent actionEvent) {
        runSimulation();
        showSimulationResult();
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

    private void runSimulation() {
        var simResult = Math.random();
        if (simResult > 0.5) {
            simulationStatus = SimulationStatus.SUCCESSFUL;
        } else {
            simulationStatus = SimulationStatus.FAILED;
        }
    }
}
