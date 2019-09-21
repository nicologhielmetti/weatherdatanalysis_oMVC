package Policies.SidePolicies;

import Actions.Action;
import State.WebAppState;

public interface SidePolicy {
    WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier);
}
