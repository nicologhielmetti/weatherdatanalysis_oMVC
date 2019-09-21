package State;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import Actions.Action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This class should contains the
 */

public class ArchState implements Serializable {
    private ConcurrentMap<Long, HttpServletRequest> requests;
    private ConcurrentMap<Long, HttpServletResponse> responses;
    private ConcurrentMap<Long, Action> actions;
    private static ArchState archState = new ArchState();

    private WebAppState webAppState;

    private ArchState() {
        this.requests = new ConcurrentHashMap<>();
        this.responses = new ConcurrentHashMap<>();
        this.actions = new ConcurrentHashMap<>();
        this.webAppState = new WebAppState();
    }

    public static ArchState getInstance() {
        return archState;
    }

    public ConcurrentMap<Long, HttpServletRequest> getRequests() {
        return requests;
    }

    public void setRequests(ConcurrentMap<Long, HttpServletRequest> requests) {
        this.requests = requests;
    }

    public ConcurrentMap<Long, HttpServletResponse> getResponses() {
        return responses;
    }

    public void setResponses(ConcurrentMap<Long, HttpServletResponse> responses) {
        this.responses = responses;
    }

    public ConcurrentMap<Long, Action> getActions() {
        return actions;
    }

    public void setActions(ConcurrentMap<Long, Action> actions) {
        this.actions = actions;
    }

    public WebAppState getWebAppState() {
        return webAppState;
    }
}
