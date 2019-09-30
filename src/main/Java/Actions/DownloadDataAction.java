package Actions;

import State.Model.Station;

public class DownloadDataAction extends Action{
    private Integer idStation;
    private Long beginTimestamp;
    private Long endTimestamp;

    public DownloadDataAction(Integer idStation, long beginTimestamp, long endTimestamp) {
        super("@DOWNLOAD_DATA_ACTION", "@CONCRETE_RESOLVER");
        this.idStation = idStation;
        this.beginTimestamp = beginTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public Integer getIdStation() {
        return idStation;
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
                "idStation=" + idStation +
                ", beginTimestamp=" + beginTimestamp +
                ", endTimestamp=" + endTimestamp +
                '}';
    }
}
