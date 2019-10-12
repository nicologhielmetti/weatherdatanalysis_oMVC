package Policies.SidePolicies;

import Actions.Action;
import Actions.DownloadDataAction;
import Policies.StatePolicies.StatePolicy;
import State.Model.Datum;
import State.Model.Station;
import State.WebAppState;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadDataSidePolicy implements SidePolicy{

    @Override
    public WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier) {

        Long beginTimestamp = ((DownloadDataAction)action).getBeginTimestamp();
        Long endTimestamp = ((DownloadDataAction)action).getEndTimestamp();
        Integer stationId = ((DownloadDataAction)action).getIdStation();
        Map<String, Object> param = new HashMap<>();
        param.put("idStation", stationId);
        Station station =  (Station) HibernateUtil.executeSelect(
                "from Station where idStation = :idStation", false, param).getResponse();
        param.put("begin_timestamp", beginTimestamp);
        param.put("end_timestamp", endTimestamp);
        String getDataToDownloadQuery = "where d.datumPK.timestamp between :begin_timestamp AND :end_timestamp AND d.datumPK.station.id = :idStation";
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
        HibernateResult result = HibernateUtil.executeSelect(getDataToDownloadQuery, true, param);
        webAppState.getLogMap().put(requestIdentifier, result.getMsg());
        List data = ((List)result.getResponse());
        if (data.size() == 0) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(new Exception(),
                    "The csv file generated is empty! Please redo the procedure selecting other dates"));
            return webAppState;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("data_" + format.format(new Date(beginTimestamp)) + "_" +
                    format.format(new Date(endTimestamp)) + ".csv");
        } catch (FileNotFoundException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "File not found."));
        }


        printWriter.println(((Datum) data.get(0)).getFieldsNameAsCSV());
        for (Object datum : data)
            printWriter.println(((Datum) datum).getFieldsAsCSV());
        printWriter.close();

        try {
            webAppState.getServerOutcomeMap().put(requestIdentifier,
                    new ServerOutcome(null, new ObjectMapper().writeValueAsString(data)));
        } catch (IOException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(e, "Error in parsing the data."));
        }
        return webAppState;

    }
}
