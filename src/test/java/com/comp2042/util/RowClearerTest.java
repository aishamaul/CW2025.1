package com.comp2042.util;

import com.comp2042.model.ClearRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RowClearerTest {
    private  RowClearer rowClearer;

    @BeforeEach
    void setUp() {
        rowClearer = new RowClearer();
    }

    @Test
    void testCheckRemoving_NoRowsToClear(){
        int [][] boardBefore = new int[][]{
                {0,0,0,0},
                {0,1,1,0},
                {1,0,1,0},
        };

        int[][] expectedBoardAfter = new int[][]{
                {0,0,0,0},
                {0,1,1,0},
                {1,0,1,0},
        };
        ClearRow result = rowClearer.checkRemoving(boardBefore);

        assertEquals(0, result.getLinesRemoved(), "Should be 0 line removed.");
        assertEquals(0, result.getScoreBonus(), "score bonus should be 0.");
    }

    @Test
    void testCheckRemoving_OneRowToClear(){
        int [][] boardBefore = new int[][]{
                {0,1,0,0},
                {1,1,1,1},
                {1,0,1,0}
        };

        int[][] expectedBoardAfter = new int[][]{
                {0,0,0,0},
                {0,1,0,0},
                {1,0,1,0}
        };

        ClearRow result = rowClearer.checkRemoving(boardBefore);
        assertEquals(1, result.getLinesRemoved(), "Should be 1 line removed.");

        assertEquals(50, result.getScoreBonus(), "score bonus for 1 line should be 50.");

        assertArrayEquals(expectedBoardAfter, result.getNewMatrix(),"The new matrix is not as expected.");

    }

    @Test
    void testCheckRemoving_FourRowsToClear(){
        int [][] boardBefore = new int[][]{
                {1,1,1,1},
                {2,2,2,2},
                {3,3,3,3},
                {4,4,4,4}
        };

        int[][] expectedBoardAfter = new int[][]{
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},

        };

        ClearRow result = rowClearer.checkRemoving(boardBefore);
        assertEquals(4, result.getLinesRemoved(), "Should be 4 lines removed.");

        assertEquals(800, result.getScoreBonus(), "score bonus for 4 lines should be 800.");

        assertArrayEquals(expectedBoardAfter, result.getNewMatrix(),"The new matrix should be empty.");
    }

}
