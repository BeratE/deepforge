package org.bertural.deepforge.telnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class Server extends Thread {
    protected static Logger logger = LoggerFactory.getLogger(Server.class);

    protected final int port;
    protected ServerSocket serverSocket = null;

    protected boolean isRunning = false;
    protected long iteration = 0;

    public Server() {
        this(6969);
    }

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract void doLoop(long iteration) throws Exception;

    @Override
    public void run() {
        startup();
        while(isRunning) {
            try {
                doLoop(iteration++);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        shutdown();
    }

    private void startup() {
        logger.info("Starting server on " + getName() + "..");
    }

    private void shutdown() {
        logger.info("Shutdown server on " + getName() + "..");
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
