package edu.sdccd.cisc191.template;


import java.io.Serializable;


/**
 * The ServerRequest class represents a request sent to a server.
 * It implements Serializable, allowing it to be converted into a byte stream
 * for transmission or storage.
 */
public class ServerRequest implements Serializable{
    // The type of request being sent
    private String requestType;
    // The message or content of the request.
    private String message;

    /**
     * Constructor for the ServerRequest class.
     *
     * @param requestType The type of the request (e.g., "GET", "POST").
     * @param message The message or content of the request.
     */
    public ServerRequest(String requestType, String message)
    {
        this.requestType = requestType;// Initialize the request type.
        this.message = message;        // Initialize the message.
    }

    /**
     * Gets the type of the request.
     *
     * @return A string representing the request type.
     */
    public String getRequestType()
    {
        return requestType;
    }

    /**
     * Gets the message or content of the request.
     *
     * @return A string representing the message.
     */
    public String getMessage()
    {
        return message;
    }
}