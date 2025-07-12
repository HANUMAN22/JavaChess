package com.sanwi22;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private int kingRow;

    public King(Position position, Boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> legalMoves = new ArrayList<>();
        int row = getPosition().getRow();
        int col = getPosition().getCol();

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            Position pos = new Position(newRow, newCol);

            if (!board.isValidPosition(pos)) continue;

            Piece target = board.getPiece(pos);
            if (target == null || target.isWhite() != this.isWhite()) {
                legalMoves.add(pos);
            }
        }

        int kingRow = this.isWhite() ? 7 : 0;
        Position kingPos = new Position(kingRow, 4);

        if (!this.hasMoved && !board.isUnderAttack(kingPos, !this.isWhite())) {
            // Kingside castling
            Position f = new Position(kingRow, 5);
            Position g = new Position(kingRow, 6);
            Position h = new Position(kingRow, 7);
            Piece kingsideRook = board.getPiece(h);

            if (kingsideRook instanceof Rook && !kingsideRook.hasMoved &&
                    board.getPiece(f) == null && board.getPiece(g) == null &&
                    !board.isUnderAttack(f, !this.isWhite()) &&
                    !board.isUnderAttack(g, !this.isWhite())) {
                legalMoves.add(g);
            }

            // Queenside castling
            Position d = new Position(kingRow, 3);
            Position c = new Position(kingRow, 2);
            Position b = new Position(kingRow, 1);
            Position a = new Position(kingRow, 0);
            Piece queensideRook = board.getPiece(a);

            if (queensideRook instanceof Rook && !queensideRook.hasMoved &&
                    board.getPiece(d) == null &&
                    board.getPiece(c) == null &&
                    board.getPiece(b) == null &&
                    !board.isUnderAttack(d, !this.isWhite()) &&
                    !board.isUnderAttack(c, !this.isWhite())) {
                legalMoves.add(c);
            }
        }

        return legalMoves;
    }

    public int getKingRow() {
        return kingRow;
    }

    private void printMove(Position pos) {
        char file = (char) ('a' + pos.getCol());
        int rank = pos.getRow() + 1;
        //System.out.println(file + "" + rank);
    }

    @Override
    public King clone() {
        King copy = new King(new Position(position.getRow(), position.getCol()), isWhite);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
