package Resolvers;

import Actions.Action;

import java.util.HashMap;
import java.util.Map;

public abstract class Resolver {
    protected final Map<String, PolicyCouple> policiesMap;

    public Resolver() {
        this.policiesMap = new HashMap<>();
        this.fillPoliciesMap();
    }

    protected abstract void fillPoliciesMap();


    public PolicyCouple resolve(Action action) throws Exception {
        PolicyCouple policyCouple = this.policiesMap.get(action.getActionIdentifier());
        if (policyCouple == null ){
            throw new Exception("No policy for the action is defined");
        }
        else {
            if (policyCouple.getSidePolicy() == null && policyCouple.getStatePolicy() == null){
                throw new Exception("No policy for the action is defined");

            }
            return policyCouple;
        }
    }
}
