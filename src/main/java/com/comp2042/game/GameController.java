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

    public GameController(GameView view) {
        this.view = view;
        board.createNewBrick();
        this.view.setEventListener(this);
        this.view.initGameView(board.getBoardMatrix(), board.getViewData());
        this.view.bindScore(board.getScore().scoreProperty());
    }

    private ClearRow handleGameOver(){
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
            view.showScoreNotification("+" + clearRow.getScoreBonus());
        }
        if (board.createNewBrick()) {
            view.gameOver();
        }

        view.refreshGameBackground(board.getBoardMatrix());
        return clearRow;
    }

    private void userScore(MoveEvent event){
        if (event.getEventSource() == EventSource.USER) {
            board.getScore().add(1);
        }
    }


    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            clearRow = handleGameOver();

        } else {
            userScore(event);
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public DownData onDropEvent(MoveEvent event){
        int rowsDropped = board.dropBrick();
        board.getScore().add(rowsDropped * 2);

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
