package State;

import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebAppState implements Serializable {
    private ConcurrentHashMap<Long, ServerOutcome> serverOutcomeMap;
    private ConcurrentHashMap<Long, String> logMap; //what the log has to save

    WebAppState() {
        this.serverOutcomeMap = new ConcurrentHashMap<>();
        this.logMap = new ConcurrentHashMap<>();
    }

    public Map<Long, ServerOutcome> getServerOutcomeMap() {
        return serverOutcomeMap;
    }

    public void setServerOutcomeMap(ConcurrentHashMap<Long, ServerOutcome> serverOutcomeMap) {
        this.serverOutcomeMap = serverOutcomeMap;
    }

    public Map<Long, String> getLogMap() {
        return logMap;
    }

    public void setLogMap(ConcurrentHashMap<Long, String> modifiedDbInfo) {
        this.logMap = modifiedDbInfo;
    }
}