package Actions;

public class RetrieveStationAction extends Action {
    private Integer stationId;

    public RetrieveStationAction(int stationId) {
        super("@RETRIEVE_STATION", "@CONCRETE_RESOLVER");
        this.stationId = stationId;
    }

    public int getStationId() {
        return stationId;
    }

    @Override
    public String toString() {
        return "RetrieveStationAction{" +
                "stationId=" + stationId +
                "}";
    }
}
