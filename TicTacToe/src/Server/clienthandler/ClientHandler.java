package Server.clienthandler;

import Server.gamemanager.GameManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread
{

    private final Socket clientSocket;

    private final GameManager gameManager;

    private BufferedReader reader;

    private PrintWriter writer;

    public ClientHandler(Socket clientsocket, GameManager gameManager)
    {
        this.clientSocket = clientsocket;

        this.gameManager = gameManager;

        try
        {
            reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));

            writer = new PrintWriter(clientsocket.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("Error while reading and writing to user stream");
        }
    }

    @Override
    public void run()
    {
        try
        {
            writer.println("Welcome to Multiplayer Tic-Tac-Toe!");

            sendInstruction();

            System.out.println("Instructions sent");

            processInput();

        }
        finally
        {
            try
            {
                clientSocket.close();
            }
            catch(IOException exception)
            {

                System.out.println("error while closing the client socket" + exception.getMessage());

            }
        }
    }

    private void sendInstruction()
    {

        writer.println("To create a new game room, send the enter 1.");

        writer.println("If You already have a sessionId of GameRoom,enter 2");

        writer.println("TERMINATE");

        writer.flush();

    }

    private void processInput()
    {

        System.out.println("Taking Input");

        String inputLine;

        try
        {
            while((inputLine = reader.readLine()) != null)
            {
                if(inputLine.equals("1"))
                {
                    List<String> ids = gameManager.createAndStartNewRoom();

                    writer.println("New Room created on Port:" + ids.get(0));

                    writer.println("Here is your sessionId:" + ids.get(1));

                    writer.flush();
                }
                else if(inputLine.equals("2"))
                {
                    writer.println("Enter Session ID : ");

                    writer.flush();

                    System.out.println("----------------");

                    String sessionId = reader.readLine();
                    try
                    {
                        Integer portNo = gameManager.validateSessionAndSendPortNo(Long.valueOf(sessionId));

                        if(portNo == -1)
                        {
                            writer.println("No game room exist on this session Id!");

                            writer.flush();

                            sendInstruction();

                            System.out.println("Instructions Sent Again : ");

                        }
                        else
                        {
                            writer.println("Port No of GameRoom :"+ portNo);

                            writer.flush();

                            break;
                        }
                    }catch(NumberFormatException exception){
                        System.out.println("received null from user for session ID.");
                    }
                }
                else
                {
                    writer.println("Unknown Command!!");

                    writer.flush();

                    sendInstruction();

                    System.out.println("Instructions Sent Again : ");
                }
            }
        } catch(IOException e)
        {
            writer.println("Error Reading the Input");
        }
    }

}