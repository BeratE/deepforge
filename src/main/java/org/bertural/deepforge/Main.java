package org.bertural.deepforge;

import org.bertural.deepforge.telnet.TelnetServer;

public class Main {
    public static void main(String[] args) {
        Database.getInstance().configure();
        TelnetServer server = new TelnetServer();
        server.run();
    }
}
