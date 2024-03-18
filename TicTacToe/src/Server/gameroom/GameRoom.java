package Server.gameroom;

import Server.game.Game;
import Server.gameboard.Symbol;
import Server.player.Player;
import Server.util.LoggingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class GameRoom extends Thread
{
    private final Integer portNo;

    private final String sessionId;

    private static final Logger logger = LoggingUtils.getLogger();

    private final AtomicInteger participants= new AtomicInteger(0);

    private final Game game;

    public GameRoom(String gameRoomId)
    {
        List<String> lst = List.of(gameRoomId.split("-"));

        this.sessionId = lst.get(0);

        this.portNo = Integer.valueOf(lst.get(1));

        game = new Game();
    }

    @Override
    public void run()
    {
        try(ServerSocket gameRoomSocket = new ServerSocket(portNo))
        {
            logger.info("Game room started on port " + portNo);

            while(true)
            {
                Socket playerSocket = gameRoomSocket.accept();

                var playerId = playerSocket.getRemoteSocketAddress().toString().split(":")[1];

                logger.info("Player with ID : " + playerId + " connected on game room with session id " + this.sessionId );

                new Thread(()->{
                    try
                    {
                        validatePlayers(playerSocket,playerId);
                    }
                    catch(Exception exception)
                    {
                        logger.info("Error with player id "  + playerId);
                        logger.info(exception.getMessage());
                    }finally
                    {
                        try
                        {
                            if (!playerSocket.isClosed())
                            {
                                logger.info("Player with Id : " + playerId + " disconnected from game room");

                                playerSocket.close();

                            }else{
                                logger.info("Player with Id : " + playerId + " disconnected from game room");
                            }
                        }
                        catch(IOException exception)
                        {
                            logger.info("error while closing player "+ playerId + "'s socket.");
                            logger.info("Error : " + exception.getMessage());
                        }
                    }
                }).start();
            }
        }
        catch(Exception e)
        {
            logger.severe("Error in starting game room with id : " + this.sessionId);
        }
    }

    private void validatePlayers(Socket playerSocket, String playerId) throws IOException
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
                    logger.info("Player with id " + playerId + " has wrong session id");

                    writer.println("Session Id is invalid!!");

                    writer.flush();

                    return;
                }

                Player player = new Player(reader,writer,(participants.getAndIncrement() < 1)? Symbol.CROSS: Symbol.ZERO);

                writer.println("Welcome to Game");

                writer.flush();

                handlePlayer(player);
            }
            catch(NullPointerException exception)
            {
                logger.info("Player with id " + playerId  + " disconnected.");
                logger.info("Error : " + exception.getMessage());
            }
    }

    private void handlePlayer(Player player)
    {
        game.addPlayerAndStartGaming(player);
    }

}
