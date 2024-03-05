package org.task;

import REQ_REP.BasicImplementation.Server;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class TaskSubscriber
{
    public static void main(String[] args) throws Exception {
        // Create a context
       try(ZContext context = new ZContext())
       {

           // Bind publisher sockets to three different ports
           int[] subPorts = {5555, 5556, 5557};
           int[] ackPorts = {6666, 6667, 6668};
           ZMQ.Socket[] ackPushSockets = new ZMQ.Socket[3];
           for(int i=0; i < 3; i++){
               ackPushSockets[i] = context.createSocket(SocketType.PUSH);
               ackPushSockets[i].bind("tcp://localhost:" + ackPorts[i]);

           }
           for(int i=0; i < subPorts.length; i++){
               ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
               subscriber.connect("tcp://localhost:" + subPorts[i]);
               subscriber.subscribe("".getBytes(ZMQ.CHARSET));

               Thread.sleep(2000);
//               for(int j=0;j < 10; j++)
//               {
               byte[] topic = subscriber.re(0);
               byte[] message = subscriber.recv(0);

                   System.out.println("Received:  " + new String(topic, ZMQ.CHARSET) + " - " + new String(message,ZMQ.CHARSET));

                   ackPushSockets[i].send(message, 0);

//               }
           }
       }
    }
}
