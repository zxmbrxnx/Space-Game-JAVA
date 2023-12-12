package states;

import java.awt.Graphics;

public abstract class State {

    private static State currentState = null;

    public static State getCurrentState() { return currentState; }
    public static void changeState(State state){
        currentState = state;
    }

    public abstract void update();
    public abstract void draw(Graphics g);
}
