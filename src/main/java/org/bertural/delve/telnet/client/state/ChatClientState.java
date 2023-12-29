package org.bertural.delve.telnet.client.state;

import org.bertural.delve.telnet.client.Client;

public class ChatClientState extends BaseClientState {

    public ChatClientState(Client client) {
        super(client);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onUpdate() {
        String msg = client.readLine();
        client.getServer().broadcast(client, msg);
    }
}
