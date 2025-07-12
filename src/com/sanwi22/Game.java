package com.sanwi22;

import java.util.Scanner;

public class Game {

    private Board board;
    private boolean isWhiteTurn;

    public Game() {
        board = new Board();
        isWhiteTurn = true;
    }

    public void start() {
        board.initialize();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println((isWhiteTurn ? "White" : "Black") + "'s move:");

            break;
        }
    }

    public Board getBoard() {
        return board;
    }
}
