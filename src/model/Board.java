package model;

import exceptions.ExplosionException;
import exceptions.MinesOverloadException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private final List<Field> board = new ArrayList<>();
    private final int columns;
    private final int rows;
    private final int mines;

    public Board(int rows, int columns, int mines) {
        this.columns = columns;
        this.rows = rows;

        if (mines < 1 || mines > (rows * columns)) {
            throw new MinesOverloadException();
        } else {
            this.mines = mines;
        }

        instantiateBoardFields();
        connectNeighbors();
        placeMines();
    }

    public void resetBoard() {
        board.forEach(Field::reset);
        placeMines();
    }

    public boolean isObjectiveComplete() {
        return board.stream().allMatch(Field::isObjectiveReached);
    }

    public void exploreFieldIn(int row, int column) {
        try {
            board.parallelStream()
                    .filter(f -> f.getRow() == row && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(Field::explore);
        } catch (ExplosionException e) {
            board.forEach(Field::setExplored);
            throw e;
        }
    }

    public void flagFieldIn(int row, int column) {
        board.parallelStream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(Field::toggleFlagged);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(" ");
                sb.append(board.get(index));
                sb.append(" ");
                index++;
            }
            sb.append("\n");
        }

        return  sb.toString();
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
        Predicate<Field> mined = (Field::isMined);

        do {
            int random = (int) (Math.random() * board.size());
            board.get(random).placeMine();
            minesPlaced = board.stream().filter(mined).count();
        } while (minesPlaced < mines);
    }

    // --- Getters ---
    public List<Field> copyBoardList() {
        return List.copyOf(board);
    }

    public int getMines() {
        return mines;
    }
}
