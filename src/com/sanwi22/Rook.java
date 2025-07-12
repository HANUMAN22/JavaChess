package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        int[][] directions = {
                {-1, 0}, // up
                {+1, 0}, // down
                {0, -1}, // left
                {0, +1}  // right
        };

        for (int[] dir : directions) {
            int row = getPosition().getRow();
            int col = getPosition().getCol();

            while (true) {
                row += dir[0];
                col += dir[1];
                Position newPos = new Position(row, col);

                if (!board.isValidPosition(newPos)) break;

                Piece piece = board.getPiece(newPos);
                if (piece == null) {
                    legalMoves.add(newPos);
                    printMove(newPos);
                } else {
                    if (piece.isWhite() != this.isWhite()) {
                        legalMoves.add(newPos);
                        printMove(newPos);
                    }
                    break;
                }
            }
        }

        return legalMoves;
    }

    private void printMove(Position pos) {
        char file = (char) ('a' + pos.getCol()); // convert column to letter
        int rank = pos.getRow() + 1; // rows are 0-based, ranks are 1-based
        //System.out.println(file + "" + rank);
    }

    @Override
    public Rook clone() {
        Rook copy = new Rook(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }

}
