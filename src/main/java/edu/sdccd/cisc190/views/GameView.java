package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.services.TimerService;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView extends BaseScene {
    private String playerName;
    private float playerScore;
    private final BaseScene leaderboardScene;

    public GameView(Stage stage, BaseScene leaderboardScene) {
        super(stage);
        this.leaderboardScene = leaderboardScene;
    }

    public String getPlayerName() {
        return playerName;
    }

    public float getPlayerScore() {
        return playerScore;
    }

    @Override
    public Parent getContent() {
        stage.setTitle("How fast can you type your name?");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label playerNameLabel = new Label("Player Name");
        TextField playerNameTextField = new TextField();
        Button submitButton = new Button();

        TimerService timer = new TimerService(submitButton);
        submitButton.setOnAction(event -> {
            playerName = playerNameTextField.getText();
            playerScore = Float.parseFloat(submitButton.getText());
            leaderboardScene.showScene();
            timer.stopTimer();
        });

        grid.add(playerNameLabel, 0, 0);
        grid.add(playerNameTextField, 1, 0);
        grid.add(submitButton, 2, 1);

        return new VBox(grid);
    }
}
