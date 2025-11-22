package com.comp2042.game.core;

import com.comp2042.model.NextShapeInfo;
import com.comp2042.game.bricks.Brick;

public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    public boolean tryRotate(BoardGrid grid, Point currentOffset) {
        NextShapeInfo nextShapeInfo = getNextShape();
        int[][] nextMatrix = nextShapeInfo.getShape();
        int currentX = (int) currentOffset.getX();
        int currentY = (int) currentOffset.getY();

        int[] kickOffsets = {0, 1, -1, 2, -2};

        for (int kick : kickOffsets) {
            int newX = currentX + kick;
            if (!grid.intersects(nextMatrix, newX, currentY)) {
                setCurrentShape(nextShapeInfo.getPosition());
                currentOffset.translate(kick,0);
                return true;
            }
        }
        return false;
    }

    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }


}
