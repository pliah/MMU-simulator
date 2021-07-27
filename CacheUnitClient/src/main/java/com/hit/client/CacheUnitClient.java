package com.hit.client;

import java.io.*;
import java.net.Socket;

/**
 * CacheUnitClient class stands for communicating with the server.
 */
public class CacheUnitClient {
    private Socket myServer;
    private StringBuilder sb;
    private DataOutputStream output;
    private DataInputStream input;
    public final static String HOST = "127.0.0.1";
    public final static String STATISTICS = "getStatistics";
    public final static Integer PORT = 12345;
    public CacheUnitClient() {}

    /**
     * Send function checks the request's type and sends it to the matching handler
     * @param request the request to the server
     * @return the message that the server sends back after taking care of the request
     */
    public java.lang.String send(java.lang.String request) {
        String massage =null;
        switch (request) {
            case (STATISTICS):
                massage = handleStatistics();
                break;
            default:
                try {
                    massage = handleRequest(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return massage;
    }

    /**
     * handleStatistics function takes care of sending the
     * statistics request to the server side and reading its answer
     * @return the response that it reads from the server
     */
    private String handleStatistics() {
        String response=null;
        try {
            myServer = new Socket(HOST, PORT);
            output = new DataOutputStream(myServer.getOutputStream());
            input = new DataInputStream(myServer.getInputStream());
            output.writeUTF(STATISTICS);  //write to server
            output.flush();
            response = input.readUTF(); //the respond - read the server input
            output.close();
            input.close();
            myServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  response;
    }

    /**
     * handleRequest function takes care of sending the DELETE, GET
     *  and UPDATE requests to the server side and reading its answer
     * @return the response that it reads from the server
     */
    private String handleRequest(String request) throws IOException {
        String response;
        response = "";
        try {
            myServer = new Socket(HOST, PORT);
            output = new DataOutputStream(myServer.getOutputStream());
            input = new DataInputStream(myServer.getInputStream());
            output.writeUTF(request);
            output.flush();
            response = input.readUTF();
            output.close();
            input.close();
            myServer.close();
        } catch (IOException e) {
           e.printStackTrace(); }
        return response;
    }


}
