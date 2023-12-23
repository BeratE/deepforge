package org.bertural.deepforge.telnet;

import java.net.Socket;

public class TelnetServer extends Server {

    public TelnetServer() {
        this(6969);
    }

    public TelnetServer(int port) {
        super(port);

    }

    @Override
    protected void doLoop(long iteration) throws Exception {
        Socket newSocket = serverSocket.accept();
        ClientThread client = new ClientThread(newSocket, allUserWriters);
        Thread t = new Thread(client);
        t.start();
    }
}
