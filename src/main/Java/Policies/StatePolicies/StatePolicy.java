package Policies.StatePolicies;

import Actions.Action;
import State.State;

public interface StatePolicy {
    void apply(State state, Action action);

}
