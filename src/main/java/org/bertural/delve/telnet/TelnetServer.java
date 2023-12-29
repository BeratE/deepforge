package org.bertural.delve.telnet;

import org.bertural.delve.telnet.client.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TelnetServer extends Server {

    private List<Client> loggedClients = new ArrayList<>();

    public TelnetServer(int port) {
        super(port);
    }

    @Override
    protected void doLoop(long iteration) throws Exception {
        // Listen for new connections and accept
        Socket socket = serverSocket.accept();
        logger.info("New connection on " + socket.toString());
        Client client = new Client(socket, this);
        client.start();
    }


    synchronized public void clientLogin(Client client) {
        if (client.getUser() != null) {
            broadcast(client.getUser().getLogin() + " joined the chatroom.");
            client.getWriter().println("Welcome " + client.getUser().getLogin() + "!");
            client.getWriter().flush();
            loggedClients.add(client);
        }
    }

    synchronized public void clientLogout(Client client) {
        if (client.getUser() != null) {
            broadcast(client.getUser().getLogin() + " left the chatroom.");
            loggedClients.remove(client);
        }
    }

    synchronized public void broadcast(Client client, String message) {
        final String line = client.getUser().getLogin() + ": " + message;
        for (Client clientThread : loggedClients) {
            PrintWriter w = clientThread.getWriter();
            if (!w.equals(client.getWriter())) {
                w.println(line);
                w.flush();
            }
        }
    }

    synchronized private void broadcast(String message) {
        final String line = "SERVER: " + message;
        for (Client client : loggedClients) {
            PrintWriter w = client.getWriter();
            w.println(line);
            w.flush();
        }
    }
}
