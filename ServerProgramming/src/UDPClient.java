import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket clientSocket = new DatagramSocket(0);

            byte[] sendData = new byte[1024];

            byte[] recieveData = new byte[1024];

            InetAddress serverAddress = InetAddress.getByName("localhost");

            String sentData = "Hello server";
            sendData = sentData.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,serverAddress,9090);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(recieveData,recieveData.length);
            clientSocket.receive(receivePacket);
            recieveData = receivePacket.getData();
            String recievedData = new String(recieveData);
            System.out.println("From Server : " + recievedData);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
