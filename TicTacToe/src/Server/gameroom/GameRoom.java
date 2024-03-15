package Server.gameroom;

import Server.game.Game;
import Server.gameboard.Seed;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameRoom extends Thread
{
    private final Integer portNo;

    private final String sessionId;

    private ServerSocket gameRoomSocket;

    private Integer participants=0;

    private final Lock gameLock = new ReentrantLock();

    private final Condition playerJoined = gameLock.newCondition();

    private volatile boolean gameState = false;



    private Game game = null;

    public GameRoom(String gameRoomId)
    {
        List<String> lst = List.of(gameRoomId.split("-"));

        System.out.println("Room created with Id : " + gameRoomId);

        this.sessionId = lst.get(0);

        System.out.println("sessionId : " + sessionId);

        this.portNo = Integer.valueOf(lst.get(1));

        game = new Game();
    }

    @Override
    public void run()
    {
        try
        {
            gameRoomSocket = new ServerSocket(portNo);

            System.out.println("Game room started on port " + portNo);

            System.out.println(Thread.currentThread().getName());

            while(true)
            {

                Socket playerSocket = gameRoomSocket.accept();

                new Thread(()->{
                    try
                    {
                        validatePlayers(playerSocket);

                    } catch(Exception exception)
                    {
                        System.out.println("Error in validating the player at game room :" + exception.getMessage());

                        exception.printStackTrace();
                    }
                }).start();
            }

        }
        catch(Exception e)
        {

            System.err.println("Error starting game room: " + e.getMessage());

        }
        finally
        {
            try
            {

                gameRoomSocket.close();
            }
            catch(Exception exception)
            {

                System.out.println("Error closing game room socket : " + exception.getMessage());

            }
        }
    }

    private void validatePlayers(Socket playerSocket) throws IOException
    {
            BufferedReader reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

            PrintWriter writer = new PrintWriter(playerSocket.getOutputStream());

            String ssid = reader.readLine();

            if(!ssid.equals(sessionId))
            {
                writer.println("Session Id is invalid!!");

                writer.flush();

                reader.close();

                writer.close();

                return;
            }

            if(participants < 2){
                Player player = new Player(reader,writer,(participants < 1)?Seed.CROSS:Seed.NOUGHT);

                participants++;

                writer.println("Welcome to Game");

                writer.flush();

                handlePlayer(player);

            }else{
                writer.println("Maximum number of players reached for this game room. Cannot join.");

                writer.flush();

                reader.close();

                writer.close();
            }
    }

    private void handlePlayer(Player player)
    {
        game.addPlayerAndStartGaming(player);
    }

}
