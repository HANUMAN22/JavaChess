package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        String[] colLetters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        int[][] directions = {
                {-1, -1}, // up-left
                {-1, +1}, // up-right
                {+1, -1}, // down-left
                {+1, +1}  // down-right
        };

        for (int[] dir : directions) {
            int row = getPosition().getRow();
            int col = getPosition().getCol();

            while (true) {
                row += dir[0];
                col += dir[1];
                Position newPos = new Position(row, col);

                if (!board.isValidPosition(newPos)) break;

                Piece target = board.getPiece(newPos);
                if (target == null) {
                    legalMoves.add(newPos);
                    System.out.println(colLetters[newPos.getCol()] + (newPos.getRow() + 1));
                } else {
                    if (target.isWhite() != this.isWhite()) {
                        legalMoves.add(newPos);
                        System.out.println(colLetters[newPos.getCol()] + (newPos.getRow() + 1));
                    }
                    break; // Stop scanning in this direction
                }
            }
        }

        return legalMoves;
    }

    @Override
    public Bishop clone() {
        Bishop copy = new Bishop(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }

}
