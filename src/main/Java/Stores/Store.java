package Stores;

import Actions.Action;
import Resolvers.ConcreteResolver;
import Resolvers.PolicyCouple;
import Resolvers.Resolver;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private static final Store store = new Store();
    private final Map<String, Resolver> actionGroupToResolver;


    private Store() {
        this.actionGroupToResolver = new HashMap<>();

    }

    public  Store getInstance() {
        return store;
    }

    private void fillResolverMap() {
        this.actionGroupToResolver.put("@CONCRETE_RESOLVER", new ConcreteResolver());
    }

    public synchronized void propagateAction(Action action) {
        Resolver resolver = this.actionGroupToResolver.get(action.getActionGroupIdentifier());
        try {
            PolicyCouple policyCouple = resolver.resolve(action);
            //TODO: log state pre action propagation
            if (policyCouple.getStatePolicy() != null){
                //this.observableState.setServerState(policyCouple.getStatePolicy().apply(this.getState(), action),action);
            }
            //TODO: log state post action propagation
            if (policyCouple.getSidePolicy() != null){
                //policyCouple.getSidePolicy().apply(this.getState(),action);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
