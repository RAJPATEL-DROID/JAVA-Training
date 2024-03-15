package Server.game;

import Server.gameboard.Board;
import Server.gameboard.Symbol;
import Server.gameboard.State;
import Server.player.Player;

import java.io.IOException;
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
    private final Lock gameLock = new ReentrantLock();

    private final Condition player1Turn = gameLock.newCondition();

    private final Condition player2Turn = gameLock.newCondition();

    private final AtomicBoolean instructionsSent = new AtomicBoolean(false);

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

                printBoard();
            }

            start();

            if(instructionsSent.compareAndSet(true, false))
            {
                System.out.println(Thread.currentThread().getName());
                sendEndingInstructions();
            }
            System.out.println(Thread.currentThread().getName());
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

                    if(!gameState.equals(State.PLAYING))
                    {
                        return;

                    }
                }
                else
                {

                    player1Turn.signal();

                    player2Turn.await();

                    if(!gameState.equals(State.PLAYING))
                    {
                        return;
                    }
                }

                boolean validMove = false;


                // Receive move from the current player
                while(!validMove)
                {
                    try
                    {
                        System.out.println("Currently Thread : " + Thread.currentThread().getName() + "'s turn is to take input");
                        System.out.println("It's turn of player with symbol of " + currentPlayer.get().symbol());

                        String move = receiveMove(currentPlayer.get());

                        System.out.println(move);

                        int row = Integer.parseInt(move.split(",")[0]);

                        int col = Integer.parseInt(move.split(",")[1]);

                        if(isValidMove(row, col))
                        {
                            gameState = board.updateGameState(currentPlayer.get().symbol(), row, col);

                            validMove = true;

                            currentPlayer.get().writer().println("Valid Move");

                            currentPlayer.get().writer().flush();

                            printBoard();
                        }
                        else
                        {
                            currentPlayer.get().writer().println("Invalid Move,Please Try again with valid move.");

                            currentPlayer.get().writer().flush();
                        }
                    }
                    catch(NumberFormatException e)
                    {
                        currentPlayer.get().writer().println("Invalid input format. Try again with row and column separated by a comma.");

                        currentPlayer.get().writer().flush();
                    }
                    catch(ArrayIndexOutOfBoundsException e)
                    {
                        currentPlayer.get().writer().println("Invalid input. Try again with values in the range of Board.");

                        currentPlayer.get().writer().flush();
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error reading player's move, Game is Ending...");

                        participants.remove(currentPlayer.get());

                        gameState = State.STOPPED;

                        break;
                    }
                    catch(NullPointerException exception){
                        System.out.println("Player with symbol " + currentPlayer.get().symbol() + "  disconnected, " + exception.getMessage());

                        participants.remove(currentPlayer.get());

                        gameState = State.STOPPED;

                        break;
                    }
                    catch(InterruptedException e)
                    {
                        System.out.println("Interruption while receiving moves" + e.getMessage());

                        participants.remove(currentPlayer.get());

                        gameState = State.STOPPED;

                        break;
                    }
                }

                handleGameState();

                if(!gameState.equals(State.PLAYING))
                {
                    if((currentPlayerIndex == 0))
                    {
                        System.out.println("signaling the waiting state");
                        player2Turn.signalAll();
                    }
                    else
                    {
                        player1Turn.signalAll();
                    }
                    gameLock.unlock();
                    break;
                }

                switchTurn();
                gameLock.unlock();
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

    private void printBoard() throws InterruptedException
    {
        board.print(participants);
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
        currentPlayer.writer().println("Player (" + currentPlayer.symbol().getIcon() + "), enter your move (row,col): ");
        currentPlayer.writer().flush();
        move = currentPlayer.reader().readLine();
        return move;
    }

    private boolean isValidMove(int row, int col)
    {
        return row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].content == Symbol.NO_SYMBOL;
    }

    private void handleGameState()
    {
        switch(gameState)
        {
            case PLAYING:
                participants.forEach(participant ->
                {
                    participant.writer().println("PLAYING");
                    participant.writer().flush();
                });
                break;
            case DRAW:
                participants.forEach(participant ->
                {
                    participant.writer().println("DRAW");
                    participant.writer().flush();
                });
                break;
            case CROSS_WON:
                participants.forEach(participant ->
                {
                    participant.writer().println("CROSS_WON");
                    participant.writer().flush();
                });
                break;
            case ZERO_WON:
                participants.forEach(participant ->
                {
                    participant.writer().println("ZERO_WON");
                    participant.writer().flush();
                });
                break;
            case STOPPED:
                informOtherUser();
        }
    }

    private void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private void informOtherUser()
    {
        participants.forEach(
                (participant) -> {
                    if(!currentPlayer.get().equals(participant))
                    {
                        participant.writer().println("Game can't be continued due to some server error.Connect back to server.");

                        participant.writer().flush();
                    }
                });
    }

    private void sendEndingInstructions()
    {
        String instruction = "Game Over!!Hope You Enjoyed the game,Connect Again to Server for Starting new Game.";

        participants.forEach(
                (participant) ->
                {
                    participant.writer().println(instruction);

                    participant.writer().flush();
                });
    }
}

