package Server;

import Server.clienthandler.ClientHandler;
import Server.gamemanager.GameManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerClass
{
    private static final int SERVER_PORT = 12345;
    //    private static final int MAX_GAME_ROOMS = 10;

    private static final ExecutorService clientHandlerThreads = Executors.newCachedThreadPool();

    public static void main(String[] args)
    {

        try(ServerSocket socket = new ServerSocket(SERVER_PORT);)
        {
            System.out.println("Server started on port : " + SERVER_PORT);
            GameManager gameManager = new GameManager();
            while(true)
            {
                Socket userSocket = socket.accept();

                System.out.println("New client connected : " + userSocket);

                ClientHandler clientHandler = new ClientHandler(userSocket, gameManager);

                clientHandlerThreads.execute(clientHandler);


            }
        } catch(IOException e)
        {
            System.out.println();
        }
    }

}