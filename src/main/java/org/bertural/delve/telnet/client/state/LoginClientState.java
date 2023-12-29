package org.bertural.delve.telnet.client.state;

import org.bertural.delve.data.Authentication;
import org.bertural.delve.data.entities.EntityUser;
import org.bertural.delve.telnet.EscapeCode;
import org.bertural.delve.telnet.client.Client;

public class LoginClientState extends BaseClientState {

    public LoginClientState(Client client) {
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
        boolean success = connect(); // blocking
        if (success) {
            client.getStateMachine().setCurrent(new ChatClientState(client));
        } else {
            client.getStateMachine().revert();
        }
    }

    private boolean connect() {
        client.getWriter().print("Enter username: ");
        client.getWriter().flush();
        String username = client.readLine();
        client.getWriter().print("Enter password: ");
        client.getWriter().flush();
        EscapeCode.FONT_HIDDEN_SET.print(client.getWriter());
        String password = client.readLine();
        EscapeCode.FONT_HIDDEN_RES.print(client.getWriter());

        EntityUser user = Authentication.login(username, password);
        if (user == null) {
            client.getWriter().println("Invalid login!");
            client.getWriter().flush();
            return false;
        }
        client.setUser(user);

        client.getServer().clientLogin(client);
        return true;
    }
}
