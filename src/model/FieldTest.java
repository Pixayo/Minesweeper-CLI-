package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    Field center;
    Field neighbor1;
    Field neighbor2;
    Field farNeighbor;
    Field sameField;

    @BeforeEach
    void createFields() {
        center = new Field(5,5);
        sameField = center;

        neighbor1 = new Field(4,5);
        neighbor2 = new Field(4,4);
        farNeighbor = new Field(999,999);
    }

    @Test
    void test_AddNeighbor() {
        boolean test1 = center.addNeighbor(neighbor1);
        boolean test2 = center.addNeighbor(neighbor2);
        boolean test3 = center.addNeighbor(farNeighbor);
        boolean test4 = center.addNeighbor(sameField);
        // this tests should be in this order: true, true, false, false
        assertTrue(test1 && test2 && (!test3) && (!test4));
    }

    @Test
    void test_ToString() {
        Field[][] grid = new Field[3][3];
        String expectedGrid =
                "???\n" +
                "???\n" +
                "???\n";

        Supplier<String> displayGrid = () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = new Field(i, j);
                    sb.append(grid[i][j]);
                }
                sb.append("\n");
            }
            return sb.toString();
        };

        assertEquals(expectedGrid, displayGrid.get());
    }

    @Test
    void test_Explore() {
        center.toggleFlagged();
        boolean test1 = center.explore();

        center.reset();
        boolean test2 = center.explore();

        center.reset();
        center.addNeighbor(neighbor1);
        boolean test3 = center.explore();
        boolean test4 = neighbor1.isExplored();

        assertTrue((!test1) && test2 && test3 && test4);
    }

    @Test
    void test_IsObjectiveReached() {
        center.placeMine();
        boolean test1 = center.isObjectiveReached();

        neighbor1.toggleFlagged();
        boolean test2 = neighbor1.isObjectiveReached();

        neighbor2.explore();
        boolean test3 = neighbor2.isObjectiveReached();

        assertTrue((!test1) && (!test2) && test3);
    }
}