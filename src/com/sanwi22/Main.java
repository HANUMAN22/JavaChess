package com.sanwi22;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        new ChessGUI(game.getBoard());
    }
}
