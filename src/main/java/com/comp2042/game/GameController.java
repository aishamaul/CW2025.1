package com.comp2042.game;

import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import com.comp2042.model.ClearRow;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.GameView;
import com.comp2042.ui.GuiController;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GameView view;

    private final ScoreEvaluator scoreEvaluator;

    public GameController(GameView view) {
        this.view = view;
        board.createNewBrick();
        this.view.setEventListener(this);
        this.view.initGameView(board.getBoardMatrix(), board.getViewData());
        this.view.bindScore(board.getScore().scoreProperty());
        this.scoreEvaluator = new ScoreEvaluator();
    }

    private ClearRow handleGameOver(){
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            scoreEvaluator.scoreLineClear(clearRow.getScoreBonus(), board.getScore());
            view.showScoreNotification("+" + clearRow.getScoreBonus());
        }
        if (board.createNewBrick()) {
            view.gameOver();
        }

        view.refreshGameBackground(board.getBoardMatrix());
        return clearRow;
    }


    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            clearRow = handleGameOver();

        } else {
            scoreEvaluator.scoreMovement(event, board.getScore());
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public DownData onDropEvent(MoveEvent event){
        int rowsDropped = board.dropBrick();
        scoreEvaluator.scoreDrop(rowsDropped, board.getScore());

        ClearRow clearRow = handleGameOver();

        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        view.refreshGameBackground(board.getBoardMatrix());
    }
}
