package com.comp2042.game;

import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.EventType;
import com.comp2042.game.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoopManager {

    private final Timeline timeLine;

    public GameLoopManager(Runnable onTick) {
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> onTick.run()
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    public void play() {
        timeLine.play();
    }

    public void pause() {
        timeLine.pause();
    }

    public void stop() {
        timeLine.stop();
    }
}
