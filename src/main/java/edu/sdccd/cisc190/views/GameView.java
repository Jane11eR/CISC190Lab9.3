package edu.sdccd.cisc190.views;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class GameView extends BaseScene {
    private final BaseScene leaderboardScene;

    public GameView(Stage stage, BaseScene leaderboardScene) {
        super(stage);
        this.leaderboardScene = leaderboardScene;
    }

    @Override
    public Parent getContent() {
        // TODO: finish game scene content
        return null;
    }
}
