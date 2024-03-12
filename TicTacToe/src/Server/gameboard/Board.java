package Server.gameboard;

import java.io.PrintWriter;

public class Board
{
    public static final int ROWS = 3;
    public static final int COLS = 3;

    // Define properties (package-visible)
    /** A board composes of [ROWS]x[COLS] Cell instances */
    public Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the board (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS];  // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /** Reset the contents of the game board, ready for new game. */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();  // The cells init itself
            }
        }
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {

        cells[selectedRow][selectedCol].content = player;

        if (hasWon(player, selectedRow, selectedCol)) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (isBoardFull()) {
            return State.DRAW;
        } else {
            return State.PLAYING;
        }
    }

    private boolean hasWon(Seed player, int selectedRow, int selectedCol) {
        return (cells[selectedRow][0].content == player &&
                cells[selectedRow][1].content == player &&
                cells[selectedRow][2].content == player) ||
                (cells[0][selectedCol].content == player &&
                        cells[1][selectedCol].content == player &&
                        cells[2][selectedCol].content == player) ||
                (selectedRow == selectedCol &&
                        cells[0][0].content == player &&
                        cells[1][1].content == player &&
                        cells[2][2].content == player) ||
                (selectedRow + selectedCol == 2 &&
                        cells[0][2].content == player &&
                        cells[1][1].content == player &&
                        cells[2][0].content == player);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }

    /** The board paints itself */
    public void paint(PrintWriter writer1,PrintWriter writer2) {
        StringBuilder boardRepresentation = new StringBuilder();
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                boardRepresentation.append(" ");
                boardRepresentation.append(cells[row][col].content.getIcon());
                boardRepresentation.append(" ");
                if (col < COLS - 1) boardRepresentation.append("|");
            }
            boardRepresentation.append("\n");
            if (row < ROWS - 1) {
                boardRepresentation.append("-----------\n");
            }
        }
        boardRepresentation.append("\n");

        String boardString = boardRepresentation.toString();
        writer1.println(boardString);
        writer1.flush();
        writer2.println(boardString);
        writer2.flush();
    }
}
