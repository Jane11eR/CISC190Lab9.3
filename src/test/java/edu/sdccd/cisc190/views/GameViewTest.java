package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.PositiveFloatMatcher;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

@ExtendWith(ApplicationExtension.class)
class GameViewTest {

    @Start
    public void start(Stage stage) {
        BaseScene scene = new GameView(stage, null);
        scene.showScene();
    }

    @Test
    void should_contain_button_with_positive_float(FxRobot robot) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        FxAssert.verifyThat(".button", LabeledMatchers.hasText(new PositiveFloatMatcher()));
    }
}