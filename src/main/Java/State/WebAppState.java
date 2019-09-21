package State;

import Utils.ServerOutcome;

import java.util.HashMap;
import java.util.Map;

public class WebAppState {
    private Map<Long, ServerOutcome> serverOutcomeMap;

    WebAppState() {
        this.serverOutcomeMap = new HashMap<>();
    }

    public Map<Long, ServerOutcome> getServerOutcomeMap() {
        return serverOutcomeMap;
    }

    public void setServerOutcomeMap(Map<Long, ServerOutcome> serverOutcomeMap) {
        this.serverOutcomeMap = serverOutcomeMap;
    }
}
