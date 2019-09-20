package Policies.StatePolicies;

import Actions.Action;
import State.State;

public class RegisterStationStatePolicy implements StatePolicy {
    @Override
    public State apply(State state, Action action) {
        return State.getInstance();
    }
}
