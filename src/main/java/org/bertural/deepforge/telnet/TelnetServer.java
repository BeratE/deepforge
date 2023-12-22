package org.bertural.deepforge.telnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TelnetServer extends Server {

    public TelnetServer() {
        this(6969);
    }

    public TelnetServer(int socket) {
        super(socket);

    }

    @Override
    protected void doLoop(long iteration) throws Exception {
        Socket newSocket = serverSocket.accept();
        ClientThread client = new ClientThread(newSocket, allUserWriters);
        Thread t = new Thread(client);
        t.start();
    }
}
