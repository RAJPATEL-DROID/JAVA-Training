package com.chatapp.version2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread1 extends Thread
{

    Socket socket;

    BufferedReader in;

    PrintWriter out;

    ClientThread1(Socket socket)
    {
        this.socket = socket;
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
                            System.out.println("Client Terminated the chat");
                            socket.close();
                            break;
                        }
                        System.out.println("Client " + socket.getRemoteSocketAddress() + " : " + msg);
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
