package model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Field> board = new ArrayList<>();
    private final int columnSize;
    private final int rowSize;

    private final int totalFields;
    private final int totalMines;

    Board(int rowSize, int columnSize, int minesCount) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;

        this.totalFields = rowSize * columnSize;
        this.totalMines = minesCount;

        // fillBoardWithFields();
        // connectNeighbors();
        // placeMines();
    }
}
