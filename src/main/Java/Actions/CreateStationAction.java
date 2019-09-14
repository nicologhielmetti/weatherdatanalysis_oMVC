package Actions;

import State.Model.Station;

public class CreateStationAction extends Action{
    private Station station;

    public CreateStationAction (Station station) {
        super("@CREATE_STATION_ACTION", "@CONCRETE_RESOLVER");
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "CreateStationAction{" +
                "station=" + station +
                "}";
    }
}
