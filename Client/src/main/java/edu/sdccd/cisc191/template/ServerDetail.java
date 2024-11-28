package edu.sdccd.cisc191.template;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.stream.Collectors;


public class ServerDetail {
    static final String SERVER_ADDRESS = "localhost";
    static final int SERVER_PORT = 5678;

    public static void sendWinToServer(String playerWon) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            ServerRequest request = new ServerRequest("UPDATE_WIN", playerWon);
            outputStream.writeObject(request);
            outputStream.flush();

            System.out.println(inputStream);

        } catch (IOException e) {
            System.out.println("Error, could not connect to local server!");
            showServerErrorMessage();
        }
    }
    private static void showServerErrorMessage() {
        Stage errorStage = new Stage();
        errorStage.setTitle("Server Error");

        Label label = new Label("Could not find server. Is the server currently running?");
        label.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");

        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 500, 100);
        errorStage.setScene(scene);

        errorStage.show();
    }

    public static void displayActionLog() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Send a request to get the win log
            ServerRequest update = new ServerRequest("GET_WIN_LOG", null);
            outputStream.writeObject(update);
            outputStream.flush();

            // Receive the win log from the server
            // Linked list
            LinkedList<String> winLog = (LinkedList<String>) inputStream.readObject();

            System.out.println("Action Log:");

            // Stream api
            String sb = winLog.stream().collect(Collectors.joining("\n"));

//            StringBuilder sb = new StringBuilder();
//            for (String action : winLog) {
//                sb.append(action).append("\n");
//            }

            // open a new window
            // Creating a scene object that will display the game user;'s interface
            Stage logStage = new Stage();
            logStage.setTitle("Action Log");


            Label logLabel = new Label(sb);
            System.out.println(sb);
            ScrollPane scrollPane = new ScrollPane(logLabel);
            scrollPane.setFitToWidth(true);


            Scene scene = new Scene(scrollPane, 400, 300);
            logStage.setScene(scene);


            logStage.show();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error getting win log from server: " + e.getMessage());
        }


        StringBuilder sb = new StringBuilder();
        // loop / recurse through the LinkedList and append the nodes to the string builder
        // set label text to the sb.toString()
    }


}
