package model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Field> board = new ArrayList<>();
    private final int columns;
    private final int rows;

    private final int totalFields;
    private final int totalMines;

    public Board(int rowSize, int columnSize, int difficult) {
        this.columns = columnSize;
        this.rows = rowSize;

        this.totalFields = rowSize * columnSize;
        this.totalMines = Math.max(totalFields / difficult, 1);

        instantiateBoardFields();
        connectNeighbors();
        placeMines();
    }

    public void resetBoard() {
        board.forEach(Field::reset);
    }

    private void instantiateBoardFields() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board.add(new Field(i, j));
            }
        }
    }

    private void connectNeighbors() {
        for (Field f1 : board) {
            for (Field f2 : board) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void placeMines() {
        long minesPlaced = 0;

        do {
           int random = (int) (Math.random() * board.size());
           Field randomField = board.get(random);

           if (randomField.isMined()) {
               continue;
           } else {
               randomField.placeMine();
           }
           minesPlaced = board.stream().filter(Field::isMined).count();
        } while(minesPlaced < totalMines);
    }
}
