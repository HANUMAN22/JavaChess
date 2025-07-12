package com.sanwi22;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(Position other) {
        this.row = other.row;
        this.col = other.col;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isWhite(Position pos) {
        return (pos.getRow() + pos.getCol()) % 2 != 0;
    }

    public boolean isValidPosition() {
        int r = this.getRow();
        int c = this.getCol();
        return (r >= 0) && (r < 8) && (c >= 0) && (c < 8);
    }
}
