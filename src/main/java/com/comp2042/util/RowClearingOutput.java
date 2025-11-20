package com.comp2042.util;

public class RowClearingOutput {

    public final int linesRemoved;
    private final int[][] newMatrix;

    public RowClearingOutput(int linesRemoved, int[][] newMatrix) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
