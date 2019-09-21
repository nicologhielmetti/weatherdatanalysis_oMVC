package ViewNormal;

import State.WebAppState;

import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public interface WebAppStateChange {
    void onWebAppStateChange(WebAppState oldState, WebAppState newState, String actionId, Long requestId) throws IOException, ServletException;
}
