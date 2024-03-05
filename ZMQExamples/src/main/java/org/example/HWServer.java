package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HWServer
{
    public static void main(String[] args)
    {
        try(ZContext context = new ZContext()){
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:5555");

            while(!Thread.currentThread().isInterrupted())
            {
                byte[] reply = socket.recv(0);
                System.out.println("Rec : "  + " : [ " + new String(reply, ZMQ.CHARSET) + " ] ");

                Thread.sleep(1000);

                String response = "world";
                socket.send(response.getBytes(ZMQ.CHARSET),0);

            }
        } catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}