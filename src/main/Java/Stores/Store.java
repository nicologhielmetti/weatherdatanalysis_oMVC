package Stores;

import Actions.Action;
import Resolvers.ConcreteResolver;
import Resolvers.PolicyCouple;
import Resolvers.Resolver;
import State.ObservableState;
import State.WebAppState;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private static final Store store = new Store();
    private final Map<String, Resolver> actionGroupToResolver;
    private final ObservableState observableState;

    private Store() {
        this.actionGroupToResolver = new HashMap<>();
        this.observableState = new ObservableState(WebAppState.getInstance());
    }

    public static Store getInstance() {
        return store;
    }

    private void fillResolverMap() {
        this.actionGroupToResolver.put("@CONCRETE_RESOLVER", new ConcreteResolver());
    }

    public synchronized void propagateAction(Action action, Long requestIdentifier) {
        Resolver resolver = this.actionGroupToResolver.get(action.getActionGroupIdentifier());
        try {
            PolicyCouple policyCouple = resolver.resolve(action);
            //TODO: log state pre action propagation
            if (policyCouple.getStatePolicy() != null){
                this.observableState.setState(policyCouple.getStatePolicy().apply(this.getWebAppState(),action),action, requestIdentifier);
            }
            //TODO: log state post action propagation
            if (policyCouple.getSidePolicy() != null){
                //policyCouple.getSidePolicy().apply(this.getWebAppState(),action);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void observeState(PropertyChangeListener propertyChangeListener) {
        this.observableState.addChangeListener(propertyChangeListener);
    }

    public WebAppState getWebAppState() {
        return this.observableState.getWebAppState();
    }
}
