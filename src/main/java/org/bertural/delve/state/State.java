package org.bertural.delve.state;

public interface State {
    void onEnter();
    void onExit();
    void onUpdate() throws Exception;
}
