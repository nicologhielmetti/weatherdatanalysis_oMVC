package ViewNormal;

import State.WebAppState;
import Stores.Store;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WebAppStateChangeObserver implements PropertyChangeListener{
    private Long requestId;
    private String actionId;
    private WebAppStateChange webAppStateChange;

    WebAppStateChangeObserver(Long requestId, String actionId, WebAppStateChange webAppStateChange){
        Store.getInstance().observeState(this);
        this.requestId = requestId;
        this.actionId = actionId;
        this.webAppStateChange = webAppStateChange;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        webAppStateChange.onWebAppStateChange((WebAppState) propertyChangeEvent.getOldValue(),
                (WebAppState) propertyChangeEvent.getNewValue(), this.actionId, this.requestId);
    }

}
