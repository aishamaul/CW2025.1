package com.comp2042.game;

import com.comp2042.game.events.EventSource;
import com.comp2042.game.events.MoveEvent;

public class ScoreEvaluator {

    public void scoreMovement(MoveEvent event, Score score){

        if (event.getEventSource() == EventSource.USER) {
            score.add(1);
        }
    }

    public void scoreDrop(int rowDropped, Score score){
        int points = rowDropped * 2;
        score.add(points);
    }

    public void scoreLineClear(int bonus, Score score){
        if (bonus>0){
            score.add(bonus);
        }
    }
}
