package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.Main;
import edu.sdccd.cisc190.model.PlayerScore;
import edu.sdccd.cisc190.repositories.LeaderboardRepository;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LeaderboardView extends BaseScene {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardView.class);
    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardView(Stage stage, LeaderboardRepository leaderboardRepository) {
        super(stage);
        this.leaderboardRepository = leaderboardRepository;
    }

    @Override
    public Parent getContent() {
        stage.setTitle("High Scores");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label playerNameLabel = new Label("Player Name");
        Label scoreLabel = new Label("Scores");
        Button quitButton = new Button("Quit");

        quitButton.setOnAction(event -> {
            Platform.exit();
        });
        grid.add(playerNameLabel, 0, 0);
        grid.add(scoreLabel, 1, 0);

        try {
            leaderboardRepository.loadLeaderboard();
            leaderboardRepository.addPlayerScore(new PlayerScore(Main.gameView.getPlayerName(), Main.gameView.getPlayerScore()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        int row = 1;
        for(PlayerScore score: leaderboardRepository.getLeaderboard().getPlayerScores()) {
            if(score != null) {
                grid.add(new Label(score.getPlayerName()), 0, row);
                grid.add(new Label(String.format("%.1f", score.getScore())), 1, row);
                row++;
            }
        }

        grid.add(quitButton, 1, row);

        return new VBox(grid);
    }
}
