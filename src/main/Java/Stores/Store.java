package Stores;

import Actions.Action;
import Resolvers.ConcreteResolver;
import Resolvers.PolicyCouple;
import Resolvers.Resolver;
import State.ArchState;
import State.ObservableState;
import State.WebAppState;
import Utils.StoreLogger;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private static final Store store = new Store();
    private final Map<String, Resolver> actionGroupToResolver;
    private final ObservableState observableState;
    private final StoreLogger storeLogger;

    private Store() {
        this.actionGroupToResolver = new HashMap<>();
        this.observableState = new ObservableState(ArchState.getInstance().getWebAppState());
        this.storeLogger = StoreLogger.getInstance();
    }

    public static Store getInstance() {
        return store;
    }


    private void fillResolverMap() {
        this.actionGroupToResolver.put("@CONCRETE_RESOLVER", new ConcreteResolver());
    }

    // information redundancy: the action as parameter of propagateAction method can be avoided
    public synchronized void propagateAction(Action action, Long requestIdentifier) {
        Resolver resolver = this.actionGroupToResolver.get(action.getActionGroupIdentifier());
        try {
            PolicyCouple policyCouple = resolver.resolve(action);
            storeLogger.logPreActionPropagation(requestIdentifier);
            if (policyCouple.getStatePolicy() != null){
                this.observableState.setState(policyCouple.getStatePolicy().apply(this.getWebAppState(),action, requestIdentifier),action, requestIdentifier);
            }
            storeLogger.logPostActionPropagation(requestIdentifier);
            if (policyCouple.getSidePolicy() != null){
                // also side policies call the observable state method setState, triggering the onWebAppStateChange method in the servlets
                this.observableState.setState(policyCouple.getSidePolicy().apply(this.getWebAppState(),action, requestIdentifier),action, requestIdentifier);
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
