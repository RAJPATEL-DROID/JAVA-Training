package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameHandler {


    public void playGame(String sessionId, String portNo,int turn) throws IOException {
        try(Socket gameSocket = new Socket(ClientMain.SERVER_ADDRESS, Integer.parseInt(portNo)); PrintWriter writer = new PrintWriter(gameSocket.getOutputStream()); BufferedReader reader = new BufferedReader(new InputStreamReader(gameSocket.getInputStream())); BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.println("Playing Game!!!");

            writer.println(sessionId);

            writer.flush();

            String response = reader.readLine();

            switch(response)
            {
                case "Maximum number of players reached for this game room. Cannot join." ->
                {
                    System.out.println(response);

                    System.out.println("Try connecting after some time or create new room.");

                }
                case "Session Id is invalid!!" ->
                {
                    System.out.println(response);

                    System.out.println("Try connecting with correct session Id.");

                }
                case "Welcome to Game" ->
                {
                    System.out.println("Welcome to Game");

                    System.out.println("----------------");

                    readGameInstruction(reader);

                    System.out.println("----------------");

                    printBoard(reader);

                    System.out.println("----------------");

                    gameLogic(reader,writer,userInputReader,turn);
                }
            }
        } catch(UnknownHostException e)
        {
            System.out.println("IP address of the host could not be determined.");
        } catch(IOException e)
        {
            System.out.println("Error creating the Socket!!");
        }

    }

    private void gameLogic(BufferedReader reader,PrintWriter writer,BufferedReader userInputReader,int turn){

        Boolean firstUserMove = true;
        try
        {
            String result = "PLAYING";

            while(result.contains("PLAYING"))
            {
                if( (turn == 1) && !firstUserMove){
                    printBoard(reader);
                    firstUserMove = !firstUserMove;
                }else if( (turn == 2) && firstUserMove){
                    printBoard(reader);
                    firstUserMove = !firstUserMove;
                }
                takeUserMove(reader, writer, userInputReader);

                String validationResult = reader.readLine();

                if(validationResult == null)
                {
                    return;
                }

                if(validationResult.contains("Try Again"))
                {

                    while(validationResult.contains("Try Again"))
                    {

                        System.out.println(validationResult);

                        takeUserMove(reader, writer, userInputReader);

                        validationResult = reader.readLine();
                    }
                }

                printBoard(reader);
                if( (turn == 1) && firstUserMove){
                    result = reader.readLine();
                    System.out.println(result);
                    firstUserMove = !firstUserMove;
                }else if( (turn == 2) && !firstUserMove){
                    result = reader.readLine();
                    System.out.println(result);
                    firstUserMove = !firstUserMove;
                }
            }

            System.out.println(result);

            readGameEndingInstructions(reader);

        } catch(IOException e)
        {
            System.err.println("Error Reading Input Stream from Server" + e.getMessage());
        }
    }

    private static void readGameInstruction(BufferedReader reader) throws IOException
    {
        String instructions;

        while((instructions = reader.readLine()) != null)
        {

            System.out.println(instructions);


            if(instructions.contains("representation"))
            {
                break;
            }
        }
    }

    private static void printBoard(BufferedReader reader) throws IOException
    {
        String rows;
        int cntRows = 0;
        while((rows = reader.readLine()) != null)
        {
            System.out.println(rows);
            cntRows++;
            if(cntRows == 6)break;
        }
    }

    private static void takeUserMove(BufferedReader reader, PrintWriter writer, BufferedReader userInputReader) throws IOException
    {
        String serverRequest = reader.readLine();
        if(serverRequest == null)
        {
            System.out.println("Server Disconnected");
            return;
        }
        System.out.println(serverRequest);

        String move = userInputReader.readLine();

        System.out.println(move);

        writer.println(move);

        writer.flush();
    }

    private void readGameEndingInstructions(BufferedReader reader) throws IOException
    {

        System.out.println(reader.readLine());
    }
}
