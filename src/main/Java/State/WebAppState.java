package State;

import Actions.Action;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebAppState implements Serializable {
    private static WebAppState webAppState = new WebAppState();

    private ConcurrentHashMap<Long, HttpServletRequest> requests;
    private ConcurrentHashMap<Long, HttpServletResponse> responses;
    private ConcurrentHashMap<Long, Action> actions;

    private StateForPolicies stateForPolicies;

    private WebAppState() {
        this.requests = new ConcurrentHashMap<>();
        this.responses = new ConcurrentHashMap<>();
        this.actions = new ConcurrentHashMap<>();
        this.stateForPolicies = new StateForPolicies();
    }

    public static WebAppState getInstance() {
        return webAppState;
    }

    public ConcurrentMap<Long, HttpServletRequest> getRequests() {
        return requests;
    }

    public void setRequests(ConcurrentHashMap<Long, HttpServletRequest> requests) {
        this.requests = requests;
    }

    public ConcurrentMap<Long, HttpServletResponse> getResponses() {
        return responses;
    }

    public void setResponses(ConcurrentHashMap<Long, HttpServletResponse> responses) {
        this.responses = responses;
    }

    public ConcurrentMap<Long, Action> getActions() {
        return actions;
    }

    public void setActions(ConcurrentHashMap<Long, Action> actions) {
        this.actions = actions;
    }

    public StateForPolicies getStateForPolicies() {
        return stateForPolicies;
    }

    public void setStateForPolicies(StateForPolicies stateForPolicies) {
        this.stateForPolicies = stateForPolicies;
    }
}
