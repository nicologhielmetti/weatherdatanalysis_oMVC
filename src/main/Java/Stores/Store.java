package Stores;

import Actions.Action;
import Resolvers.ConcreteResolver;
import Resolvers.PolicyCouple;
import Resolvers.Resolver;
import State.ArchState;
import State.ObservableState;
import State.WebAppState;
import Utils.StoreLogger;
import ViewNormal.WebAppStateChange;

import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private static final Store store = new Store();
    private final Map<String, Resolver> actionGroupToResolver;
    private final StoreLogger storeLogger;

    private Store() {
        this.actionGroupToResolver = new HashMap<>();
        this.storeLogger = StoreLogger.getInstance();
        this.fillResolverMap();
    }

    public static Store getInstance() {
        return store;
    }


    private void fillResolverMap() {
        this.actionGroupToResolver.put("@CONCRETE_RESOLVER", new ConcreteResolver());
    }

    // information redundancy: the action as parameter of propagateAction method can be avoided
    public synchronized void propagateAction(Action action, Long requestIdentifier, WebAppStateChange webAppStateChange) throws IOException, ServletException {
        //this.observableState.addChangeListener(webAppStateChangeObserver);
        Resolver resolver = this.actionGroupToResolver.get(action.getActionGroupIdentifier());
        try {
            PolicyCouple policyCouple = resolver.resolve(action);
            storeLogger.logPreActionPropagation(requestIdentifier);
            if (policyCouple.getStatePolicy() != null){
                //ArchState.getInstance().setWebAppState(policyCouple.getStatePolicy().apply(this.getWebAppState(),action, requestIdentifier));
                //this.observableState.setState(ArchState.getInstance(), action, requestIdentifier);
                ArchState.getInstance().setWebAppState(policyCouple.getStatePolicy().apply(ArchState.getInstance().getWebAppState(),action, requestIdentifier));

            }

            if (policyCouple.getSidePolicy() != null){
                // also side policies call the observable state method setState, triggering the onWebAppStateChange method in the servlets
                //ArchState.getInstance().setWebAppState(policyCouple.getSidePolicy().apply(this.getWebAppState(),action, requestIdentifier));
                //this.observableState.setState(ArchState.getInstance(), action, requestIdentifier);
                ArchState.getInstance().setWebAppState(policyCouple.getSidePolicy().apply(ArchState.getInstance().getWebAppState(),action, requestIdentifier));
            }
            storeLogger.logPostActionPropagation(requestIdentifier);

        } catch (Exception e) {
            e.printStackTrace();
        }
        webAppStateChange.onWebAppStateChange(action.getActionIdentifier(), requestIdentifier);
        //this.observableState.removeChangeListener(webAppStateChangeObserver);
    }
}
