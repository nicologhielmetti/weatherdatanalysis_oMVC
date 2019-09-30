package State;

import Actions.Action;
import ViewNormal.WebAppStateChange;

import javax.servlet.ServletException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

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

    public void setState(WebAppState webAppState, Action action, Long requestIdentifier){
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, action.getActionIdentifier(), this.archState.getWebAppState(), webAppState));
        this.archState.setWebAppState(webAppState);
    }

    public void addChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }






}
