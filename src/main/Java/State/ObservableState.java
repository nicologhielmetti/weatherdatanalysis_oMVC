package State;

import Actions.Action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableState {
    private PropertyChangeSupport propertyChangeSupport;

    private ArchState archState;

    public ObservableState(ArchState archState){
        this.archState = archState;
        propertyChangeSupport = new PropertyChangeSupport(archState);
    }

    public WebAppState getWebAppState() {
        return this.archState.getWebAppState();
    }

    public void setState(ArchState archState, Action action, Long requestIdentifier){
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, action.getActionIdentifier(), this.archState, archState));
        this.archState = archState;
    }

    public void addChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }






}
