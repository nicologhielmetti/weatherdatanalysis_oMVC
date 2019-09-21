package ViewNormal;

import State.WebAppState;

import java.beans.PropertyChangeListener;

public interface WebAppStateChange {
    void onWebAppStateChange(WebAppState oldState, WebAppState newState, String actionId, Long requestId);
}
