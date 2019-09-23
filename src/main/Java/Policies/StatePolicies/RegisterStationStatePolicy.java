package Policies.StatePolicies;

import Actions.Action;
import State.ArchState;
import State.WebAppState;

public class RegisterStationStatePolicy implements StatePolicy {
    @Override
    public WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier) {
        return ArchState.getInstance().getWebAppState();
    }
}
