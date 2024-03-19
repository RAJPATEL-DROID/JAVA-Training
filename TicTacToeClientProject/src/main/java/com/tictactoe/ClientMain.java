package com.tictactoe;

import com.tictactoe.utils.ConfigReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientMain
{
    static ConfigReader configReader = new ConfigReader("src/main/java/com/tictactoe/utils/config.json");

    public static final String SERVER_ADDRESS = configReader.get_SERVER_IP_ADDRESS();

    private static final int SERVER_PORT = configReader.get_SERVER_PORT();

    static String sessionId;

    static String portNo;

    public static void main(String[] args)
    {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Connected to server.");

            String instruction;

            readServerInstructions(reader);

            String userChoice = "";

            userChoice = takeUserInput(userChoice, userInputReader, writer);

            boolean shouldContinue = true;

            while(shouldContinue)
            {
                if( userChoice.equals(configReader.get_CHOICE_FOR_NEW_GAME_ROOM() ) )
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

                            shouldContinue = false;

                            GameHandler gameHandler = new GameHandler();

                            gameHandler.playGame(sessionId, portNo, 1);

                            break;
                        }
                    }
                }
                else if(userChoice.equals( configReader.get_JOIN_GAME_ROOM_CHOICE() ))
                {
                    System.out.println(reader.readLine());

                    System.out.println("----------------");

                    sessionId = userInputReader.readLine();

                    writer.println(sessionId);

                    writer.flush();

                    while((instruction = reader.readLine()) != null)
                    {
                        if(instruction.equals("No game room exist on this session Id!"))
                        {
                            System.out.println(instruction);

                            readServerInstructions(reader);

                            userChoice = takeUserInput(userChoice, userInputReader, writer);

                            break;
                        }

                        if(instruction.contains("Port No of GameRoom :"))
                        {
                            System.out.println(instruction);

                            portNo = (String) Arrays.stream(instruction.split(":")).toArray()[1];

                            System.out.println(portNo);

                            shouldContinue = false;

                            GameHandler gameHandler = new GameHandler();

                            gameHandler.playGame(sessionId, portNo, 2);

                            break;
                        }
                    }
                }
                else
                {
                    System.out.println(reader.readLine());

                    readServerInstructions(reader);

                    userChoice = takeUserInput(userChoice, userInputReader, writer);

                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Server refused connection, Try After sometime!!");
        }
        finally
        {
            System.out.println("Closing connection with Server....");
        }
    }

    private static void readServerInstructions(BufferedReader reader)
    {
        try
        {
            String instruction;

            while((instruction = reader.readLine()) != null)
            {
                if(instruction.equals("TERMINATE"))
                {
                    break;
                }

                System.out.println(instruction);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in reading Reader Stream from Server");
        }
    }

    private static String takeUserInput(String user, BufferedReader term, PrintWriter writer)
    {
        System.out.println("Enter your Option:");

        try
        {
            user = term.readLine();

            writer.println(user);

            writer.flush();
        }
        catch(IOException e)
        {
            System.out.println("Error in reading Standard Input");
        }

        return user;
    }


}
