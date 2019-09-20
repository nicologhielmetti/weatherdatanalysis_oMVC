package Policies.StatePolicies;

import Actions.Action;
import State.WebAppState;

public class RegisterStationStatePolicy implements StatePolicy {
    @Override
    public WebAppState apply(WebAppState webAppState, Action action) {
        return WebAppState.getInstance();
    }
}
