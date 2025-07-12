package com.sanwi22;

import java.util.List;

public abstract class Piece implements Cloneable{

    protected Position position;
    protected boolean isWhite;
    protected boolean hasMoved;

    public Piece(Position position, Boolean isWhite) {
        this.position = position;
        this.isWhite = isWhite;
        this.hasMoved = false;
    }


    public String getUnicodeSymbol() {
        if (this instanceof King) {
            return "\u265A";
        } else if (this instanceof Queen) {
            return "\u265B";
        } else if (this instanceof Rook) {
            return "\u265C";
        } else if (this instanceof Bishop) {
            return "\u265D";
        } else if (this instanceof Knight) {
            return "\u265E";
        } else if (this instanceof Pawn) {
            return "\u265F";
        }
        return "";
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract List<Position> getLegalMoves(Board board);

    public Position getPosition() {
        return position;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract Piece clone();
}
