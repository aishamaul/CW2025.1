package com.comp2042.game.scoring;

public class RowScoreCalculator {
    public int calculateScoreBonus(int linesRemoved){
        return 50 * linesRemoved * linesRemoved;
    }
}
