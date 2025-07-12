package com.sanwi22;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ChessGUI extends Frame {

    private final Board board;
    private final int squareSize = 75;
    private final int labelPadding = 20;
    private Position selectedPosition = null;
    private List<Position> legalMoves = new ArrayList<>();
    private boolean dragging = false;
    private Position draggedFrom = null;
    private Piece draggedPiece = null;
    private int dragX, dragY;
    private boolean whiteToMove = true;

    public ChessGUI(Board board) {
        this.board = board;
        board.initialize();

        setTitle("Chess");
        setSize(8 * squareSize + labelPadding + 40, 8 * squareSize + 60);
        setVisible(true);
        setResizable(false);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    dragX = e.getX();
                    dragY = e.getY();
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int col = (e.getX() - getInsets().left - labelPadding) / squareSize;
                int row = (e.getY() - getInsets().top) / squareSize;
                Position pos = new Position(row, col);
                Piece piece = board.getPiece(pos);
                if (piece != null && piece.isWhite() == whiteToMove) {
                    dragging = true;
                    draggedFrom = pos;
                    draggedPiece = piece;
                    legalMoves = filterLegalMoves(piece, board);
                    selectedPosition = pos;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging && draggedPiece != null) {
                    int col = (e.getX() - getInsets().left - labelPadding) / squareSize;
                    int row = (e.getY() - getInsets().top) / squareSize;
                    Position target = new Position(row, col);

                    if (legalMoves.contains(target)) {
                        // Check if move leaves king in check
                        Board copy = board.copy();
                        copy.movePiece(draggedFrom, target);
                        if (copy.isInCheck(whiteToMove)) {
                            // Illegal move: king would be in check
                            Toolkit.getDefaultToolkit().beep();
                        } else {
                            // Commit move
                            board.getGrid()[draggedFrom.getRow()][draggedFrom.getCol()] = null;
                            draggedPiece.setPosition(target);
                            board.placePiece(draggedPiece);
                            draggedPiece.setHasMoved(true);

                            // Handle castling rook move
                            if (draggedPiece instanceof King) {
                                int startCol = draggedFrom.getCol();
                                int endCol = target.getCol();
                                int sameRow = draggedFrom.getRow();

                                if (Math.abs(endCol - startCol) == 2) {
                                    if (endCol == 6) { // Kingside castling
                                        Piece rook = board.getPiece(new Position(sameRow, 7));
                                        board.getGrid()[sameRow][7] = null;
                                        rook.setPosition(new Position(sameRow, 5));
                                        board.placePiece(rook);
                                        rook.setHasMoved(true);
                                    } else if (endCol == 2) { // Queenside castling
                                        Piece rook = board.getPiece(new Position(sameRow, 0));
                                        board.getGrid()[sameRow][0] = null;
                                        rook.setPosition(new Position(sameRow, 3));
                                        board.placePiece(rook);
                                        rook.setHasMoved(true);
                                    }
                                }
                            }

                            // Switch turn
                            whiteToMove = !whiteToMove;

                            // Check check/checkmate for opponent
                            checkGameStatus();
                        }
                    }

                    dragging = false;
                    draggedPiece = null;
                    draggedFrom = null;
                    legalMoves.clear();
                    selectedPosition = null;
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Insets insets = getInsets();
                int offsetX = insets.left + labelPadding;
                int offsetY = insets.top;

                int mouseX = e.getX();
                int mouseY = e.getY();

                int col = (mouseX - offsetX) / squareSize;
                int row = (mouseY - offsetY) / squareSize;

                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    Position clicked = new Position(row, col);

                    if (selectedPosition != null && legalMoves.contains(clicked)) {
                        Board copy = board.copy();
                        copy.movePiece(selectedPosition, clicked);
                        if (copy.isInCheck(whiteToMove)) {
                            Toolkit.getDefaultToolkit().beep();
                            return;
                        }

                        Piece selectedPiece = board.getPiece(selectedPosition);
                        if (selectedPiece != null) {
                            board.getGrid()[selectedPosition.getRow()][selectedPosition.getCol()] = null;
                            selectedPiece.setPosition(clicked);
                            board.placePiece(selectedPiece);
                            selectedPiece.setHasMoved(true);

                            if (selectedPiece instanceof King) {
                                int startCol = selectedPosition.getCol();
                                int endCol = clicked.getCol();
                                int sameRow = selectedPosition.getRow();

                                if (Math.abs(endCol - startCol) == 2) {
                                    if (endCol == 6) { // Kingside
                                        Piece rook = board.getPiece(new Position(sameRow, 7));
                                        board.getGrid()[sameRow][7] = null;
                                        rook.setPosition(new Position(sameRow, 5));
                                        board.placePiece(rook);
                                        rook.setHasMoved(true);
                                    } else if (endCol == 2) { // Queenside
                                        Piece rook = board.getPiece(new Position(sameRow, 0));
                                        board.getGrid()[sameRow][0] = null;
                                        rook.setPosition(new Position(sameRow, 3));
                                        board.placePiece(rook);
                                        rook.setHasMoved(true);
                                    }
                                }
                            }

                            whiteToMove = !whiteToMove;
                            checkGameStatus();
                        }
                        selectedPosition = null;
                        legalMoves.clear();
                    } else {
                        Piece piece = board.getPiece(clicked);
                        if (piece != null && piece.isWhite() == whiteToMove) {
                            selectedPosition = clicked;
                            legalMoves = filterLegalMoves(piece, board);
                        } else {
                            selectedPosition = null;
                            legalMoves.clear();
                        }
                    }
                    repaint();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Filters legal moves to those that do not leave own king in check
    private List<Position> filterLegalMoves(Piece piece, Board board) {
        List<Position> moves = piece.getLegalMoves(board);
        List<Position> legal = new ArrayList<>();
        for (Position move : moves) {
            Board copy = board.copy();
            copy.movePiece(piece.getPosition(), move);
            if (!copy.isInCheck(piece.isWhite())) {
                legal.add(move);
            }
        }
        return legal;
    }

    // Check for check or checkmate and notify player
    private void checkGameStatus() {
        boolean opponentIsInCheck = board.isInCheck(whiteToMove);
        boolean opponentIsCheckmate = board.isCheckmate(whiteToMove);

        if (opponentIsCheckmate) {
            String winner = whiteToMove ? "Black" : "White";
            repaint();
            JOptionPane.showMessageDialog(this, "Checkmate! " + winner + " wins.");
            System.exit(0);
        } else if (opponentIsInCheck) {
            String checked = whiteToMove ? "White" : "Black";
            //JOptionPane.showMessageDialog(this, "Check to " + checked + "!");
        }
    }

    @Override
    public void paint(Graphics g) {
        Image offscreen = createImage(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) offscreen.getGraphics();

        Insets insets = getInsets();
        int offsetX = insets.left + labelPadding;
        int offsetY = insets.top;

        Color lightColor = new Color(240, 217, 181);
        Color darkColor = new Color(181, 136, 99);
        g2.setFont(new Font("Serif", Font.PLAIN, squareSize - 10));

        // Draw board and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int x = offsetX + col * squareSize;
                int y = offsetY + row * squareSize;

                boolean isLight = (row + col) % 2 == 0;
                g2.setColor(isLight ? lightColor : darkColor);
                g2.fillRect(x, y, squareSize, squareSize);

                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, squareSize, squareSize);

                Position pos = new Position(row, col);
                Piece piece = board.getPiece(pos);
                if (piece != null) {
                    String symbol = piece.getUnicodeSymbol();
                    g2.setColor(piece.isWhite() ? Color.WHITE : Color.BLACK);

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(symbol);
                    int textHeight = fm.getAscent();

                    int xText = x + (squareSize - textWidth) / 2;
                    int yText = y + (squareSize + textHeight) / 2 - 4;

                    g2.drawString(symbol, xText, yText);
                }
            }
        }

        // Highlight selected square
        if (selectedPosition != null) {
            int selX = offsetX + selectedPosition.getCol() * squareSize;
            int selY = offsetY + selectedPosition.getRow() * squareSize;

            g2.setColor(Color.YELLOW);
            g2.drawRect(selX, selY, squareSize, squareSize);
            g2.drawRect(selX + 1, selY + 1, squareSize - 2, squareSize - 2);
        }

        // Highlight legal moves
        g2.setColor(new Color(0, 255, 0, 160));
        for (Position move : legalMoves) {
            int cx = offsetX + move.getCol() * squareSize + squareSize / 2;
            int cy = offsetY + move.getRow() * squareSize + squareSize / 2;
            int radius = 12;
            g2.fillOval(cx - radius / 2, cy - radius / 2, radius, radius);
        }

        // Draw file letters (a-h)
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int col = 0; col < 8; col++) {
            int x = offsetX + col * squareSize + squareSize / 2 - g2.getFontMetrics().stringWidth(files[col]) / 2;
            int y = offsetY + 8 * squareSize + 15;
            g2.drawString(files[col], x, y);
        }

        // Draw rank numbers (1–8)
        for (int row = 0; row < 8; row++) {
            String rank = String.valueOf(8 - row);
            int x = offsetX - 12;
            int y = offsetY + row * squareSize + squareSize / 2 + g2.getFontMetrics().getAscent() / 2 - 2;
            g2.drawString(rank, x, y);
        }

        g.drawImage(offscreen, 0, 0, this);

        // Draw dragged piece on top
        if (dragging && draggedPiece != null) {
            String symbol = draggedPiece.getUnicodeSymbol();
            g2.setFont(new Font("Serif", Font.PLAIN, squareSize - 10));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(symbol);
            int textHeight = fm.getAscent();

            g2.setColor(draggedPiece.isWhite() ? Color.WHITE : Color.BLACK);
            g2.drawString(symbol, dragX - textWidth / 2, dragY + textHeight / 2);
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g); // Skip background clearing to prevent flicker
    }

    public static void main(String[] args) {
        Board board = new Board();
        new ChessGUI(board);
    }
}
