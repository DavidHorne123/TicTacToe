package edu.sdccd.cisc191.template;


import javafx.scene.control.Button;

public class GameBoardButton extends Button {
    private int row;
    private int column;
    private TicTacToeClient buttons;

    private TicTacToeClient gameBoard;

    // constructor created to initialize the button with its position and the game board reference
    public GameBoardButton(int row, int col, TicTacToeClient gameBoard) {
        // Assign the row and column variables to the respective fields
        this.row = row;
        this.column = column;

        // Stores the game board reference to interact with the game logic
        this.gameBoard=gameBoard;
    }
    // Method to handle the logic after a button has been clicked
    public void handleButtonClick() {
        // Set the button's text to the current player's symbol (X or O)
        setText(gameBoard.getCurrentTurn());

        // Apply color styling based on the current player's symbol
        if ("X".equals(gameBoard.getCurrentTurn())) {
            setStyle("-fx-text-fill: blue; -fx-font-size: 50;"); // Blue for X
        } else if ("O".equals(gameBoard.getCurrentTurn())) {
            setStyle("-fx-text-fill: red; -fx-font-size: 50;"); // Red for O
            System.out.println("Hello world");

        }
        System.out.println(getText());

        // Disable the button after it's been clicked
        setDisable(true);

        // Check for a win or other game state
        gameBoard.Check();

        // Switch to the next player's turn
        gameBoard.SwitchTurn();

        // Update the header to reflect the current scores and turn
        gameBoard.updateHeader();
    }

}
