package com.comp2042.game;

import com.comp2042.model.ViewData;
import com.comp2042.ui.GameView;
import javafx.beans.property.IntegerProperty;

public class GameViewAdapter {

    public final GameView view;

    public GameViewAdapter(GameView view) {
        this.view = view;
    }

    public void initializeView(int[][] boardMatrix, ViewData brick) {
        this.view.initGameView(boardMatrix, brick);
    }

    public void bindScore(IntegerProperty scoreProperty) {
        view.bindScore(scoreProperty);
    }

    public void refreshGameBackground(int[][] board) {
        view.refreshGameBackground(board);
    }

    public void refreshBrick(ViewData brick) {
        view.refreshBrick(brick);
    }

    public void gameOver() {
        view.gameOver();
    }

    public void showScoreNotification(int scoreBonus) {
        view.showScoreNotification("+" + scoreBonus);
    }
}
