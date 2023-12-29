package org.bertural.delve.telnet.client.state;

import org.bertural.delve.data.Banner;
import org.bertural.delve.telnet.EscapeCode;
import org.bertural.delve.telnet.client.Client;

import java.util.Locale;

public class InitialClientState extends BaseClientState {

    public InitialClientState(Client client) {
        super(client);
    }

    @Override
    public void onEnter() {
        // Show Initial print screen
        EscapeCode.CLEAR_SCREEN.print(getWriter());
        EscapeCode.CURSOR_RESET.print(getWriter());
        Banner.WELCOME.print(getWriter());
        // Wait for any user input
        EscapeCode.CURSOR_POS_PUSH.print(getWriter());
        client.readLine();
        EscapeCode.CURSOR_POS_POP.print(getWriter());
        EscapeCode.CLEAR_LINE.print(getWriter());
    }

    @Override
    public void onExit() {
        EscapeCode.CURSOR_POS_POP.print(getWriter());
        EscapeCode.CLEAR_LINE.print(getWriter());
    }

    @Override
    public void onUpdate() {
        EscapeCode.CURSOR_POS_PUSH.print(getWriter());
        client.getWriter().println("Type Login, Register or Quit ..");
        client.getWriter().flush();
        String command = client.readLine().toLowerCase(Locale.ROOT);
        switch (command) {
            case "register":
                client.getStateMachine().setCurrent(new RegisterClientState(client));
                break;
            case "login":
                client.getStateMachine().setCurrent(new LoginClientState(client));
                break;
            case "quit":
                client.stopRunning();
                break;
            default:
                break;
        }
        EscapeCode.CURSOR_POS_POP.print(getWriter());
        EscapeCode.CLEAR_SCREEN_END.print(getWriter());
    }
}
