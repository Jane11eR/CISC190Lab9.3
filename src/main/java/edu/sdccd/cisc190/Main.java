package edu.sdccd.cisc190;

import edu.sdccd.cisc190.repositories.LeaderboardRepository;
import edu.sdccd.cisc190.views.GameView;
import edu.sdccd.cisc190.views.LeaderboardView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static Properties config;
    public static LeaderboardRepository leaderboardRepository;
    public static GameView gameView;
    public static LeaderboardView leaderboardView;


    public static void main(String[] args) {
        config = loadConfigFile();
        launch(args);
    }

    public static Properties loadConfigFile() {
        Properties config = new Properties();

        try {
            config.load(Main.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return config;
    }

    @Override
    public void start(Stage stage) throws Exception {
        leaderboardRepository = LeaderboardRepository.getInstance(config);
        leaderboardView = new LeaderboardView(stage, leaderboardRepository);
        gameView = new GameView(stage, leaderboardView);
        gameView.showScene();
    }
}
