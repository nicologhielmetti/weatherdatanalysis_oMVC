package State;

import Utils.ServerOutcome;

import java.util.HashMap;
import java.util.Map;

public class WebAppState {
    private Map<Long, ServerOutcome> serverOutcomeMap;
    private static WebAppState webAppState = new WebAppState();

    private WebAppState() {
        this.serverOutcomeMap = new HashMap<>();
    }

    public static WebAppState getInstance() {
        return webAppState;
    }

    public Map<Long, ServerOutcome> getServerOutcomeMap() {
        return serverOutcomeMap;
    }

    public void setServerOutcomeMap(Map<Long, ServerOutcome> serverOutcomeMap) {
        this.serverOutcomeMap = serverOutcomeMap;
    }
}
