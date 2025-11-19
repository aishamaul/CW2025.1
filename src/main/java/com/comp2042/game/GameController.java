package com.comp2042.game;

import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import com.comp2042.model.ClearRow;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.GameView;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GameViewAdapter viewAdapter;

    private final ScoreEvaluator scoreEvaluator;

    public GameController(GameView view) {
        this.scoreEvaluator = new ScoreEvaluator();
        this.viewAdapter = new GameViewAdapter(view);
        board.createNewBrick();
        this.viewAdapter.view.setEventListener(this);
        this.viewAdapter.initializeView(board.getBoardMatrix(), board.getViewData());
        this.viewAdapter.bindScore(board.getScore().scoreProperty());

    }

    private ClearRow handleGameOver(){
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            scoreEvaluator.scoreLineClear(clearRow.getScoreBonus(), board.getScore());
            viewAdapter.showScoreNotification(clearRow.getScoreBonus());
        }
        if (board.createNewBrick()) {
            viewAdapter.gameOver();
        }

        viewAdapter.refreshGameBackground(board.getBoardMatrix());
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
            viewAdapter.refreshBrick(board.getViewData());
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
        viewAdapter.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        viewAdapter.refreshBrick(board.getViewData());
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        viewAdapter.refreshBrick(board.getViewData());
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewAdapter.refreshGameBackground(board.getBoardMatrix());
    }
}
