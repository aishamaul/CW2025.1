package com.comp2042.ui;

import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.EventType;
import com.comp2042.game.events.InputEventListener;
import com.comp2042.game.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    private InputEventListener eventListener;

    private Timeline timeLine;

    private GameRenderer gameRenderer;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @FXML
    private Label scoreLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        this.gameRenderer = new GameRenderer(gameBoard, gamePanel, brickPanel);

        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }

                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        moveDown(new MoveEvent(EventType.DROP, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    pauseButton.setSelected(!pauseButton.isSelected());
                    pauseGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    @Override
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gameRenderer.initGameView(boardMatrix, brick);

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
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

    private void moveDown(MoveEvent event) {
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
        timeLine.stop();
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
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);

        pauseButton.setDisable(false);
        pauseButton.setSelected(false);
    }

    @FXML
    public void pauseGame(ActionEvent actionEvent) {
        if (pauseButton.isSelected()) {
            isPause.setValue(Boolean.TRUE);
            timeLine.pause();

            pauseButton.getStyleClass().remove("pauseButton");
            if(!pauseButton.getStyleClass().contains("playButton")) {
                pauseButton.getStyleClass().add("playButton");
            }
        }else {
            isPause.setValue(Boolean.FALSE);
            timeLine.play();

            pauseButton.getStyleClass().remove("playButton");
            if(!pauseButton.getStyleClass().contains("pauseButton")) {
                pauseButton.getStyleClass().add("pauseButton");
            }
        }
        gamePanel.requestFocus();

    }
}
