package edu.sdccd.cisc191.template;

// import statements
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import static edu.sdccd.cisc191.template.ServerDetail.displayActionLog;
import static javafx.application.Application.launch;

// The TicTacToe class extends the JavaFX Application
// Added generics


public class TicTacToeClient extends Application {


    private LoadAndSaveGame loadAndSaveGame;

    private Date startTime;
    private boolean isplayed = false;

    private ShowMoves actionLogTree = new ShowMoves(); // Create a BST to store moves

    //static final String SERVER_ADDRESS = "localhost";
    //static final int SERVER_PORT = 5678;

    // Making labels to display the score for players X and O
    // and the current player's turn
    private boolean gameOver = false;
    private final GameBoardLabel Xscore = new GameBoardLabel();
    private final GameBoardLabel Oscore = new GameBoardLabel();
    private final GameBoardLabel Turn = new GameBoardLabel();

    // Variables to store the number of wins for players X and O
    public int Xwins = 0; // Tracks the number of games X has won
    public int Owins = 0; // Tracks the number of games O has won

    // 2D array of type Button where each element is a button object
    // used generics
    public static Button[][] T = new Button[3][3]; //array representation of the game board
    public boolean x = false; // tracks whether it's X's or O'x turn
    public Button[][] buttons = new Button[3][3];

    /**
     * launches the javaFX applicatgion
     *
     * @param args
     */
    public static void main(String[] args) {
        // launches the application
        launch(args);
    }

    /**
     * Method for updating the header
     */
    public void updateHeader() {
        // update labels
        // changes the text depending how many fishes or guesses are remaining in
        // the game
        Xscore.setText("X: " + Xwins);
        Oscore.setText("O: " + Owins);
        Turn.setText("Turn: " + getCurrentTurn());

    }


