package com.comp2042.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new SimpleBoard(25,10);
        board.createNewBrick();
    }

    @Test
    void testNewBrick_StartPosition() {
        assertEquals(4, board.getViewData().getxPosition(), "New brick should start at x-position 4.");
    }

    @Test
    void testMoveBrickLeft_Success(){
        boolean canMove = board.moveBrickLeft();

        assertTrue(canMove, "Should be able to move left from start position.");
        assertEquals(3, board.getViewData().getxPosition(),"x-position should be 3 after one move left.");
    }

    @Test
    void testMoveBrickLeft_HitsWall(){
        board.moveBrickLeft();
        board.moveBrickLeft();
        board.moveBrickLeft();
        board.moveBrickLeft();

        assertEquals(0, board.getViewData().getxPosition(), "Test setup failed. Brick is not at x=0");

        boolean canMove = board.moveBrickLeft();

        assertFalse(canMove, "Should return false when trying to move past left wall.");
        assertEquals(0, board.getViewData().getxPosition(), "x-position should remain 0.");
    }

    @Test
    void testMoveBrickRight_HitsWall(){
        board.moveBrickRight();
        board.moveBrickRight();

        assertEquals(6, board.getViewData().getxPosition(), "Test setup failed. Brick is not at x=6.");

        boolean canMove = board.moveBrickRight();

        assertFalse(canMove, "Should return false when trying to move past right wall.");
        assertEquals(6, board.getViewData().getxPosition(), "x-position should remain 6.");
    }

    @Test
    void testMoveBrickDown_Success(){
        boolean canMove = board.moveBrickDown();

        assertTrue(canMove, "Should be able to move down from start position.");
        assertEquals(2, board.getViewData().getyPosition(), "y-position should be 2 after one move down.");
    }
}
