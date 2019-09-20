package Policies.SidePolicies;

import Actions.Action;
import State.WebAppState;

public interface SidePolicy {
    void apply(WebAppState webAppState, Action action);
}
