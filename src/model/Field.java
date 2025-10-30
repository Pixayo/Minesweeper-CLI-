package model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final List<Field> neighbors = new ArrayList<>();
    private final int X;
    private final int Y;

    private boolean explored;
    private boolean flagged;
    private boolean mined;

    Field(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public boolean addNeighbor(Field neighbor) {
        int delta = Math.abs((this.X - neighbor.X) + (this.Y - neighbor.Y));

        if (delta == 1 || delta == 2) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    //public boolean explore();
    //public boolean isObjectiveReached();
    //public void reset();

    private boolean isNeighborsSafe() {
        return neighbors.stream().noneMatch(Field::isMined);
    }
    private long countMinedNeighbors() {
        return neighbors.stream().filter(Field::isMined).count();
    }

    @Override
    public String toString() {
        if (flagged) {
            return "x";
        } else if (explored && mined) {
            return "*";
        } else if (explored && !isNeighborsSafe()) {
            return Long.toString(countMinedNeighbors());
        } else {
            return "?";
        }
    }

    // ---- Setters ---
    public void toggleFlagged() {
        flagged = !flagged;
    }

    public void placeMine() {
        mined = true;
    }

    // ---- Getters ---
    public boolean isExplored() {
        return explored;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isMined() {
        return mined;
    }
}
