package edu.sdccd.cisc190;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static Properties config;

    public static void main(String[] args) {
        config = loadConfigFile();

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
        // TODO: load GameView and showScene()
    }
}
