package org.bertural.delve.state;

public interface State {
    void onExit();
    void onEnter();
    void onUpdate();
}
