package State;

import Actions.Action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableState {
    private PropertyChangeSupport propertyChangeSupport;

    private State state;

    public ObservableState(State state){
        this.state = state;
        propertyChangeSupport = new PropertyChangeSupport(state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state, Action action){
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, action.getActionIdentifier(), this.state, state));
        this.state = state;
    }

    public void addChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }






}
