package org.bertural.deepforge.telnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TelnetServer {

    private static Logger logger = LoggerFactory.getLogger(TelnetServer.class);

    private final int socket;
    private ServerSocket serverSocket = null;
    private List<PrintWriter> allUserWriters = new ArrayList<>();

    public TelnetServer() {
        this(6969);
    }

    public TelnetServer(int socket) {
        this.socket = socket;
        try {
            serverSocket = new ServerSocket(socket);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void run() {
        logger.info("Starting telnet server..");
        while(true) {
            try {
                Socket newSocket = serverSocket.accept();
                TelnetClientThread client = new TelnetClientThread(newSocket, allUserWriters);
                Thread t = new Thread(client);
                t.start();
            } catch (Exception e) {
                logger.error(e.getMessage());
                break;
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void broadcast(String message) {
        final String line = "SERVER: " + message;
        for (PrintWriter w : allUserWriters) {
            w.println(line);
        }
    }
}
