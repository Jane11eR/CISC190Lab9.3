package edu.sdccd.cisc190.services;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Labeled;

public class TimerService extends Service<Void> {
    private long startTime;
    private final Labeled node;
    private boolean isRunning;

    public TimerService(Labeled node) {
        this.node = node;
        this.node.setText("0.0");
        isRunning = true;
    }

    public void stopTimer() {
        isRunning = false;
    }


    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                startTime = System.currentTimeMillis();
                float elapsedTime = 0;
                while (isRunning) {
                    elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
                    Thread.sleep(100);
                    float finalElapsedTime = elapsedTime;
                    Platform.runLater(() -> node.setText(String.format("%.1f", finalElapsedTime)));
                }
                return null;
            }
        };
    }
}
