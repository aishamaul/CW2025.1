package com.comp2042.game.core;

import com.comp2042.ui.view.GameViewAdapter;
import com.comp2042.game.scoring.ScoreEvaluator;
import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.view.GameView;

public class GameController implements InputEventListener {

    private final Board board;
    private final GameLifecycleManager lifecycleManager;

    public GameController(Board board, GameLifecycleManager lifecycleManager) {
        this.board = board;
        this.lifecycleManager = lifecycleManager;

    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        if (board.moveBrickDown()){
            lifecycleManager.getScoreEvaluator().scoreMovement(event, board.getScore());
            lifecycleManager.getViewAdapter().refreshBrick(lifecycleManager.getViewData());
            return new DownData(null, lifecycleManager.getViewData());
        }else{
            return lifecycleManager.processTurnEnd();
        }
    }

    @Override
    public DownData onDropEvent(MoveEvent event){
        int rowsDropped = board.dropBrick();
        lifecycleManager.getScoreEvaluator().scoreDrop(rowsDropped, board.getScore());
        return lifecycleManager.processTurnEnd();
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        lifecycleManager.getViewAdapter().refreshBrick(lifecycleManager.getViewData());
        return lifecycleManager.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        lifecycleManager.getViewAdapter().refreshBrick(lifecycleManager.getViewData());
        return lifecycleManager.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        lifecycleManager.getViewAdapter().refreshBrick((lifecycleManager.getViewData()));
        return lifecycleManager.getViewData();
    }


    @Override
    public void createNewGame() {
        lifecycleManager.handleNewGame();
    }
}
