package org.bertural.delve.state;

public class StateMachine {
    protected State currentState;
    protected State previousState;

    public StateMachine() {
        this.currentState = new EmptyState();
        this.previousState = new EmptyState();
    }

    public StateMachine(State currentState) {
        this.currentState = currentState;
        this.previousState = new EmptyState();
    }


    public void update() {
        currentState.onUpdate();
    }

    public final State getCurrent() {
        return currentState;
    }

    public final State getPrevious() {
        return previousState;
    }

    public final void setCurrent(State state) {
        if (currentState != null)
            currentState.onExit();
        previousState = currentState;
        currentState = state;
        if (currentState != null)
            currentState.onEnter();
    }

    public void revert() {
        State tmp = currentState;
        if (currentState != null)
            currentState.onExit();
        currentState = previousState;
        if (currentState != null)
            currentState.onEnter();
        previousState = tmp;
    }
}
