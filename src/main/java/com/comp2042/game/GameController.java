package com.comp2042.game;

import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import com.comp2042.model.ClearRow;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;
import com.comp2042.ui.GameView;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);

    private final GameLifecycleManager lifecycleManager;

    public GameController(GameView view) {
        ScoreEvaluator scoreEvaluator = new ScoreEvaluator();
        GameViewAdapter viewAdapter = new GameViewAdapter(view);
        this.lifecycleManager = new GameLifecycleManager(board, scoreEvaluator, viewAdapter);

        board.createNewBrick();

        viewAdapter.view.setEventListener(this);
        viewAdapter.initializeView(board.getBoardMatrix(), lifecycleManager.getViewData());
        viewAdapter.bindScore(board.getScore().scoreProperty());

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
