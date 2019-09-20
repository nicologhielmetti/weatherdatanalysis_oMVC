package Policies.SidePolicies;

import Actions.Action;
import State.Model.MinimizedStation;
import State.WebAppState;
import Utils.HibernateUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class RetrieveMinimizedStationsSidePolicy implements SidePolicy {
    @Override
    public void apply(WebAppState webAppState, Action action) {
        try {
            List<MinimizedStation> results = (List<MinimizedStation>) HibernateUtil.executeSelect("SELECT new Model.MinimizedStation(idStation,name,type) FROM Station", true);
            ObjectMapper mapper = new ObjectMapper();
            action.setOutcome(mapper.writeValueAsString(results));
        } catch (IOException e) {
            //
        }
    }
}
