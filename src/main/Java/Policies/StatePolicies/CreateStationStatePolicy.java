package Policies.StatePolicies;

import Actions.Action;
import Actions.CreateStationAction;
import State.Model.Station;
import State.WebAppState;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CreateStationStatePolicy implements StatePolicy{

    @Override
    public WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier) {
        BufferedReader br = ((CreateStationAction)action).getBufferedReader();
        HibernateResult result;
        try {
            String json = br.readLine();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Station station = mapper.readValue(json, Station.class);
                result = HibernateUtil.executeInsert(station);
                webAppState.getLogMap().put(requestIdentifier, result.getMsg());
            } catch (JsonMappingException e) {
                webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "{\"success\": \"false\", \"text\": \"Latitude, Longitude and Altitude must be numbers!\"}"));
                return webAppState;
            }
        } catch (IOException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "{\"success\": \"false\", \"text\": \"Unable to create the station. Try again.\"}"));
        }

        webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(null, "{\"success\": \"true\", \"text\": \"The new station has been created succesfully. In order to see the modification you must reload the page.\"}"));
        return webAppState;
    }
}