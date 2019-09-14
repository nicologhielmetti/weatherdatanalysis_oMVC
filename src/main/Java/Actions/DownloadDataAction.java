package Actions;

import State.Model.Station;

public class DownloadDataAction extends Action{
    private Station station;
    private Long beginTimestamp;
    private Long endTimestamp;

    public DownloadDataAction(Station station, long beginTimestamp, long endTimestamp) {
        super("@DOWNLOAD_DATA_ACTION", "@CONCRETE_RESOLVER");
        this.station = station;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public Station getStation() {
        return station;
    }

    public long getBeginTimestamp() {
        return beginTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    @Override
    public String toString() {
        return "DownloadDataAction{" +
                "station=" + station +
                ", beginTimestamp=" + beginTimestamp +
                ", endTimestamp=" + endTimestamp +
                '}';
    }
}
