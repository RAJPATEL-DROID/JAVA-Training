package com.chatapp.version1;

import java.net.ServerSocket;
import java.net.Socket;

public class Server
{


    ServerSocket server;

    Socket socket;

    Server()
    {
        try
        {
            server = new ServerSocket(9090);
            while(!server.isClosed())
            {
                socket = server.accept();

                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();
            }
            server.close();
        } catch(Exception e)
        {
            System.out.println(e.toString());
        }

    }

    public static void main(String[] args)
    {
        System.out.println("Server is started....");
        System.out.println("Waiting for Connection...");
        new Server();
    }


}