    /**
     * @param primayStage the primary stage for this application, onto which
     *                    the application scene can be set. The primary stage will be embedded in
     *                    the browser if the application was launched as an applet.
     *                    Applications may create other stages, if needed, but they will not be
     *                    primary stages and will not be embedded in the browser.
     */
    public void start(Stage primayStage) {

        loadAndSaveGame = new LoadAndSaveGame(this);

        InitialTime();

        // RESTART BUTTON
        Button restartButton = new Button("Restart");
        restartButton.setOnAction(event -> {
            restart();
        });
        gameOver = false;

        // SAVE GAME BUTTON
        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(event -> loadAndSaveGame.saveGame());

        // LOAD GAME BUTTON
        Button loadButton = new Button("Load Game");
        loadButton.setOnAction(event -> loadAndSaveGame.loadGame());

        Button displayActionLog = new Button("displayActionLog ");
        displayActionLog.setOnAction(event -> displayActionLog());

        // This button uses binary search trees
        Button displayMovesButton = new Button("Show Moves");
        displayMovesButton.setOnAction(event -> displayMoves());

        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        HBox hbox = new HBox(Xscore, Oscore, Turn, restartButton, saveButton, loadButton, displayActionLog, displayMovesButton);
        borderPane.setTop(hbox);
        x = !x;

        updateHeader();

        // nested for loop to create a 3X3 grid of buttons
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                GameBoardButton button = new GameBoardButton(row, column, this);

                // size of each button
                button.setMinSize(160, 150);
                // set the font size of the text to 50
                button.setStyle("-fx-font-size: 50;");

                // Create final variables for row and column
                final int r = row;
                final int c = column;

                // Sets the action event for when the button is clicked
                button.setOnAction(event -> {
                    button.handleButtonClick();
                    // used generics
                    // Assigning a button object to a specific position
                    TicTacToeClient.T[r][c] = button;
                    // stores the clicked button in the corresponding position
                    // buttons[r][c] = button;
                    // Log the move into the BST
                    actionLogTree.insertMove(r, c, getCurrentTurn()); // Use row and col as part of the key
                });
                grid.add(button, column, row);
                buttons[row][column] = button;


            }
        }
        // Creating a scene object that will display the game user;'s interface
        Scene scene = new Scene(borderPane, 660, 550);


        // Sets the title of the game window
        primayStage.setTitle("Tic -  Tac - Toe");


        // Set the scene of the primary stage to the one containing the buttons
        primayStage.setScene(scene);


        // Show the game window
        primayStage.show();


    } // end start();

    /**
     * System prints out all the moves that happened in the game
     */
    public void displayMoves() {
        System.out.println("Moves in order:");
        actionLogTree.inOrder();
    }

    /**
     * Starts a separate thread that tracks and prints the current time every second.
     * <p>
     * This method creates a new thread that runs in an infinite loop. The thread performs the following:
     * - If the `isplayed` flag is false, it skips the current iteration and waits for one second before checking again.
     * - If the `startTime` is not set (i.e., it's null), it assigns the current date and time to `startTime`.
     * - It continuously prints the current date and time every second.
     * <p>
     * The thread runs until the program is terminated. Interrupted exceptions are caught and ignored to allow the thread to continue running.
     */
    public void InitialTime() {

        Thread timmerThread = new Thread(() -> {
            while (true) {

                try {
                    TimeUnit.SECONDS.sleep(1);
                    if (!isplayed) {
                        continue;

                    }
                    if (startTime == null) {
                        startTime = new Date();
                    }
                    System.out.println(new Date());
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        });

        timmerThread.setDaemon(true);
        timmerThread.start();
    }


    /**
     * Method for restarting the game
     */
    public void restart() {
        // iterating through each row and column
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");  // Clear the text on each button
                buttons[row][col].setDisable(false); // Re-enable the button
            }
        }
        x = true;
        // Calling the updateHeader method
        updateHeader();
    }


    /**
     * Method for knowing whose turn it is
     *
     * @return
     */
    public String getCurrentTurn() {
        return x ? "X" : "O";
    }


    /**
     * This method switches the current player's turn.
     * The variable 'x' represents whether it's X's turn.
     * If 'x' is true, it's X's turn, and if false, it's O's turn.
     * The method toggles the value of 'x', so the turn is switched
     * between X and O after each call.
     */
    public void SwitchTurn() {
        x = !x; // toggle  the value of "x"
    }


    /**
     * Method for disabling the buttons on the game board
     */
    public void disableBoard() {
        isplayed = false;
        long totalTime = (new Date().getTime() - startTime.getTime()) / 1000;
        System.out.println("totalTime:" + totalTime + "s");
        startTime = null;

        // Iterate through each row of the 3x3 grid
        for (int row = 0; row < 3; row++) {
            // For each row, iterate through each column
            for (int col = 0; col < 3; col++) {
                // Disable the button at the current row and column which makes it
                // unclickable
                buttons[row][col].setDisable(true);

            }
        }
    }

    /**
     * Method for checking who won
     */
    public void Check() {
        int count = 0;
        if (!isplayed) {
            isplayed = true;

        }
        //rows
        // iterates over each row
        for (int row = 0; row < 3; row++) {
            // If the first button of the row is empty, it skips the check for that row using
            // continue
            if (buttons[row][0].getText().equals("")) {
                continue;
            }
            // Checks if all the buttons in the row have the same value
            if (buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][0].getText().equals(buttons[row][2].getText())) {
                // If they do, then the board is disabled
                disableBoard();
                // The current player's win count is incremented
                if (getCurrentTurn().equals("X")) {
                    Xwins++;

                    ServerDetail.sendWinToServer("X won");
                } else {
                    Owins++;
                    ServerDetail.sendWinToServer("O won");
                }
                // prints the result
                System.out.println(getCurrentTurn() + " wins");
                isplayed = false;
                return;

            }
        }

        //columns
        // iterates over each column
        for (int col = 0; col < 3; col++) {
            // skips the check if the first button in the column is empty
            if (buttons[0][col].getText().equals("")) {
                continue;
            }
            // Checks if all buttons in that column have the same text. If true, it declares a win
            if (buttons[0][col].getText().equals(buttons[1][col].getText()) && buttons[0][col].getText().equals(buttons[2][col].getText())) {
                // Disables the board
                disableBoard();
                if (getCurrentTurn().equals("X")) {
                    Xwins++;
                    ServerDetail.sendWinToServer("X won");
                } else {
                    Owins++;
                    ServerDetail.sendWinToServer("O won");
                }
                // Prints the winner
                System.out.println(getCurrentTurn() + " wins");
                isplayed = false;
                return;

            }
        }

        //diagonal 1 ( top left to bottom right)
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText()) && !(buttons[0][0].getText().equals(""))) {

            // Disables the board
            disableBoard();
            if (getCurrentTurn().equals("X")) {
                // Updates the win count
                Xwins++;
                ServerDetail.sendWinToServer("X won");
            } else {
                Owins++;
                ServerDetail.sendWinToServer("O won");
            }
            // Prints the winner
            System.out.println(getCurrentTurn() + " wins");
            isplayed = false;
            return;
        }

        //diagonal 2 ( top right to bottom left)
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText()) && !(buttons[0][2].getText().equals(""))) {
            // Disables the board
            disableBoard();
            if (getCurrentTurn().equals("X")) {
                Xwins++;
                ServerDetail.sendWinToServer("X won");
            } else {
                Owins++;
                ServerDetail.sendWinToServer("O won");
            }
            // Prints the winner
            System.out.println(getCurrentTurn() + " wins");
            isplayed = false;
            return;
        }

        //tie
        // Loops through the entire gameboard
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(buttons[i][j].getText().equals(""))) {
                    count++;


                }
            }
        }
        // if all buttons are filled, it means no empty spots remain
        if (count == 9) {
            System.out.println("tie");
            isplayed = false;
            disableBoard();
        }
    }


}
