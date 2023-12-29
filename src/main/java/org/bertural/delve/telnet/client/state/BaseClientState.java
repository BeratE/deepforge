package org.bertural.delve.telnet.client.state;

import org.bertural.delve.state.State;
import org.bertural.delve.telnet.client.Client;

import java.io.PrintWriter;

public abstract class BaseClientState implements State {
    protected Client client;

    public BaseClientState(Client client) {
        this.client = client;
    }

    protected PrintWriter getWriter() {
        return client.getWriter();
    }
}
