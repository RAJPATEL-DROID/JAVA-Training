package Server.game;

import Server.clienthandler.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<ClientHandler> participants;
    private int currentPlayerIndex;

    public Game(List<ClientHandler> participants) {
        this.participants = new ArrayList<>(participants);
        this.currentPlayerIndex = 0;
    }

    public void start() {
        // Initialize the game state and rules
        // ...

        while (!isGameOver()) {
            ClientHandler currentPlayer = participants.get(currentPlayerIndex);
            boolean validMove = false;

            // Receive move from the current player
            while (!validMove) {
                // Implement logic to receive and validate the move from the current player
                // ...
                validMove = true; // Set to true if the move is valid
            }

            // Update the game state based on the move
            // ...

            // Switch to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % participants.size();
        }

        // Game over, handle the result
        // ...
    }

    private boolean isGameOver() {
        // Implement the game logic to determine if the game is over
        // ...
        return false; // Return true if the game is over
    }
}