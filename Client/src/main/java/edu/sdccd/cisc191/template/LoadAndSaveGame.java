package edu.sdccd.cisc191.template;

import java.io.*;

public class LoadAndSaveGame {
    private TicTacToeClient client;


    public LoadAndSaveGame(TicTacToeClient client){
        this.client = client;
    }
    /**
     * Method for saving the game to a file
     */
    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tic_tac_toe_save.dat"))) {
            // Save the board state
            String[][] boardState = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardState[i][j] = client.buttons[i][j].getText();
                }
            }
            out.writeObject(boardState);
            out.writeBoolean(client.x);  // Save whose turn it is
            out.writeInt(client.Xwins);      // Save X's score
            out.writeInt(client.Owins);      // Save O's score
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    /**
     * Method for loading in the saved game
     */
    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tic_tac_toe_save.dat"))) {
            // Load the board state
            String[][] boardState = (String[][]) in.readObject();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    client.buttons[i][j].setText(boardState[i][j]);
                }
            }
            client.x = in.readBoolean();  // Load whose turn it is
            client.Xwins = in.readInt();      // Load X's score
            client.Owins = in.readInt();      // Load O's score
            // Calling the updateHeader method
            client.updateHeader();
            client.Check();

            System.out.println("Game loaded successfully!.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the game: " + e.getMessage());
        }


    }
    
}
