package com.comp2042.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void testIntersect_NoCollision(){
        int[][] matrix = new int [][]{
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
        };

        int[][] brick = new int [][]{
                {0,1,0},
                {1,1,1},
                {0,0,0}
        };

        boolean intersects = MatrixOperations.intersect(matrix, brick, 0, 0);
        assertFalse(intersects, "Should not intersect with an empty board.");
    }

    @Test
    void testIntersect_CollisionWithWall(){
        int[][] matrix = new int [][] { {0,0}, {0,0}};
        int [][] brick = new int [][] { {1,1}, {1,1}};

        boolean intersects = MatrixOperations.intersect(matrix, brick, -1, 0);

        assertTrue(intersects, "Should intersect with the left wall.");
    }

    @Test
    void testIntersect_CollisionWithFloor(){
        int[][] matrix = new int [][] { {0,0}, {0,0}};
        int [][] brick = new int [][] { {1,1}, {1,1}};

        boolean intersects = MatrixOperations.intersect(matrix, brick, 0, 1);

        assertTrue(intersects, "Should intersect with the floor.");
    }

    @Test
    void testINtersect_CollisionWithAnotherBrick(){
        int[][] matrix = new int[][]{
                {0,0,0,0},
                {0,0,0,0},
                {1,1,1,1}
        };

        int [][] brick = new int [][] {
                {0,2,0},
                {2,2,2},
                {0,0,0}
        };

        boolean intersects = MatrixOperations.intersect(matrix, brick, 0, 1);
        assertTrue(intersects, "Should intersect with the other brick in the matrix.");
    }
}
