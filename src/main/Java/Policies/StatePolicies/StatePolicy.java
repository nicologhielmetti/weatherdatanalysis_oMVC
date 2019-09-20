package Policies.StatePolicies;

import Actions.Action;
import State.WebAppState;

public interface StatePolicy {
    WebAppState apply(WebAppState webAppState, Action action);
}
