package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    List<Field> boardList;

    @BeforeEach
    public void defaultAttributes() {
        // 6x6 board, 10% of the total fields have mines.
        board = new Board(6, 6, 10);
        boardList = board.copyBoardList();
    }

    @Test
    public void test_ObjectiveReached() {
        assertFalse(board.isObjectiveComplete());
    }

    @Test
    public void test_MinePlacement() {
        int minesCount = (int) boardList.stream().filter(Field::isMined).count();
        assertEquals(board.getMines(), minesCount);
    }
}