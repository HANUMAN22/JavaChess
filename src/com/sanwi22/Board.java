package com.sanwi22;

import java.util.List;

public class Board {

    private Piece[][] board = new Piece[8][8];

    public void initialize() {
        // White pieces (bottom row, row 7)
        placePiece(new Rook(new Position(7, 0), true));
        placePiece(new Knight(new Position(7, 1), true));
        placePiece(new Bishop(new Position(7, 2), true));
        placePiece(new Queen(new Position(7, 3), true));  // Queen on d1
        placePiece(new King(new Position(7, 4), true));   // King on e1
        placePiece(new Bishop(new Position(7, 5), true));
        placePiece(new Knight(new Position(7, 6), true));
        placePiece(new Rook(new Position(7, 7), true));

        // White pawns (row 6)
        for (int col = 0; col < 8; col++) {
            placePiece(new Pawn(new Position(6, col), true));
        }

        // Black pieces (top row, row 0)
        placePiece(new Rook(new Position(0, 0), false));
        placePiece(new Knight(new Position(0, 1), false));
        placePiece(new Bishop(new Position(0, 2), false));
        placePiece(new Queen(new Position(0, 3), false));  // Queen on d8
        placePiece(new King(new Position(0, 4), false));   // King on e8
        placePiece(new Bishop(new Position(0, 5), false));
        placePiece(new Knight(new Position(0, 6), false));
        placePiece(new Rook(new Position(0, 7), false));

        // Black pawns (row 1)
        for (int col = 0; col < 8; col++) {
            placePiece(new Pawn(new Position(1, col), false));
        }
    }


    public Piece getPiece(Position pos) {
        return board[pos.getRow()][pos.getCol()];
    }

    public void placePiece(Piece piece) {
        Position pos = piece.getPosition();
        if (isValidPosition(pos)) {
            board[pos.getRow()][pos.getCol()] = piece;
        }
    }

    public boolean movePiece(Position from, Position to) {
        if (!isValidPosition(from) || !isValidPosition(to)) return false;

        Piece piece = getPiece(from);
        if(piece == null) return false;

        board[to.getRow()][to.getCol()] = piece;
        board[from.getRow()][from.getCol()] = null;

        piece.setPosition(to);

        return true;
    }

    public boolean isValidPosition(Position pos) {
        int r = pos.getRow();
        int c = pos.getCol();
        return (r >= 0) && (r < 8) && (c >= 0) && (c < 8);
    }

    public Position findKingPosition(boolean isWhite) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece instanceof King && piece.isWhite() == isWhite) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public boolean isUnderAttack(Position position, boolean byWhite) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.isWhite() == byWhite) {
                    if (!(piece instanceof King)) { // Avoid recursive king check
                        List<Position> moves = piece.getLegalMoves(this);
                        for (Position move : moves) {
                            if (move.equals(position)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheck(boolean isWhite) {
        Position kingPos = findKingPosition(isWhite);
        return isUnderAttack(kingPos, !isWhite);
    }

    public boolean isCheckmate(boolean isWhite) {
        if (!isInCheck(isWhite)) return false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece.isWhite() == isWhite) {
                    List<Position> moves = piece.getLegalMoves(this);
                    for (Position move : moves) {
                        Board copy = this.copy(); // See next step
                        copy.movePiece(piece.getPosition(), move);
                        if (!copy.isInCheck(isWhite)) {
                            return false; // Escaped check
                        }
                    }
                }
            }
        }

        return true; // No legal escape found
    }

    public Board copy() {
        Board newBoard = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    newBoard.placePiece(piece.clone()); // Ensure each piece has a .clone() method
                }
            }
        }
        return newBoard;
    }


    public Piece[][] getGrid() {
        return board;
    }
}
