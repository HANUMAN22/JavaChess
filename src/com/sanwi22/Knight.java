package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getCol();

        int[][] moves = {
                {-2, -1}, {-2, +1},
                {-1, -2}, {-1, +2},
                {+1, -2}, {+1, +2},
                {+2, -1}, {+2, +1}
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            Position pos = new Position(newRow, newCol);

            if (!board.isValidPosition(pos)) continue;

            Piece target = board.getPiece(pos);
            if (target == null || target.isWhite() != this.isWhite()) {
                legalMoves.add(pos);
                printMove(pos);
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
    public Knight clone() {
        Knight copy = new Knight(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }

}
