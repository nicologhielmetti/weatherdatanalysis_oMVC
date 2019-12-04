package Policies.StatePolicies;

import Actions.Action;
import State.StateForPolicies;

public interface StatePolicy {
    StateForPolicies apply(StateForPolicies stateForPolicies, Action action, Long requestIdentifier);
}
