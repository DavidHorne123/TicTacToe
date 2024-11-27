package edu.sdccd.cisc191.template;


import java.io.Serializable;



public class ServerRequest implements Serializable{
    private String requestType;
    private String message;

    public ServerRequest(String requestType, String message)
    {
        this.requestType = requestType;
        this.message = message;
    }

    public String getRequestType()
    {
        return requestType;
    }

    public String getMessage()
    {
        return message;
    }
}