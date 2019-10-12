package Actions;

import State.Model.Station;

import java.io.BufferedReader;

public class CreateStationAction extends Action{
    BufferedReader br;

    public CreateStationAction (BufferedReader br) {
        super("@CREATE_STATION_ACTION", "@CONCRETE_RESOLVER");
        this.br = br;
    }

    public BufferedReader getBufferedReader() {
        return br;
    }

    @Override
    public String toString() {
        return "CreateStationAction{" +
                "bufferedReader=" + br +
                "}";
    }
}
