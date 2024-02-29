import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TcpClient
{
    public static void main(String[] args)
    {

        try
        {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            System.out.println("server ip address :" + Arrays.toString(serverAddress.getAddress()));

            Socket socket = new Socket(serverAddress, 9090);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(input.readLine());
            out.println("Hello Server");
            input.close();
            out.close();
            socket.close();

        } catch(UnknownHostException e)
        {
            System.out.println("Unknown Host " + e.toString());
        } catch(IOException e)
        {
            System.out.println("IO Exception : " + e.toString());

        } catch(IllegalArgumentException e)
        {
            System.out.println("Illegal argument " + e.toString());

        } catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
