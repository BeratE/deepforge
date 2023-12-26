package org.bertural.delve.telnet;

import org.bertural.delve.data.Authentication;
import org.bertural.delve.data.Banner;
import org.bertural.delve.data.entities.EntityUser;
import org.bertural.delve.state.StateMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private final TelnetServer server;
    private final Socket socket;
    private boolean isRunning;
    private StateMachine state;
    private PrintWriter writer;
    private BufferedReader reader;
    private EntityUser user;

    public ClientThread(Socket socket, TelnetServer server) {
        this.socket = socket;
        this.server = server;
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
        if (!isRunning)
            return;

        EscapeCode.CLEAR_SCREEN.print(writer);
        EscapeCode.CURSOR_RESET.print(writer);
        Banner.WELCOME.print(writer);

        if (connect()) {
            while (isRunning) {
                String message = readLine();
                if (message == null) {
                    isRunning = false;
                } else {
                    server.broadcast(this, message);
                }
            }
        }
        shutdown();
    }

    private boolean connect() {
        writer.print("Enter username: ");
        writer.flush();
        String username = readLine();
        writer.print("Enter password: ");
        writer.flush();
        String password = readLine();

        user = Authentication.login(username, password);
        if (user == null) {
            writer.println("Invalid login!");
            writer.flush();
            return false;
        }

        server.clientLogin(this);
        return true;
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

    public EntityUser getUser() {
        return user;
    }
    
    public PrintWriter getWriter() {
        return writer;
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
}
