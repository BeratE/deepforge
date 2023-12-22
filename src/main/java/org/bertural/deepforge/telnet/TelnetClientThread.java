package org.bertural.deepforge.telnet;

import org.bertural.deepforge.data.Authentication;
import org.bertural.deepforge.data.entities.EntityUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class TelnetClientThread implements Runnable {
    private final Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private boolean running = false;

    private List<PrintWriter> allUserWriters;

    private EntityUser user = null;


    public TelnetClientThread(Socket socket, List<PrintWriter> allUserWriters) {
        this.socket = socket;
        this.allUserWriters = allUserWriters;
        try {
            this.writer = new PrintWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.running = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        if (connect()) {
            writer.println("Welcome " + user.getLogin() + "!");
            writer.flush();
            broadcast("Joined the chat room!");
            while (running) {
                String message = readInput();
                if (message == null) {
                    running = false;
                } else {
                    this.broadcast(message);
                }
            }
        }
        shutdown();
    }

    private boolean connect() {
        writer.print("Enter username: ");
        writer.flush();
        String username = readInput();
        writer.print("Enter password: ");
        writer.flush();
        String password = readInput();

        user = Authentication.login(username, password);
        if (user == null) {
            writer.println("Invalid login!");
            writer.flush();
            return false;
        }

        allUserWriters.add(writer);
        return true;
    }

    /** IO Management */

    private String readInput() {
        String response = null;
        try {
            response = reader.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return  response;
    }

    private void broadcast(String message) {
        final String line = user.getLogin() + ": " + message;
        for (PrintWriter w : allUserWriters) {
            if (!w.equals(writer)) {
                w.println(line);
                w.flush();
            }
        }
    }

    /** Shutdown / Resource Management */

    private void shutdown() {
        System.out.println("Shutdown connection..");
        allUserWriters.remove(writer);
        this.closeClosable(reader);
        this.closeClosable(writer);
        this.closeClosable(socket);
    }

    /** Close an AutoClosable resource */
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
