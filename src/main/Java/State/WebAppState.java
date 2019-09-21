package State;

import Utils.HibernateUtil;
import Utils.ServerOutcome;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WebAppState implements Serializable {
    private Map<Long, ServerOutcome> serverOutcomeMap;
    private Map<Long, String> modifiedDbInfo;

    WebAppState() {
        this.serverOutcomeMap = new HashMap<>();
        this.modifiedDbInfo = new HashMap<>();
    }

    public Map<Long, ServerOutcome> getServerOutcomeMap() {
        return serverOutcomeMap;
    }

    public void setServerOutcomeMap(Map<Long, ServerOutcome> serverOutcomeMap) {
        this.serverOutcomeMap = serverOutcomeMap;
    }

    public Map<Long, String> getModifiedDbInfo() {
        return modifiedDbInfo;
    }

    public void setModifiedDbInfo(Map<Long, String> modifiedDbInfo) {
        this.modifiedDbInfo = modifiedDbInfo;
    }

    public void executeInsert(Collection<?> data) {
        HibernateUtil.executeInsert(data);
    }

    public void executeInsert(Object data) {
        HibernateUtil.executeInsert(data);
    }

    public Object executeSelect(String queryString, boolean isResultList) {
        return HibernateUtil.executeSelect(queryString, isResultList);
    }

    public Object executeSelect(String queryString, boolean isResultList, Map<String, Object> params) {
        return HibernateUtil.executeSelect(queryString, isResultList, params);
    }
}