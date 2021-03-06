package Policies.StatePolicies;

import Actions.Action;
import Actions.RegisterStationAction;
import State.Model.Station;
import State.StateForPolicies;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.PeriodicDataAcquirer;
import Utils.ServerOutcome;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterStationStatePolicy implements StatePolicy {
    @Override
    public StateForPolicies apply(StateForPolicies stateForPolicies, Action action, Long requestIdentifier) {
        Map<String, Object> param = new HashMap<>();

        JSONObject data = ((RegisterStationAction)action).getJSONObject();

        URL url;
        try {
            url = new URL(data.getString("URL"));
        } catch (MalformedURLException e) {
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "{\"success\": \"false\", \"text\": \"The URL must be valid.\"}"));
            return stateForPolicies;
        }

        Integer timeInterval;
        try {
            timeInterval = Integer.parseInt(data.getString("timeInterval"));
        } catch (NumberFormatException e) {
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "{\"success\": \"false\", \"text\": \"The time interval must be an integer.\"}"));
            return stateForPolicies;
        }

        param.put("idStation", Integer.parseInt(data.getString("idStation")));
        Station station = (Station)((HibernateResult)HibernateUtil.executeSelect("FROM Station WHERE idStation = :idStation", false, param)).getResponse();
        new PeriodicDataAcquirer(url.toString(), station, timeInterval);
        stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(null, "{\"success\": \"true\", \"text\": \"Station " + station.getName() + " successfully registered!\"}"));
        return stateForPolicies;
    }
}
