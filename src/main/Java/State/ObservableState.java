package State;

import Actions.Action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableState {
    private PropertyChangeSupport propertyChangeSupport;

    private WebAppState webAppState;

    public ObservableState(WebAppState webAppState){
        this.webAppState = webAppState;
        propertyChangeSupport = new PropertyChangeSupport(webAppState);
    }

    public WebAppState getWebAppState() {
        return webAppState;
    }

    public void setState(WebAppState webAppState, Action action){
        propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, action.getActionIdentifier(), this.webAppState, webAppState));
        this.webAppState = webAppState;
    }

    public void addChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }






}
