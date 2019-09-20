package State;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import Actions.Action;
import Utils.HibernateUtil;
import Utils.ServerOutcome;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This class should contains the
 */

public class ArchState implements Serializable {
    private ConcurrentMap<Long, HttpServletRequest> requests;
    private ConcurrentMap<Long, HttpServletResponse> responses;
    private ConcurrentMap<Long, Action> actions;
    private WebAppState webAppState;

    public ArchState() {
        this.requests = new ConcurrentHashMap<>();
        this.responses = new ConcurrentHashMap<>();
        this.actions = new ConcurrentHashMap<>();
        this.we = new HashMap<>();

    }
}
