package com.hit.server;

import com.hit.util.CLI;

import java.io.IOException;

public class CacheUnitDriver {
    public static void main(String[] args) throws IOException {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();
    }
}
