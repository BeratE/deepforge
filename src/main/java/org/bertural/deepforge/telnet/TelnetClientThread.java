package org.bertural.deepforge.telnet;

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

    private String username;
    private List<PrintWriter> allUserWriters;


    public TelnetClientThread(Socket socket, List<PrintWriter> allUserWriters) {
        this.socket = socket;
        this.allUserWriters = allUserWriters;
        try {
            this.writer = new PrintWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.allUserWriters.add(this.writer);

            this.running = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        if (running) {
            running = connect();
            broadcast("Joined the chat room.");
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
        username = readInput();
        return true;
    }

    private void shutdown() {
        System.out.println("Shutdown connection");
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
        final String line = username + ": " + message;
        for (PrintWriter w : allUserWriters) {
            if (!w.equals(writer)) {
                w.println(line);
                w.flush();
            }
        }
    }
}
