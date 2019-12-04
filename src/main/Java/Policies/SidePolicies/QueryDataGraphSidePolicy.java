package Policies.SidePolicies;

import Actions.Action;
import Actions.QueryDataGraphAction;
import State.Model.DatumForGraph;
import State.Model.Station;
import State.Model.UnitOfMeasure;
import State.StateForPolicies;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDataGraphSidePolicy implements SidePolicy {
    @Override
    public StateForPolicies apply(StateForPolicies stateForPolicies, Action action, Long requestIdentifier) {
        List<DatumForGraph> dataOfStations = new ArrayList<>();
        ArrayList<Integer> stationIds = ((QueryDataGraphAction)action).getStationIds();
        Long beginTimestamp = ((QueryDataGraphAction)action).getBeginTimestamp();
        Long endTimestamp = ((QueryDataGraphAction)action).getEndTimestamp();
        String weatherDimension = ((QueryDataGraphAction)action).getWeatherDimension();
        Boolean isAvgRequired = ((QueryDataGraphAction)action).getAvgRequired();
        for (Integer stationId : stationIds) {
            Map<String, Object> param = new HashMap<>();
            param.put("idStation", stationId);
            Station station = (Station) HibernateUtil.executeSelect(
                    "from Station where idStation = :idStation", false, param).getResponse();
            param.put("begin_timestamp", beginTimestamp);
            param.put("end_timestamp", endTimestamp);
            String getDataToDownloadQuery = "where d.datumPK.timestamp between :begin_timestamp AND :end_timestamp " +
                    "AND d.datumPK.station.id = :idStation";
            switch (station.getType().toLowerCase()) {
                case "city":
                    getDataToDownloadQuery = "from DatumCity as d " + getDataToDownloadQuery;
                    break;
                case "country":
                    getDataToDownloadQuery = "from DatumCountry as d " + getDataToDownloadQuery;
                    break;
                case "mountain":
                    getDataToDownloadQuery = "from DatumMountain as d " + getDataToDownloadQuery;
                    break;
                case "sea":
                    getDataToDownloadQuery = "from DatumSea as d " + getDataToDownloadQuery;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            //retrieve the data required
            Double avg = null;
            String log = "";
            if (isAvgRequired) {
                String getAvg = "select avg(d." + weatherDimension.toLowerCase() + ") " +
                        getDataToDownloadQuery;
                HibernateResult hibernateResult = HibernateUtil.executeSelect(getAvg, false, param);
                log.concat(hibernateResult.getMsg().concat("\n"));
                avg = (Double) hibernateResult.getResponse();
            }
            String getTimestampQuery = "select d.datumPK.timestamp*1000 " + getDataToDownloadQuery;

            String[] splittedWeatherDimension = weatherDimension.toLowerCase().split(" ");
            String compliantWeatherDimension = splittedWeatherDimension[0].toLowerCase();
            for (int i = 1; i < splittedWeatherDimension.length; i++) {
                char[] chars = splittedWeatherDimension[i].toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                compliantWeatherDimension = compliantWeatherDimension.concat(String.valueOf(chars));
            }

            String getMeasurementsQuery = "select " + compliantWeatherDimension + " " + getDataToDownloadQuery;
            HibernateResult measurementsHR = HibernateUtil.executeSelect(getMeasurementsQuery, true, param);
            List<Float> measurements = (List<Float>) measurementsHR.getResponse();
            log.concat(measurementsHR.getMsg().concat("\n"));
            HibernateResult timestampHR = HibernateUtil.executeSelect(getTimestampQuery, true, param);
            List<Long> timestamp = (List<Long>) timestampHR.getResponse();
            log.concat(timestampHR.getMsg().concat("\n"));

            String weatherDimForReflection = compliantWeatherDimension.substring(0, 1).toUpperCase() +
                    compliantWeatherDimension.substring(1);
            if (!measurements.isEmpty()) {
                DatumForGraph data = null;
                UnitOfMeasure unitOfMeasure = station.getUnitOfMeasure();
                try {
                    data = new DatumForGraph(stationId, station.getName(), (String) unitOfMeasure.getClass()
                            .getMethod("get" + weatherDimForReflection).invoke(unitOfMeasure), measurements, timestamp, avg);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                dataOfStations.add(data);
            }
        }


        if (dataOfStations.isEmpty()) {
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(new Exception(), "{\"success\": \"false\", \"text\": \"There are no data for the selected station in the selected time frame. Try to change the request parameter or to upload some data.\"}"));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            try {
                json = mapper.writeValueAsString(dataOfStations);
            } catch (IOException e) {
                stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "{\"success\": \"false\", \"text\": \"Unable to map the data.\"}"));
            }
            stateForPolicies.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(null, "{\"success\": \"true\", \"text\":" + json + "}"));
        }
        return stateForPolicies;
    }
}
