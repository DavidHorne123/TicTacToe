package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.ServerRequest;

import java.io.*;
import java.net.*;
import java.util.LinkedList;


public class TicTacToeServer {

    // Constant defining the port number on which the server listens
    private static final int PORT = 5678;
    // Boolean flag to indicate if the server is running
    private static boolean isServerRunning = true;
    // Global linked list to maintain a log of messages or updates
    public static LinkedList<String> globalLinkedList = new LinkedList<String>();

    /**
     * The main method serves as the server's entry point.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main (String[] args)
    {
        // Create a server socket and listen on the defined port
        try(ServerSocket serverSocket = new ServerSocket(PORT))
        {
            // Continuously accept client connections while the server is running
            while(isServerRunning)
            {
                //listens for client
                Socket socket = serverSocket.accept();
                // Handle the client's request in a separate method
                handleClient(socket);
            }
        } catch (IOException e)
        {
            // Handle any exceptions that occur while setting up or running the server
            System.out.println("Server exception: " + e);
        }
    }

    /**
     * Handles communication with a connected client.
     *
     * @param inSocket The socket representing the client connection.
     */
    public static void handleClient(Socket inSocket) {
        // Create input and output streams for communication with the client
        try (ObjectInputStream inputStream = new ObjectInputStream(inSocket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(inSocket.getOutputStream())) {

            // Read the request object sent by the client
            ServerRequest update = (ServerRequest) inputStream.readObject();

            // Process the request based on its type
            switch (update.getRequestType())
            {
                case "UPDATE_WIN": // Update the win log
                    globalLinkedList.add(update.getMessage());
                    outputStream.writeObject("Win log updated");
                    break;

                case "GET_WIN_LOG": // Send the win log to the client
                    outputStream.writeObject(globalLinkedList);
                    break;

                default: // Handle unknown request types
                    outputStream.writeObject("Unknown request type");
                    outputStream.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream");
            }
        }
    }
} //end class Server
