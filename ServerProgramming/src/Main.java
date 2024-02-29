import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
    public static void main(String[] args)
    {
        // Multi User

//        try{
//            ServerSocket serverSocket = new ServerSocket(9090);
//            System.out.println("Waiting for clients...");
//            boolean stop = false;
//            while(!stop){
//                Socket socket = serverSocket.accept();
//                PrintWriter out = new PrintWriter(socket.getOutputStream());
//                out.println("hello Client");
//                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String clientInput = input.readLine();
//                System.out.println(clientInput);
//                input.close();
//                out.close();
//                socket.close();
//            }
//            serverSocket.close();
//        }
//        catch(Exception e){
//            System.out.println(e.toString());
//        }

        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            boolean stop = false;
            while(!stop){
                System.out.println("Waiting for the client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client is connected");
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}