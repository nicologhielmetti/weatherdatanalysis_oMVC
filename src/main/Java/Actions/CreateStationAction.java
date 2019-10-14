package Actions;

import org.json.JSONObject;

import java.io.BufferedReader;

public class CreateStationAction extends Action{
    JSONObject data;

    public CreateStationAction (JSONObject data) {
        super("@CREATE_STATION_ACTION", "@CONCRETE_RESOLVER");
        this.data = data;
    }

    public JSONObject getJSONObject() {
        return data;
    }

    @Override
    public String toString() {
        return "CreateStationAction{" +
                "data=" + data +
                "}";
    }
}
