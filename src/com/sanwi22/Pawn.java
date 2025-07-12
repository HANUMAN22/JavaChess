package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getCol();

        int direction = isWhite() ? -1 : 1; // white moves up, black moves down
        int startRow = isWhite() ? 6 : 1;

        // 1-step forward
        Position forwardOne = new Position(row + direction, col);
        if (board.isValidPosition(forwardOne) && board.getPiece(forwardOne) == null) {
            legalMoves.add(forwardOne);
            printMove(forwardOne);

            // 2-step forward if at start row and 1-step is empty
            Position forwardTwo = new Position(row + 2 * direction, col);
            if (row == startRow && board.isValidPosition(forwardTwo) && board.getPiece(forwardTwo) == null) {
                legalMoves.add(forwardTwo);
                printMove(forwardTwo);
            }
        }

        // Diagonal captures
        int[] dCols = {-1, +1};
        for (int dc : dCols) {
            Position diag = new Position(row + direction, col + dc);
            if (board.isValidPosition(diag)) {
                Piece target = board.getPiece(diag);
                if (target != null && target.isWhite() != this.isWhite()) {
                    legalMoves.add(diag);
                    printMove(diag);
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
    public Pawn clone() {
        Pawn copy = new Pawn(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
