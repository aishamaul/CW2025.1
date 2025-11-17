package com.comp2042.ui;

import com.comp2042.game.GameLoopManager;
import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.EventType;
import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import com.comp2042.model.DownData;
import com.comp2042.model.ViewData;


import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable, GameView {

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private BorderPane gameBoard;

    @FXML
    private ToggleButton pauseButton;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private Label scoreLabel;

    private InputEventListener eventListener;

    private GameLoopManager gameLoopManager;

    private GameRenderer gameRenderer;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        this.gameRenderer = new GameRenderer(gameBoard, gamePanel, brickPanel);

        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    @Override
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initGameView(boardMatrix, brick);

        gamePanel.setOnKeyPressed(new InputHandler(this, eventListener, isPause, isGameOver, this::moveDown, () -> newGame(null), () -> pauseGame(null)));

        gameLoopManager = new GameLoopManager(() -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD)));
        gameLoopManager.play();
    }



    @Override
    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            gameRenderer.refreshBrick(brick);
        }
    }

    @Override
    public void refreshGameBackground(int[][] board) {
        gameRenderer.refreshGameBackground(board);
    }

    // change from private to public so the InputHandler can pass the reference
    public void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData;

            if (event.getEventType() == EventType.DROP) {
                downData = eventListener.onDropEvent(event);
            } else{
                downData = eventListener.onDownEvent(event);
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    @Override
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString());
    }

    @Override
    public void gameOver() {
        gameLoopManager.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    @Override
    public void showScoreNotification(String text) {
        NotificationPanel notificationPanel = new NotificationPanel(text);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }

    public void newGame(ActionEvent actionEvent) {
        gameLoopManager.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        gameLoopManager.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);

        pauseButton.setDisable(false);
        pauseButton.setSelected(false);
    }

    @FXML
    public void pauseGame(ActionEvent actionEvent) {
        if (pauseButton.isSelected()) {
            isPause.setValue(Boolean.TRUE);
            gameLoopManager.pause();

            pauseButton.getStyleClass().remove("pauseButton");
            if(!pauseButton.getStyleClass().contains("playButton")) {
                pauseButton.getStyleClass().add("playButton");
            }
        }else {
            isPause.setValue(Boolean.FALSE);
            gameLoopManager.play();

            pauseButton.getStyleClass().remove("playButton");
            if(!pauseButton.getStyleClass().contains("pauseButton")) {
                pauseButton.getStyleClass().add("pauseButton");
            }
        }
        gamePanel.requestFocus();

    }
}
