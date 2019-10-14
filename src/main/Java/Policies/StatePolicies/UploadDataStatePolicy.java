package Policies.StatePolicies;

import Actions.Action;
import Actions.UploadDataAction;
import State.Model.*;
import State.WebAppState;
import Utils.HibernateResult;
import Utils.HibernateUtil;
import Utils.ServerOutcome;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UploadDataStatePolicy implements StatePolicy {
    @Override
    public WebAppState apply(WebAppState webAppState, Action action, Long requestIdentifier) {
        UploadDataAction uploadDataAction = (UploadDataAction) action;
        String hql = "FROM Station WHERE idStation = :idStation";
        Integer idStation = uploadDataAction.getIdStation();

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("idStation", idStation);
            Station station = (Station)(HibernateUtil.executeSelect(hql, false, param)).getResponse();
            InputStream fileContent = uploadDataAction.getFilePart().getInputStream();
            Reader in = new InputStreamReader(fileContent, StandardCharsets.UTF_8);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            Vector<Datum> dataToUpload = new Vector<>();
            for (CSVRecord record : records) {
                try {
                    Long timestamp = Long.parseLong(record.get("timestamp")); // /uFEFF is the Byte Order Mark --> removed, it gave problems
                    Float temperature = Float.parseFloat(record.get("temperature"));
                    Float pressure = Float.parseFloat(record.get("pressure"));
                    Float humidity = Float.parseFloat(record.get("humidity"));
                    Float rain = Float.parseFloat(record.get("rain"));
                    Float windModule = Float.parseFloat(record.get("windModule"));
                    String windDirection = record.get("windDirection");

                    // Maybe the below line should be an Object instead of a Float?
                    Float additionalField;
                    Datum datum;
                    DatumPK datumPK;
                    switch (station.getType().toLowerCase()) {
                        case "city":
                            additionalField = Float.parseFloat(record.get("pollutionLevel"));
                            datumPK = new DatumPK(timestamp, station);
                            datum = new DatumCity(datumPK, temperature, pressure, humidity, rain, windModule, windDirection, additionalField);
                            break;
                        case "country":
                            additionalField = Float.parseFloat(record.get("dewPoint"));
                            datumPK = new DatumPK(timestamp, station);
                            datum = new DatumCountry(datumPK, temperature, pressure, humidity, rain, windModule, windDirection, additionalField);
                            break;
                        case "mountain":
                            additionalField = Float.parseFloat(record.get("snowLevel"));
                            datumPK = new DatumPK(timestamp, station);
                            datum = new DatumMountain(datumPK, temperature, pressure, humidity, rain, windModule, windDirection, additionalField);
                            break;
                        case "sea":
                            additionalField = Float.parseFloat(record.get("uvRadiation"));
                            datumPK = new DatumPK(timestamp, station);
                            datum = new DatumSea(datumPK, temperature, pressure, humidity, rain, windModule, windDirection, additionalField);
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    dataToUpload.add(datum);
                } catch (NumberFormatException e) {
                    // if a datum contains an invalid field, skip it
                }
            }

            HibernateResult result = HibernateUtil.executeInsert(dataToUpload);
            webAppState.getLogMap().put(requestIdentifier, result.getMsg());

        } catch (IllegalArgumentException | IOException e) {
            webAppState.getServerOutcomeMap().put(requestIdentifier,new ServerOutcome(e, "The .csv file you are trying to upload does not fit for the selected station. Use another one."));
            return webAppState;
        }

        webAppState.getServerOutcomeMap().put(requestIdentifier,new ServerOutcome(null, "Your .csv file has been successfully uploaded."));
        return webAppState;
    }
}
