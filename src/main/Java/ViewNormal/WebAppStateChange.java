package ViewNormal;

import State.WebAppState;

import javax.servlet.ServletException;
import java.io.IOException;

public interface WebAppStateChange {
    void onWebAppStateChange(String actionId, Long requestId) throws IOException, ServletException;
}
