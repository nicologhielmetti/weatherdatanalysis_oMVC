package Resolvers;

import Policies.SidePolicies.SidePolicy;
import Policies.StatePolicies.StatePolicy;


public class PolicyCouple {
    private final StatePolicy statePolicy;
    private final SidePolicy sidePolicy;


    public PolicyCouple(StatePolicy statePolicy, SidePolicy sidePolicy) {
        this.statePolicy = statePolicy;
        this.sidePolicy = sidePolicy;
    }

    public StatePolicy getStatePolicy() {
        return statePolicy;
    }

    public SidePolicy getSidePolicy() {
        return sidePolicy;
    }
}
