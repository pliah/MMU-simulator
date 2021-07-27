package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.service.CacheUnitController;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

/**
 * HandleRequest class stands for handling the reading from clienr side, sending to controller's treat,
 * and sending the result back to the client for the request it's get
 * @param <T>
 */
public class HandleRequest<T> extends java.lang.Object implements java.lang.Runnable {
    public final static String STATISTICS = "getStatistics";
    public final static String UPDATE = "UPDATE";
    public final static String GET = "GET";
    public final static String DELETE = "DELETE";
    private Socket socket;
    private CacheUnitController<T> controller;
    private Request<DataModel<T>[]> request = null;
    private  DataOutputStream output = null;
    private  DataInputStream input = null;
    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.socket = s;
        this.controller = controller;
    }

    /**
     * run function call the getRequest function to start the flow.
     */
    @Override
    public void run() {
        getRequest();
    }

    /**
     * getRequest function reads the request from the socket and sends it to the right handler
     */
    private void getRequest(){
        DataInputStream input = null;
        StringBuilder sb = new StringBuilder();
        String content = "";
        try {
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            do {
                content = input.readUTF();
                sb.append(content);
            } while (input.available() != 0);
            content = sb.toString();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if (content.equals(STATISTICS)) {
            statisticsHandler();
            return;
        } else {
            Type ref = new TypeToken<Request<DataModel<T>[]>>() {
            }.getType();
            this.request = (new Gson().fromJson((String) content, ref));
            headerTypeHandler();
        }
    }

    /**
     * headerTypeHandler function recognizes the wether the request is UPDATE, GET or DELETE
     * and sends to the right function in the controller
     */
    private void headerTypeHandler(){
        Gson gson=new Gson();
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
            Map<String, String> headerType = request.getHeaders();
            switch (headerType.get("action")) {
                case (UPDATE):
                    boolean handleUpdate = controller.update(request.getBody());
                    output.writeUTF(gson.toJson(handleUpdate));
                    output.flush();
                    break;
                case (GET):
                    DataModel<T>[] handleGet = controller.get(request.getBody());
                    output.writeUTF(gson.toJson(handleGet));
                    output.flush();
                    break;
                case (DELETE):
                    boolean handleDelete = controller.delete(request.getBody());
                    output.writeUTF(gson.toJson(handleDelete));
                    output.flush();
                    break;
                default:
                    System.out.println("wrong action");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * statisticsHandler function call the getStatistic controller's function
     * and write the result back to the client
     */
    private void statisticsHandler(){
        String statistics=controller.getStatistic();
        try {
            this.output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(statistics);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

