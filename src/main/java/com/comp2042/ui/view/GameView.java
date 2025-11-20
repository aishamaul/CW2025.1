package com.comp2042.ui.view;

import com.comp2042.game.events.InputEventListener;
import com.comp2042.model.ViewData;
import javafx.beans.property.IntegerProperty;

public interface GameView {

    void initGameView(int [][] boardMatrix, ViewData brick);

    void refreshGameBackground(int [][] board);

    void refreshBrick(ViewData brick);

    void gameOver();

    void bindScore(IntegerProperty scoreProperty);

    void setEventListener(InputEventListener eventListener);

    void showScoreNotification(String text);
}
