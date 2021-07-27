package com.hit.util;

import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * CLI class mannages the communication between the user and the server
 */
public class CLI  implements java.lang.Runnable {
    private PropertyChangeSupport support;
    private java.io.InputStream in;
    private java.io.OutputStream out;
    private OutputStreamWriter writer;
    public static final java.lang.String START="Start";
    public static final java.lang.String SHUTDOWN="Shutdown";
    private String command=null;
    private boolean serverUp=false;

    public CLI(java.io.InputStream in, java.io.OutputStream out){
        this.in=in;
        this.out= out;
        support = new PropertyChangeSupport(this);
    }

    /**
     * addPropertyChangeListener function adds a listener (server) to the CLI
     * @param pcl
     */
    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }
    /**
     * addPropertyChangeListener function removes a listener (server) to the CLI
     * @param pcl
     */
    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl){
        support.removePropertyChangeListener(pcl);
    }

    /**
     * write function used to write messages to the user
     * @param string
     */
    private void write(java.lang.String string){
        this.writer = new OutputStreamWriter(this.out);
        try {
            writer.write(string + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  run function runs until the user write the command SHUTDOWN and satart with the command START.
     *  in both cases it changes the serverUp observed flag.
     */
    @Override
    public void run() {
        while (true){
            boolean oldServerUp=this.serverUp;
            readCommandFromCommandLine();
            if (command.equals(START)){
                this.serverUp=true;
                support.firePropertyChange("serverUp", oldServerUp, true);

            }
            else if(command.equals(SHUTDOWN)){
                this.serverUp=false;
                support.firePropertyChange("serverUp", oldServerUp, false);
                shutdown(this.writer);
                break;
            }
            else{
                write("Not a valid command\n");
            }
        }

    }

    /**
     * readCommandFromCommandLine function reads the user commands from command kine
     */
    private void readCommandFromCommandLine(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        write("Please enter your command\n");

        String newCommand = null;
        try {
            newCommand  = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.command=newCommand;
    }

    /**
     * shutdown function closes the CLI writer
     * @param writer
     */
    private void shutdown(OutputStreamWriter writer){
        try {
            write("Shutdown cli");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        write("Shutdown server\n");
    }
}
