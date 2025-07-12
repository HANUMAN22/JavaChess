package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getCol();

        int[][] directions = {
                {-1, 0}, {+1, 0},  // vertical
                {0, -1}, {0, +1},  // horizontal
                {-1, -1}, {-1, +1}, // diagonals
                {+1, -1}, {+1, +1}
        };

        for (int[] dir : directions) {
            int r = row;
            int c = col;

            while (true) {
                r += dir[0];
                c += dir[1];
                Position pos = new Position(r, c);

                if (!board.isValidPosition(pos)) break;

                Piece target = board.getPiece(pos);
                if (target == null) {
                    legalMoves.add(pos);
                    printMove(pos);
                } else {
                    if (target.isWhite() != this.isWhite()) {
                        legalMoves.add(pos);
                        printMove(pos);
                    }
                    break;
                }
            }
        }

        return legalMoves;
    }

    private void printMove(Position pos) {
        char file = (char) ('a' + pos.getCol());
        int rank = pos.getRow() + 1;
        //System.out.println(file + "" + rank);
    }

    @Override
    public Queen clone() {
        Queen copy = new Queen(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }

}
