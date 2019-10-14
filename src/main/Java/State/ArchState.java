package State;

import Actions.Action;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArchState implements Serializable {
    private static ArchState archState = new ArchState();

    private ConcurrentHashMap<Long, HttpServletRequest> requests;
    private ConcurrentHashMap<Long, HttpServletResponse> responses;
    private ConcurrentHashMap<Long, Action> actions;

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

    public WebAppState getWebAppState() {
        return webAppState;
    }

    public void setWebAppState(WebAppState webAppState) {
        this.webAppState = webAppState;
    }
}
