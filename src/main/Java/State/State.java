package State;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Utils.HibernateUtil;
import Utils.ServerOutcome;


/**
 * This class should contains the
 */

public class State implements Serializable {
    private Map<Integer, ServerOutcome> serverOutcomeMap;
    private static final State instance = new State();

    private State(){
        serverOutcomeMap = new HashMap<>();
    }

    public static State getInstance() { return instance; }

    public Map<Integer, ServerOutcome> getServerOutcomeMap() {
        return serverOutcomeMap;
    }

    public void setServerOutcomeMap(Map<Integer,ServerOutcome> serverOutcomeMap) {
        this.serverOutcomeMap = serverOutcomeMap;
    }

    public void executeInsert(Collection<?> data) {
        HibernateUtil.executeInsert(data);
    }

    public void executeInsert(Object data) {
        HibernateUtil.executeInsert(data);
    }


}
