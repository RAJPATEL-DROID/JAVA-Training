package com.chatapp.version2;


import com.sun.source.tree.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    static ServerSocket server;

    static Socket socket;

    static List<ClientThread> usrList = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        int count = -1;
        System.out.println("Server is started....");
        System.out.println("Waiting for Connection...");

        try
        {
            server = new ServerSocket(9090);
            while(!server.isClosed())
            {
                socket = server.accept();

                ClientThread clientThread = new ClientThread(socket);
                clientThread.setName("User"+(++count));
                usrList.add(clientThread);
                clientThread.start();

            }

        } catch(Exception e)
        {
            System.out.println(e.toString());
        }finally
        {
            server.close();
        }

    }

    public static void broadCast(String message,ClientThread messengerUser){
        for(var user : usrList){
            if(user != messengerUser){
                user.sendMessage(message);
            }
        }
    }


}

class ClientThread extends Thread
{

    private final Socket socket;

    private BufferedReader in;

    private PrintWriter out;
    ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void sendMessage(String message){
        out.println(message);
    }

    @Override
    public void run()
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Reading();
            Writing();

        } catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void Reading()
    {
        Runnable read = () -> {
            System.out.println("Reading started for : " + socket.getRemoteSocketAddress());

            while(true)
            {
                try
                {
                    String msg = in.readLine();

                    if(!msg.isEmpty())
                    {
                        if(msg.equals("exit"))
                        {
                            System.out.println("Client " + this.getName() + " Terminated the chat");
                            Server.usrList.remove(this);
                            socket.close();
                            break;
                        }
                        Server.broadCast((Thread.currentThread().getName()) + " : " + msg,this);
                    }
                } catch(IOException e)
                {
                    System.out.println(e);
                }
            }
        };

        new Thread(read).start();
    }



    public void Writing()
    {
        Runnable write = () -> {
            System.out.println("Writer Started for  : " + socket.getRemoteSocketAddress());
            while(true)
            {
                try
                {
                    BufferedReader terminalRead = new BufferedReader(new InputStreamReader(System.in));

                    String content = terminalRead.readLine();
                    if(!content.isEmpty())
                        out.println(content);

                    if(content.equals("exit"))
                    {
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                } catch(IOException e)
                {
                    System.out.println(e);
                }
            }
        };
        new Thread(write).start();

    }
}
