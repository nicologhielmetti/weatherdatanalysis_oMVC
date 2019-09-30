package Policies.SidePolicies;

import Actions.Action;
import State.Model.MinimizedStation;
import State.WebAppState;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class LoadMinimizedStationsSidePolicy implements SidePolicy {
    @Override
    public WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier) {

        HibernateResult result = HibernateUtil.executeSelect("SELECT new State.Model.MinimizedStation(idStation,name,type) FROM Station", true);
        webAppState.getLogMap().put(requestIdentifier, result.getMsg());

        try {
            ObjectMapper mapper = new ObjectMapper();
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(null, mapper.writeValueAsString(result.getResponse())));
        } catch (IOException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "Unable to retrieve stations. Try again."));
        }

        return webAppState;
    }
}
