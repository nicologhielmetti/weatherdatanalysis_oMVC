package Policies.SidePolicies;

import Actions.Action;
import State.StateForPolicies;

public interface SidePolicy {
    StateForPolicies apply(StateForPolicies stateForPolicies, Action action, Long requestIdentifier);
}
