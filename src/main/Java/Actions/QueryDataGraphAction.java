package Actions;

import java.util.ArrayList;

public class QueryDataGraphAction extends Action{

    private ArrayList<Integer> stationIds;
    private Long beginTimestamp;
    private Long endTimestamp;
    private String weatherDimension;
    private Boolean isAvgRequired;

    public QueryDataGraphAction(ArrayList<Integer> stationIds, Long beginTimestamp, Long endTimestamp, String weatherDimension, Boolean isAvgRequired) {
        super("@QUERY_DATA_TO_DISPLAY", "@CONCRETE_RESOLVER");
        this.stationIds = stationIds;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
        this.weatherDimension = weatherDimension;
        this.isAvgRequired = isAvgRequired;
    }

    public ArrayList<Integer> getStationIds() {
        return stationIds;
    }

    public Long getBeginTimestamp() {
        return beginTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public String getWeatherDimension() {
        return weatherDimension;
    }

    public Boolean getAvgRequired() {
        return isAvgRequired;
    }

    @Override
    public String toString() {
        return "QueryDataGraphAction{" +
                "stationIds=" + stationIds +
                ", beginTimestamp=" + beginTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", weatherDimension='" + weatherDimension + '\'' +
                ", isAvgRequired=" + isAvgRequired +
                '}';
    }
}
