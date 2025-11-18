package com.comp2042.ui;

import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.EventType;
import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import com.comp2042.model.ViewData;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.text.View;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InputHandler implements EventHandler<KeyEvent> {
    private final GameView gameView;
    private final EventDispatcher dispatcher;
    private final BooleanProperty isPause;
    private final BooleanProperty isGameOver;
    private final BiConsumer<EventType, EventSource> onMoveDown;
    private final Runnable onNewGame;
    private final Runnable onTogglePause;

    public InputHandler(GameView gameView,
                        EventDispatcher dispatcher,
                        BooleanProperty isPause,
                        BooleanProperty isGameOver,
                        BiConsumer<EventType, EventSource> onMoveDown,
                        Runnable onNewGame,
                        Runnable onTogglePause) {
        this.gameView = gameView;
        this.dispatcher = dispatcher;
        this.isPause = isPause;
        this.isGameOver = isGameOver;
        this.onMoveDown = onMoveDown;
        this.onNewGame = onNewGame;
        this.onTogglePause = onTogglePause;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.P) {
            onTogglePause.run();
            return;
        }
        if (keyEvent.getCode() == KeyCode.N) {
            onNewGame.run();
            return;
        }
        if (isPause.get() || isGameOver.get()) {
            return;
        }

        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
            gameView.refreshBrick(dispatcher.moveLeft());
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            gameView.refreshBrick(dispatcher.moveRight());
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            gameView.refreshBrick(dispatcher.rotate());
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            onMoveDown.accept(EventType.DOWN, EventSource.USER);
            keyEvent.consume();
        }

        if (keyEvent.getCode() == KeyCode.SPACE) {
            onMoveDown.accept(EventType.DROP, EventSource.USER);
            keyEvent.consume();
        }


    }
}