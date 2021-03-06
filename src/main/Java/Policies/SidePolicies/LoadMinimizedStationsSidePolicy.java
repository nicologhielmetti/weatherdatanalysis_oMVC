package Policies.SidePolicies;

import Actions.Action;
import State.StateForPolicies;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class LoadMinimizedStationsSidePolicy implements SidePolicy {
    @Override
    public StateForPolicies apply(StateForPolicies stateForPolicies, Action action, Long requestIdentifier) {

        HibernateResult result = HibernateUtil.executeSelect("SELECT new State.Model.MinimizedStation(idStation,name,type) FROM Station", true);
        stateForPolicies.getLogMap().put(requestIdentifier, result.getMsg());

        try {
            ObjectMapper mapper = new ObjectMapper();
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(null, mapper.writeValueAsString(result.getResponse())));
        } catch (IOException e) {
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "Unable to retrieve stations. Try again."));
        }

        return stateForPolicies;
    }
}
