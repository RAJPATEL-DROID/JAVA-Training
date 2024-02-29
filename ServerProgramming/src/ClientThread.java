import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread
{
    private Socket socket = null;

    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello Client");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientMessage = input.readLine();
            System.out.println(clientMessage);
            input.close();
            out.close();
            socket.close();
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
