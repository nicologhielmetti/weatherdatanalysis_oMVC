package Policies.StatePolicies;

import Actions.Action;
import State.State;

public interface StatePolicy {
    State apply(State state, Action action);
}
