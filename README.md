JavaChess ♟️

A two-player chess application developed in Java, featuring a graphical user interface, object-oriented game architecture and an implementation of core chess mechanics.

The project explores object-oriented programming, inheritance, polymorphism, board-state management and the logic behind validating chess moves.

Features

* Graphical User Interface: Interactive chessboard built using Java AWT, with Unicode chess pieces.
* Two-Player Gameplay: Local multiplayer, with players taking alternating turns as White and Black.
* Move Validation: Individual movement logic implemented for all six chess piece types.
* Move Visualisation: Highlights available moves when a piece is selected.
* Drag-and-Drop Controls: Move pieces by dragging them across the board, or by selecting a piece and clicking its destination.
* Check Detection: Identifies when a king is under attack.
* Checkmate Detection: Evaluates possible moves to determine whether a player has any valid escape from check.
* Castling: Implements both kingside and queenside castling, including checks on the king’s movement path.
* Piece Captures: Supports capturing opposing pieces.

Technologies Used

* Language: Java
* GUI: Java AWT and Swing (JOptionPane)
* Concepts: Object-Oriented Programming, inheritance, polymorphism, encapsulation and 2D array-based data structures.

Project Structure

The application is organised into separate classes to keep the game logic, chess pieces, board management and graphical interface modular.

JavaChess/
│
└── src/
    └── com/
        └── sanwi22/
            │
            ├── Main.java
            ├── Game.java
            ├── ChessGUI.java
            ├── Board.java
            ├── Position.java
            ├── Piece.java
            │
            ├── King.java
            ├── Queen.java
            ├── Rook.java
            ├── Bishop.java
            ├── Knight.java
            └── Pawn.java

Architecture

* Board: Maintains the 8×8 chessboard, manages piece positions and handles board-state operations, including check and checkmate detection.
* Piece: Abstract base class defining the common properties and behaviour shared by all chess pieces.
* Individual Piece Classes: Extend the base Piece class and implement their respective movement rules through method overriding.
* Position: Represents individual coordinates on the chessboard.
* ChessGUI: Handles rendering, mouse interactions, move highlighting and communication between the graphical interface and game logic.
* Game: Initialises and maintains the game state.
* Main: Application entry point.

Getting Started

Prerequisites

* Java Development Kit (JDK)
* An IDE such as IntelliJ IDEA, Eclipse or VS Code (optional).

Running the Application

Option 1: Using an IDE

1. Clone or download this repository.
2. Open the project in your preferred Java IDE.
3. Ensure the src directory is configured as a source root.
4. Run Main.java.

Option 2: Using the Terminal

Clone the repository:

git clone https://github.com/HANUMAN22/JavaChess.git

Navigate into the project directory:

cd JavaChess

Compile the source files:

javac -d out src/com/sanwi22/*.java

Run the application:

java -cp out com.sanwi22.Main

How to Play

1. Launch the application to initialise the chessboard.
2. White makes the first move.
3. Select a piece to display its available moves.
4. Choose a highlighted square to complete the move, or drag the piece to its destination.
5. Players alternate turns until checkmate is reached.

Current Limitations

The implementation focuses on core chess mechanics. Some official chess rules and game-ending conditions are not yet implemented, including:

* En passant captures.
* Pawn promotion.
* Stalemate detection.
* Draw conditions and repetition tracking.

Future Improvements

Potential extensions include:

* Implementing the remaining official chess rules.
* Adding move history and the ability to undo moves.
* Introducing an AI opponent.
* Adding configurable difficulty levels.
* Improving the graphical interface with animations and additional visual feedback.
* Implementing chess notation and game replay functionality.

Author

Sanwi

Developed as a personal Java programming project to explore object-oriented software design, game logic and graphical application development.

GitHub Repository
