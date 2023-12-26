package org.bertural.delve.state;

/** Do nothing */
public class EmptyState implements State {
    public EmptyState() {
    }
    @Override
    public void onExit() {
    }
    @Override
    public void onEnter() {
    }
    @Override
    public void onUpdate() {
    }
}