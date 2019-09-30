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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

        //create the file
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("data_" + format.format(new Date(beginTimestamp)) + "_" +
                    format.format(new Date(beginTimestamp)) + ".csv");
        } catch (FileNotFoundException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier, new ServerOutcome(new Exception(),
                    "Cannot find the file."));
        }
        printWriter.println(((Datum) data.get(0)).getFieldsNameAsCSV());
        for (Object datum : data)
            printWriter.println(((Datum) datum).getFieldsAsCSV());
        printWriter.close();
        return webAppState;

        /*
        To be done in the servlet?
        //download the file
        response.setContentType("Application/CSV");
        response.setHeader("Content-Disposition", "attachment; filename=" + "data_" + request.getParameter("begin_date").replace('/', '-') + "_" + request.getParameter("end_date").replace('/', '-') + ".csv");
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream("data_" + request.getParameter("begin_date").replace('/', '-') + "_" + request.getParameter("end_date").replace('/', '-') + ".csv");
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0)
            out.write(buffer, 0, length);
        in.close();
        out.flush();*/

    }
}
