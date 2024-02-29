package com.chatapp.version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    static private Socket socket;

    static PrintWriter out;
    static BufferedReader in;

    public static void main(String[] args)
    {

        try
        {
            System.out.println("Connecting to a server...");
            socket = new Socket("10.20.40.197", 9090);
            while(true){
                if(socket.isConnected()){
                    break;
                }
            }
            System.out.println("Connection done.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Reading();
            Writing();

            
        } catch(Exception e)
        {
            e.printStackTrace();
        }



    }

    public static void Reading()
    {
        Runnable read = () -> {
            while(true)
            {
                try
                {
                    String msg = in.readLine();

                    if(!msg.isEmpty())
                    {
                        if(msg.equals("exit"))
                        {
                            System.out.println("Server Terminated the chat");
                            socket.close();
                            break;
                        }
                        System.out.println("Server " + socket.getRemoteSocketAddress() + " : " + msg);
                    }
                } catch(IOException e)
                {
                    System.out.println(e);
                }
            }
        };

        new Thread(read).start();
    }

    public static void Writing()
    {
        Runnable write = () -> {

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
                        System.out.println("Client terminated the chat");
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
