package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;

public class clientClass
{
    private static final String SERVER_ADDRESS = "localhost"; // Server address

    private static final int SERVER_PORT = 12345; // Server port

    private static String sessionId = null;

    private static String portNo = null;

    public static void main(String[] args)
    {
        try
        {
            // Connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server.");
            // Set up input
            // and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
            String instruction = "";
            readInstructions(instruction, reader, writer);

            String userChoice = "";
            userChoice = takeUserInput(userChoice, terminalReader, writer);

            boolean shoudlContinue = true;
            while(shoudlContinue){
                if(Objects.equals(userChoice, "CREATE_NEW_ROOM"))
                {

                    while((instruction = reader.readLine()) != null)
                    {
                        System.out.println(instruction);
                        if(instruction.contains("Port"))
                        {
                            portNo = (String) Arrays.stream(instruction.split(":")).toArray()[1];
                            System.out.println(portNo);
                        }
                        if(instruction.contains("sessionId"))
                        {
                            sessionId = (String) Arrays.stream(instruction.split(":")).toArray()[1];
                            System.out.println(sessionId);
                            shoudlContinue = false;
                            playGame(sessionId,portNo);
                            break;
                        }
                    }
                }
                else if(userChoice.equals("JOIN_ROOM"))
                {

                    System.out.println(reader.readLine());
                    System.out.println("----------ClientSide");
                    sessionId = terminalReader.readLine();
                    writer.println(sessionId);
                    writer.flush();
                    while((instruction = reader.readLine()) != null)
                    {
                        System.out.println(instruction);
                        if(instruction.contains("Port No of GameRoom :")){
                            portNo = (String) Arrays.stream(instruction.split(":")).toArray()[1];
                            System.out.println(portNo);
                        }
                        if(instruction.equals("No game room exist on this session Id!"))
                        {
                            readInstructions(instruction, reader, writer);
                            userChoice = takeUserInput(userChoice, terminalReader, writer);
                            System.out.println(userChoice + " Hello ");
                            break;
                        }
                        else if(instruction.equals("Enjoy the game!!"))
                        {
                            shoudlContinue = false;
                            playGame(sessionId,portNo);
                            break;
                        }
                    }
                }
                else
                {
                    System.out.println(reader.readLine());
                    readInstructions(userChoice, reader, writer);
                    userChoice = takeUserInput(userChoice, terminalReader, writer);

                }
            }
            // Close the connection
            socket.close();
        } catch(IOException e)
        {
            System.out.println("Server refused connection, Try After sometime!!");
        }
    }

    private static void playGame(String sessionId,String portNo){
        try(Socket gameSocket = new Socket(SERVER_ADDRESS, Integer.parseInt(portNo)))
        {
            PrintWriter writer = new PrintWriter(gameSocket.getOutputStream());
            writer.println(sessionId);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            String response = reader.readLine();

            if (response.equals("Maximum number of players reached for this game room. Cannot join.")) {
                System.out.println(response);
                System.out.println("Try connecting after some time or create new room.");
                // Handle the case where the GameRoom is full
                // You can prompt the user to try again later or take any other appropriate action
            } else {
                System.out.println("Game is starting...");
                // Implement game logic and communication with the server here


            }


            gameSocket.close();
        }
         catch(UnknownHostException e)
        {
                System.out.println("IP address of the host could not be determined.");
        }
        catch(IOException e){
                System.out.println("Error creating the Socket!!");
        }

    }
    private static String takeUserInput(String user, BufferedReader term, PrintWriter writer)
    {
        System.out.println("Enter your Option:");
        try
        {
            user = term.readLine();
            System.out.println(user);
            writer.println(user);
            writer.flush();
        } catch(IOException e)
        {
            System.out.println("Error in reading Standard Input");
        }

        return user;
    }

    private static void readInstructions(String instruction, BufferedReader reader, PrintWriter writer)
    {
        // Read server instructions
        try
        {
            while((instruction = reader.readLine()) != null)
            {
                if(instruction.equals("TERMINATE"))
                {
                    break;
                }
                System.out.println(instruction);
            }
        } catch(IOException e)
        {
            System.out.println("Error in reading Reader Stream from Server");
        }
    }

}
