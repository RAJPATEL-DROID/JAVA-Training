package Server.gameroom;

import Server.game.Game;
import Server.gameboard.Symbol;
import Server.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRoom extends Thread
{
    private final Integer portNo;

    private final String sessionId;

    private final AtomicInteger participants= new AtomicInteger(0);

    private final Game game;

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
        try(ServerSocket gameRoomSocket = new ServerSocket(portNo))
        {
            System.out.println("Game room started on port " + portNo);

            System.out.println(Thread.currentThread().getName());
            while(true)
            {
                Socket playerSocket = gameRoomSocket.accept();
                System.out.println(playerSocket.getRemoteSocketAddress());
                new Thread(()->{
                    try
                    {
                        validatePlayers(playerSocket);
                    }
                    catch(Exception exception)
                    {
                        System.out.println("Player with Id : " + playerSocket.getRemoteSocketAddress().toString().split(":")[1]  + " disconnected from game room");
                    }finally
                    {
                        try
                        {
                            System.out.println("Player with Id : " + playerSocket.getRemoteSocketAddress().toString().split(":")[1] + " disconnected from game room");
                            if (!playerSocket.isClosed())
                            {
                                playerSocket.close();
                            }
                        }
                        catch(IOException exception)
                        {
                            System.out.println("error while closing player "+ playerSocket.getRemoteSocketAddress().toString().split(":")[1] + "'s socket : "+ exception.getMessage());
                        }
                    }
                }).start();
            }
        }
        catch(Exception e)
        {
            System.err.println("Error starting game room: " + e.getMessage());
        }
    }

    private void validatePlayers(Socket playerSocket) throws IOException
    {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(playerSocket.getOutputStream()))
            {
                if(participants.get() == 2)
                {
                    writer.println("Maximum number of players reached for this game room. Cannot join.");

                    writer.flush();

                    return;
                }
                else
                {
                    writer.println("Connected to Game Room");
                    writer.flush();
                }

                String ssid = reader.readLine();

                if(!ssid.equals(sessionId))
                {
                    System.out.println("Player " + playerSocket.getRemoteSocketAddress().toString().split(":")[1] + " has wromg session id");
                    writer.println("Session Id is invalid!!");

                    writer.flush();

                    return;
                }


                Player player = new Player(reader,writer,(participants.getAndIncrement() < 1)? Symbol.CROSS: Symbol.ZERO);

                writer.println("Welcome to Game");

                writer.flush();

                handlePlayer(player);
            }catch(NullPointerException exception){
                System.out.println("Client Disconnected" + exception.getMessage());
            }
    }

    private void handlePlayer(Player player)
    {
        game.addPlayerAndStartGaming(player);
    }

}
