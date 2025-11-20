package com.comp2042.ui.components;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class NotificationAnimator {

    private static final Duration FADE_DURATION = Duration.millis(2000);
    private static final Duration TRANSLATE_DURATION = Duration.millis(2500);
    private static final double VERTICAL_OFFSET = 40.0;

    public void animate(Node notificationNode, Runnable onFinished) {
        FadeTransition fadeTransition = new FadeTransition(FADE_DURATION, notificationNode);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        TranslateTransition translateTransition = new TranslateTransition(TRANSLATE_DURATION, notificationNode);
        translateTransition.setToY(notificationNode.getLayoutY() - VERTICAL_OFFSET);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
        parallelTransition.setOnFinished(event -> onFinished.run());
        parallelTransition.play();
    }
}
