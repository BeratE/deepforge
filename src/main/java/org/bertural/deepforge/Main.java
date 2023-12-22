package org.bertural.deepforge;

import org.bertural.deepforge.data.Authentication;
import org.bertural.deepforge.data.Database;
import org.bertural.deepforge.telnet.TelnetServer;

public class Main {
    public static void main(String[] args) {
        Database.getInstance().configure();
        Authentication.register("Berat", "123456");
        TelnetServer server = new TelnetServer();
        server.run();
    }
}
