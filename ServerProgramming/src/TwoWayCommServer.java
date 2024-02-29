import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TwoWayCommServer
{
    public static void main(String args[])
    {
        try
        {
            ServerSocket serverSocket  = new ServerSocket(9090);

            Socket socket = serverSocket.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str  = "", str1 ="";

            while(!str.equals("Stop")){
                str = input.readLine();
                System.out.println("Client Says " + str);
                str1 = br.readLine();
                out.println(str1);

            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }

    }

}
