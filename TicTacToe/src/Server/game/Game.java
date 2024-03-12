package Server.game;

import Server.gameboard.Board;
import Server.gameboard.Seed;
import Server.gameboard.State;
import Server.player.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
    private int currentPlayerIndex;
    private final Player player1;
    private final Player player2;
    private final Board board;
    private  State gameState;

    public Game(CopyOnWriteArrayList<Player> participants)
    {

        this.player1 = participants.get(0);
        this.player2 = participants.get(1);
        this.currentPlayerIndex = 0;
        this.board = new Board();
        board.newGame();
        this.gameState = State.PLAYING;
    }

    public void start() {



        // Initialize the game state and rules
        while (isGameOver())
        {
            Player currentPlayer = getCurrentPlayer();
            boolean validMove = false;

            // Receive move from the current player
            while(!validMove)
            {
                // Implement logic to receive and validate the move from the current player
                // ...
                try
                {
                    String move = receiveMove(currentPlayer);
                    int row = Integer.parseInt(move.split(",")[0]);
                    int col = Integer.parseInt(move.split(",")[1]);

                    if(isValidMove(row, col))
                    {
                        gameState = board.stepGame(currentPlayer.seed(), row, col);
                        validMove = true;
                        printBoard(player1.writer(),player2.writer());
                    }
                    else
                    {
                        currentPlayer.writer().println("Invalid Move,Please Try Again");
                        currentPlayer.writer().flush();
                    }
                } catch(IOException e)
                {
                    System.err.println("Error reading player's move: " + e.getMessage());
                } catch(NumberFormatException e)
                {
                    currentPlayer.writer().println("Invalid input format. Please enter row and column separated by a comma.");
                    currentPlayer.writer().flush();
                } catch(ArrayIndexOutOfBoundsException e)
                {
                    currentPlayer.writer().println("Invalid input. Please enter row and column in the range of Board.");
                    currentPlayer.writer().flush();
                }
            }
            handleGameState(currentPlayer.writer());
            currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        }
    }

    private String receiveMove(Player currentPlayer) throws IOException
    {
        currentPlayer.writer().println("Player " + currentPlayer.seed().getIcon() + ", enter your move (row,col): ");
        currentPlayer.writer().flush();
        return currentPlayer.reader().readLine();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS &&
                board.cells[row][col].content == Seed.NO_SEED;
    }

    private void handleGameState(PrintWriter writer) {
        switch (gameState) {
            case PLAYING:
                break;
            case DRAW:
                writer.println("It's a draw!");
                writer.flush();
                break;
            case CROSS_WON:
                writer.println("CROSS wins!");
                writer.flush();
                break;
            case NOUGHT_WON:
                writer.println("NOUGHT wins!");
                writer.flush();
                break;
        }
    }
    private boolean isGameOver() {
        return gameState == State.PLAYING;
    }

    private Player getCurrentPlayer(){
        return currentPlayerIndex == 0 ? player1 : player2;
    }
    private void printBoard(PrintWriter writer1, PrintWriter writer2) {
        board.paint(writer1, writer2);
    }
}