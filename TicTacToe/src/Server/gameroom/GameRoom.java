package Server.gameroom;

import Server.game.Game;
import Server.gameboard.Seed;
import Server.gamemanager.GameManager;
import Server.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom extends Thread
{


    private final Integer portNo;

    private final String sessionId;

    private ServerSocket gameRoomSocket;

    private final CopyOnWriteArrayList<Player> participants;

    private final GameManager gameManager;
    private final ExecutorService executorService;

    private static volatile boolean checkFlag = false;

    public GameRoom(String gameRoomId, GameManager gameManager)
    {
        List<String> lst = List.of(gameRoomId.split("-"));
        this.sessionId = lst.get(0);
        this.portNo = Integer.valueOf(lst.get(1));
        this.gameManager = gameManager;
        this.participants = new CopyOnWriteArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run()
    {
        try
        {
            gameRoomSocket = new ServerSocket(portNo);
            System.out.println("Game room started on port " + portNo);

            while(true)
            {
                Socket playerSocket = gameRoomSocket.accept();
                executorService.submit(() -> handlePlayer(playerSocket));
            }
            
            
        } catch(IOException e)
        {
            System.err.println("Error starting game room: " + e.getMessage());
        } finally
        {
            cleanup();
        }
    }

    private void handlePlayer(Socket playerSocket){
        BufferedReader reader = null;
        PrintWriter writer = null;
        Player player = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            writer = new PrintWriter(playerSocket.getOutputStream());
            String receivedSessionId = reader.readLine();
            System.out.println(receivedSessionId);
            if(receivedSessionId.equals(sessionId))
            {
                if(participants.size() < 2)
                {
                    if(participants.isEmpty())
                    {
                        player = new Player(reader, writer, Seed.CROSS);
                    }else{
                        checkFlag = true;
                        player = new Player(reader,writer,Seed.NOUGHT);
                    }
                    participants.add(player);
                    System.out.println("User Connected");
                    writer.println("Welcome to Game");
                    writer.flush();
                }
                else
                {
                    writer.println("Maximum number of players reached for this game room. Cannot join.");
                    writer.flush();
                    return;
                }
            }
            else
            {
                writer.println("Session Id is invalid!!");
                writer.flush();
                System.out.println("Invalid session ID received from Player");
                return;
            }

            while(!checkFlag)
            {
                Thread.onSpinWait();
            }

            startGame();
        } catch(IOException e)
        {
            System.err.println("Error handling player connection: " + e.getMessage());
        } finally
        {
            // Close resources
            participants.remove(player);
            closeResources(reader, writer, playerSocket);
        }
    }

    private void startGame()
    {
            Game game = new Game(participants);
            game.start();
    }
    private void cleanup()
    {
        try
        {
            // Close the server socket
            if(gameRoomSocket != null && !gameRoomSocket.isClosed())
            {
                gameRoomSocket.close();
            }
            // Release any other resources or perform additional cleanup
            executorService.shutdown();
        } catch(IOException e)
        {
            System.err.println("Error in Closing the GameRoom Socket : " + e.getMessage());
        }
    }
    private void closeResources(BufferedReader reader, PrintWriter writer, Socket socket)
    {
        try
        {
            if(reader != null)
            {
                reader.close();
            }
            if(writer != null)
            {
                writer.close();
            }
            if(socket != null && !socket.isClosed())
            {
                socket.close();
            }
        } catch(IOException e)
        {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
