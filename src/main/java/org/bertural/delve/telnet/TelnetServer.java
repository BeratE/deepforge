package org.bertural.delve.telnet;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TelnetServer extends Server {

    private List<ClientThread> loggedClients = new ArrayList<>();

    public TelnetServer(int port) {
        super(port);
    }

    @Override
    protected void doLoop(long iteration) throws Exception {
        // Listen for new connections and accept
        Socket socket = serverSocket.accept();
        logger.info("New connection on " + socket.toString());
        ClientThread client = new ClientThread(socket, this);
        client.start();
    }


    synchronized public void clientLogin(ClientThread client) {
        if (client.getUser() != null) {
            broadcast(client.getUser().getLogin() + " joined the chatroom.");
            client.getWriter().println("Welcome " + client.getUser().getLogin() + "!");
            client.getWriter().flush();
            loggedClients.add(client);
        }
    }

    synchronized public void clientLogout(ClientThread client) {
        if (client.getUser() != null) {
            broadcast(client.getUser().getLogin() + " left the chatroom.");
            loggedClients.remove(client);
        }
    }

    synchronized public void broadcast(ClientThread client, String message) {
        final String line = client.getUser().getLogin() + ": " + message;
        for (ClientThread clientThread : loggedClients) {
            PrintWriter w = clientThread.getWriter();
            if (!w.equals(client.getWriter())) {
                w.println(line);
                w.flush();
            }
        }
    }

    synchronized private void broadcast(String message) {
        final String line = "SERVER: " + message;
        for (ClientThread client : loggedClients) {
            PrintWriter w = client.getWriter();
            w.println(line);
            w.flush();
        }
    }
}
