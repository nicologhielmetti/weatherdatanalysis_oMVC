package State;

import Actions.Action;

import java.beans.PropertyChangeSupport;

public class ObservableState {
    private PropertyChangeSupport propertyChangeSupport;

    private State state;

    public ObservableState(){
        state = getState();
        propertyChangeSupport = new PropertyChangeSupport(state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state, Action action){

    }


}
