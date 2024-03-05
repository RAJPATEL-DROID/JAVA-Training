package org.task;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class TaskPublisher
{
    public static void main(String[] args) throws Exception
    {
        // Create a context
        try(ZContext context = new ZContext();){

            // Bind publisher sockets to three different ports
            int[] pubPorts = {5555, 5556, 5557};
            int[] ackPorts = {6666, 6667, 6668};

            ZMQ.Socket[] ackPullSockets = new ZMQ.Socket[3];
            for(int i = 0; i < 3; i++)
            {
                ackPullSockets[i] = context.createSocket(SocketType.PULL);
                ackPullSockets[i].connect("tcp://*:" +ackPorts[i]);
            }

            for(int i=0; i< 3; i++)
            {
                ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
                publisher.bind("tcp://:" + pubPorts[i]);

//                for(int j = 0; j < 10; j++)
//                {
                    String topic = "Topic "  + ;
                    String message = "message  " + i;

                    publisher.sendMore(topic.getBytes(ZMQ.CHARSET));
                    publisher.send(message.getBytes(ZMQ.CHARSET));

                    System.out.println("Published : " + topic + " - " + message);

//                }

                String ack = new String(ackPullSockets[i].recv(), ZMQ.CHARSET);
                System.out.println("Acknowledgment recieved on port " + ackPorts[i] + " :" + ack);
            }
        }
    }
}
