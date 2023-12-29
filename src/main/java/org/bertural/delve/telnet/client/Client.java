package org.bertural.delve.telnet.client;

import org.bertural.delve.data.entities.EntityUser;
import org.bertural.delve.state.StateMachine;
import org.bertural.delve.telnet.TelnetServer;
import org.bertural.delve.telnet.client.state.InitialClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {
    private final TelnetServer server;
    private final Socket socket;
    private boolean isRunning;
    private PrintWriter writer;
    private BufferedReader reader;
    private EntityUser user;
    private StateMachine stateMachine;

    public Client(Socket socket, TelnetServer server) {
        this.socket = socket;
        this.server = server;
        this.stateMachine = new StateMachine();
        try {
            this.writer = new PrintWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.isRunning = true;
        } catch (IOException e) {
            isRunning = false;
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        if (isRunning) {
            stateMachine.setCurrent(new InitialClientState(this));
            while (isRunning) {
                try {
                    stateMachine.update();
                } catch (Exception e) {
                    isRunning = false;
                }
            }
        }
        shutdown();
    }

    public String readLine() {
        String response = null;
        try {
            response = reader.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return response;
    }


    public void stopRunning() {
        this.isRunning = false;
    }

    private void shutdown() {
        server.clientLogout(this);
        this.closeClosable(reader);
        this.closeClosable(writer);
        this.closeClosable(socket);
    }

    private void closeClosable(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public EntityUser getUser() { return user; }
    public void setUser(EntityUser user) { this.user = user; }

    public PrintWriter getWriter() {
        return writer;
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public TelnetServer getServer() { return server; }
}
