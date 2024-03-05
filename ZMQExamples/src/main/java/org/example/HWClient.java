package org.example;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class HWClient
{
    public static void main(String[] args)
    {
        try(ZContext context = new ZContext()){
            System.out.println("Connecting to server..");
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);

            socket.connect("tcp://localhost:5555");
            for(int requestNo = 0; requestNo != 100 ; requestNo++){
                String request = "Hello";
                System.out.println("Sending Hello " + requestNo);
                socket.send(request.getBytes(ZMQ.CHARSET),0);

                byte[] reply =socket.recv(0);
                System.out.println("Recieved " + new String(reply, ZMQ.CHARSET) + " " + requestNo );

            }

        }
    }
}
