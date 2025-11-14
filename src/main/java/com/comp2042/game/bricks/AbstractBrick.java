package com.comp2042.game.bricks;

import com.comp2042.util.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

public class AbstractBrick implements Brick{
    protected final List<int[][]> brickMatrix = new ArrayList<>();

    @Override
    public List<int[][]>  getShapeMatrix(){
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
