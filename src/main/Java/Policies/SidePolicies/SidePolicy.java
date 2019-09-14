package Policies.SidePolicies;

import Actions.Action;
import State.State;

public interface SidePolicy {
    void apply(State state, Action action);
}
