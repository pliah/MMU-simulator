package com.hit.server;

import com.hit.service.CacheUnitController;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 *  Server class is a listener to the CLI and open/close a socket due to the observed value.
 *  after this, for each request from the socket the server opens a new thread to deal with it
 */
public class Server extends java.lang.Object implements java.beans.PropertyChangeListener, java.lang.Runnable {
    private static boolean serverUp = false;
    private  CacheUnitController<String> controller=null;
    private ServerSocket server = null;
    static ExecutorService executor =  Executors.newCachedThreadPool();
    public Server() throws IOException {
        this.server = new ServerSocket(12345);
        this.controller=new CacheUnitController<String>();
    }

    /**
     * run function accepts the requests and creates the threads to treat them
     */
    @Override
    public void run() {
        try {

            while (serverUp) {
                Socket someClient = server.accept();
                executor.execute(new HandleRequest<String>(someClient, this.controller));
            }
            System.out.println("Server close");
        } catch (Exception e) {
            System.out.println("tired of waiting for connection");
        }
    }

    /**
     *shutdown function calls the controller's shutdown function
     * and closes the executor
     */
    private void shutdown() {
        controller.shutdown();
        executor.shutdown();

        try {
            executor.awaitTermination(1000, TimeUnit.MICROSECONDS);
            server.close();
        } catch (InterruptedException|IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * propertyChange function gets the event from CLI and starts/closes the server due to its value
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.serverUp = (boolean) evt.getNewValue();
        if (serverUp==true) {
            Executors.newSingleThreadExecutor().execute(this);
        }
        else {
            shutdown();
        }
    }
}






