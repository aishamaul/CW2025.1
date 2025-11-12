package com.comp2042.ui;

import com.comp2042.model.ViewData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameRenderer {
    private static final int BRICK_SIZE = 20;

    private final BorderPane gameBoard;

    private final GridPane gamePanel;
    private final GridPane brickPanel;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;


    public GameRenderer(BorderPane gameBoard, GridPane gamePanel, GridPane brickPanel) {
        this.gameBoard = gameBoard;
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        refreshBrick(brick);
    }

    public void refreshBrick(ViewData brick) {
        brickPanel.setLayoutX(gameBoard.getLayoutX() + gamePanel.getLayoutX() + (brick.getxPosition() * (BRICK_SIZE + brickPanel.getHgap())));
        brickPanel.setLayoutY(gameBoard.getLayoutY() + gamePanel.getLayoutY() + (brick.getyPosition() - 2) * (BRICK_SIZE + brickPanel.getVgap()));
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
            }
        }
    }


    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }


    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.ORANGE;
                break;
            case 2:
                returnPaint = Color.ORANGERED;
                break;
            case 3:
                returnPaint = Color.YELLOW;
                break;
            case 4:
                returnPaint = Color.GREENYELLOW;
                break;
            case 5:
                returnPaint = Color.AQUA;
                break;
            case 6:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 7:
                returnPaint = Color.DEEPPINK;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }


}