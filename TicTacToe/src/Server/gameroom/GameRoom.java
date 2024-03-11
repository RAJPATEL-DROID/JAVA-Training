package Server.gameroom;

import Server.clienthandler.ClientHandler;
import Server.gamemanager.GameManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameRoom extends Thread
{
    private final String roomId;

    private final Integer portNo;

    private final String sessionId;

    private ServerSocket gameRoomSocket;

    private final ExecutorService playerThreadPool;

    private volatile List<ClientHandler> participants;

    private final GameManager gameManager;

    public GameRoom(String gameRoomId, GameManager gameManager)
    {
        this.roomId = gameRoomId;
        List<String> lst = List.of(gameRoomId.split("-"));
        this.sessionId = lst.get(0);
        this.portNo = Integer.valueOf(lst.get(1));
        this.gameManager = gameManager;
        this.participants= new ArrayList<>();
        this.playerThreadPool = Executors.newFixedThreadPool(2);
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                String recievedSessionId = reader.readLine();

                if(recievedSessionId.equals(sessionId)){
                    if(participants.size() < 2){
                        ClientHandler clientHandler = new ClientHandler(playerSocket,gameManager);
                        participants.add(clientHandler);
                        playerThreadPool.submit(clientHandler);

                        if(participants.size() == 2){
                            startGame();
                        }
                    }else{
                        PrintWriter writer = new PrintWriter(playerSocket.getOutputStream());
                        writer.println("Maximum number of players reached for this game room. Cannot join.");
                        writer.flush();
                        playerSocket.close();
                    }
                }else{
                    System.out.println("Invalid session ID recieved from Player");
                    playerSocket.close();
                }
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        } finally
        {
            // Clean up resources when the game room exits
            cleanup();
            // Remove the game room from the GameManager
            gameManager.removeGameRoom(roomId);
        }
    }
        private void startGame()
    {

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
            // Shut down the player thread pool
            playerThreadPool.shutdownNow();
            // Release any other resources or perform additional cleanup
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void addParticipant(ClientHandler clientHandler){
        participants.add(clientHandler);
    }
}
