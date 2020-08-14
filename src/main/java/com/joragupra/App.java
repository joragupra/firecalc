package com.joragupra;

import com.joragupra.fire.CashPortfolioSimulator;
import com.joragupra.fire.IFIRESimulator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        var title = "F.I.R.E. calculator by joragupra";

        var scene = new Scene(createMainPane(new CashPortfolioSimulator()), 640, 480);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    private Pane createMainPane(IFIRESimulator simulator) {
        return new MainPane(simulator);
    }
}
