package edu.sdccd.cisc190.views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

abstract public class BaseScene {
    protected final Stage stage;

    public BaseScene(Stage stage) {
        this.stage = stage;
    }

    public void showScene() {
        stage.setScene(new Scene(getContent()));
        stage.show();
    }

    abstract public Parent getContent();
}
