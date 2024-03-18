package Server;

import Server.clienthandler.ClientHandler;
import Server.gamemanager.GameManager;
import Server.util.LoggingUtils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerClass
{
    private static final int SERVER_PORT = 12345;

    private static final Logger logger = LoggingUtils.getLogger();
    private static final ExecutorService clientHandlerThreads = Executors.newCachedThreadPool();

    public static void main(String[] args)
    {
        try(ServerSocket socket = new ServerSocket(SERVER_PORT);)
        {
            System.out.println("Server listening on port : " + SERVER_PORT);

            GameManager gameManager = new GameManager();

            while(true)
            {
                Socket userSocket = socket.accept();

                var clientId = userSocket.getRemoteSocketAddress().toString().split(":")[1];

                logger.info("Client with id : " + clientId + " connected.");

                ClientHandler clientHandler = new ClientHandler(userSocket, gameManager, clientId);

                clientHandlerThreads.execute(clientHandler);
            }
        }
        catch(IOException exception)
        {
            logger.warning("Server having problem on waiting for connection"  + exception.getMessage());
        }
    }
}