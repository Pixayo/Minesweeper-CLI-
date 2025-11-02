package model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Field> board = new ArrayList<>();
    private final int columns;
    private final int rows;

    private final int totalFields;
    private final int totalMines;

    public Board(int rows, int columns, int difficult) {
        this.columns = columns;
        this.rows = rows;

        this.totalFields = rows * columns;
        this.totalMines = Math.max(totalFields / difficult, 1);

        instantiateBoardFields();
        connectNeighbors();
        placeMines();
    }

    public void resetBoard() {
        board.forEach(Field::reset);
    }

    public boolean isObjetiveComplete() {
        for (Field field : board) {
            if (!field.isObjectiveReached()) return false;
        }
        return true;
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
               minesPlaced++;
           }
        } while(minesPlaced < totalMines);
    }

    // --- Getters ---
    public List<Field> copyBoardList() {
        return List.copyOf(board);
    }

    public int getTotalFields() {
        return totalFields;
    }

    public int getTotalMines() {
        return totalMines;
    }
}
