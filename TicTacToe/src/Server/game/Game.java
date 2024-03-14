package Server.game;

import Server.gameboard.Board;
import Server.gameboard.Seed;
import Server.gameboard.State;
import Server.player.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game
{
    private int currentPlayerIndex;

    private Player player1;

    private Player player2;

    private final Board board;

    private State gameState;
    private static final Lock gameLock = new ReentrantLock();

    private static final Condition player1Turn = gameLock.newCondition();

    private static final Condition player2Turn = gameLock.newCondition();

    private static final AtomicBoolean instructionsSent = new AtomicBoolean(false);

    private final CopyOnWriteArrayList<Player> participants = new CopyOnWriteArrayList<>();

    private final AtomicReference<Player> currentPlayer= new AtomicReference<>();

    private String player1ThreadName;


    public Game()
    {
        this.currentPlayerIndex = 0;

        this.board = new Board();

        board.newGame();

        this.gameState = State.PLAYING;
    }


    public void addPlayerAndStartGaming(Player player)
    {
        gameLock.lock();
        try
        {
            participants.add(player);
            if (participants.size() == 1)
            {
                player1 = player;

                player1ThreadName = Thread.currentThread().getName();

                System.out.println("Player 1 is on Thread : " + Thread.currentThread().getName());

                while (participants.size() < 2)
                {
                    player1Turn.await();

                }
            }
            else
            {
                System.out.println("Player 2 is on Thread : " + Thread.currentThread().getName());

                player2 = player;

                player1Turn.signal();
            }
        }
        catch (InterruptedException exception)
        {

            System.out.println("Error while awaiting or signaling thread" + exception.getMessage());
            System.out.println("Stopping the execution of play");
            return;
        }
        catch(IllegalMonitorStateException exception)
        {

            System.out.println("Error on waiting for the player : " +  exception.getMessage());
            System.out.println("Stopping the execution of play");
            return;
        }
        finally
        {
            gameLock.unlock();
        }
        try {
            if(instructionsSent.compareAndSet(false, true))
            {
                sendInstructions();

                board.paint(player1.writer(), player2.writer());
            }

            start();
        } catch (InterruptedException exception) {
            System.out.println("Game Interrupted while waiting for the Thread" + exception.getMessage());
        }
    }

    public void start() throws InterruptedException
    {
        while(isGameOver())
            {

                currentPlayer.set(getCurrentPlayer());

                gameLock.lock();


                if(Thread.currentThread().getName().equals(player1ThreadName))
                {

                    player2Turn.signal();

                    player1Turn.await();

                }
                else
                {

                    player1Turn.signal();

                    player2Turn.await();

                }

                boolean validMove = false;


                // Receive move from the current player
                while(!validMove)
                {
                    try
                    {
                        System.out.println("Currently Thread : " + Thread.currentThread().getName() + "'s turn is to take input");
                        System.out.println("It's turn of player with symbol of " + currentPlayer.get().seed());
                        String move = receiveMove(currentPlayer.get());

                        System.out.println(move);

                        int row = Integer.parseInt(move.split(",")[0]);

                        int col = Integer.parseInt(move.split(",")[1]);

                        if(isValidMove(row, col))
                        {
                            gameState = board.updateGameState(currentPlayer.get().seed(), row, col);

                            validMove = true;

                            currentPlayer.get().writer().println("Valid Move");

                            currentPlayer.get().writer().flush();

                            printBoard(player1.writer(), player2.writer());
                        }
                        else
                        {
                            currentPlayer.get().writer().println("Invalid Move,Please Try Again");

                            currentPlayer.get().writer().flush();
                        }
                    } catch(IOException e)
                    {
                        System.out.println("Error reading player's move, Game is Ending...");

//                        informOtherUser();

                        return;
                    } catch(NumberFormatException e)
                    {

                        currentPlayer.get().writer().println("Invalid input format. Please enter row and column separated by a comma.");
                        currentPlayer.get().writer().flush();


                    } catch(ArrayIndexOutOfBoundsException e)
                    {
                        currentPlayer.get().writer().println("Invalid input. Please enter row and column in the range of Board.");

                        currentPlayer.get().writer().flush();

                    } catch(InterruptedException e)
                    {
                        System.out.println("Interruption while receiving moves" + e.getMessage());
                    }
                }

                handleGameState(currentPlayer.get().writer());
                switchTurn();
                gameLock.unlock();
            }


        if(instructionsSent.compareAndSet(true, false))
        {
            sendEndingInstructions(player1.writer(), player2.writer());
//            sendEndingInstructions(player1.writer());

        }
    }
    private void sendInstructions()
    {
        String instructions1 = "Game Board consist of 3*3 grid of cells.\nPlayer will take turns placing their symbol on empty cells";
        String instructions2 = "The first player to get three of their symbols in a row, column, or diagonal wins the game.\nIf all cells are filled and no player has achieved three in a row, the game is a draw.";
        String instructions3 = "Input Format: Enter the row and column numbers separated by a comm(x,y).Here X and Y are in range of (0-2)";

        participants.forEach(player -> {
            player.writer().println(instructions1);
            player.writer().println(instructions2);
            player.writer().println(instructions3);
            player.writer().flush();
        });
        player1.writer().println("Symbol X is assigned to you for your representation on board");
        player1.writer().flush();
        player2.writer().println("Symbol O is assigned to you for your representation on board");
        player2.writer().flush();

    }

    private void printBoard(PrintWriter writer1, PrintWriter writer2) throws InterruptedException
    {
        board.paint(writer1, writer2);
     }

    private boolean isGameOver()
    {
        return gameState == State.PLAYING;
    }

    private Player getCurrentPlayer()
    {
        return currentPlayerIndex == 0 ? player1 : player2;
    }


    private String receiveMove(Player currentPlayer) throws IOException,InterruptedException
    {
        String move;
        currentPlayer.writer().println("Player (" + currentPlayer.seed().getIcon() + "), enter your move (row,col): ");
        currentPlayer.writer().flush();
        move = currentPlayer.reader().readLine();
        return move;
    }

    private boolean isValidMove(int row, int col)
    {
        return row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].content == Seed.NO_SEED;
    }

    private void handleGameState(PrintWriter writer)
    {
        switch(gameState)
        {
            case PLAYING:
                writer.println("PLAYING");
                writer.flush();
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

    private void sendEndingInstructions(PrintWriter writer1, PrintWriter writer2)
    {
        String instruction = "Game Over!!Hope You Enjoyed the game,Connect Again to Server for Starting new Game.";
        writer1.println(instruction);
        writer1.flush();
        writer2.println(instruction);
        writer2.flush();
    }

    private void informOtherUser()
    {
        if(currentPlayerIndex == 0)
        {
            player2.writer().println("Player 1 disconnected.");
            player2.writer().println("Game is ending.");
            player2.writer().flush();
        }
        else
        {
            player1.writer().println("Player 2 disconnected.");
            player1.writer().println("Game is ending.");
            player1.writer().flush();
        }
    }
    private void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

}

