package model;

import exceptions.ExplosionExeception;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final List<Field> neighbors = new ArrayList<>();
    private final int X;
    private final int Y;

    private boolean explored;
    private boolean flagged;
    private boolean mined;

    public Field(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public boolean addNeighbor(Field neighbor) {
        if (neighbor == null) return false;

        int delta = Math.abs((this.X - neighbor.X) + (this.Y - neighbor.Y));

        if (delta == 1 || delta == 2) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    public boolean explore() {
        if (!explored && !flagged) {
            this.explored = true;

            if (mined) {
                throw new ExplosionExeception();
            }
            if (isNeighborsSafe()) {
                neighbors.forEach(Field::explore);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean isObjectiveReached() {
        return (explored && !mined) || (flagged && mined);
    }

    public void reset() {
        explored = false;
        flagged = false;
        mined = false;
    }

    @Override
    public String toString() {
        if (flagged) {
            return "x";
        } else if (!explored) {
            return "?";
        } else if (mined) {
            return "*";
        } else if (isNeighborsSafe()) {
            return ".";
        } else {
            return Long.toString(countMinedNeighbors());
        }
    }

    private boolean isNeighborsSafe() {
        return neighbors.stream().noneMatch(Field::isMined);
    }

    private long countMinedNeighbors() {
        return neighbors.stream().filter(Field::isMined).count();
    }

    // --- Setters ---
    public void toggleFlagged() {
        if (!explored) flagged = !flagged;
    }

    public void placeMine() {
        mined = true;
    }

    // --- Getters ---
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
