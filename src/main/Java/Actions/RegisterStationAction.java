package Actions;

import org.json.JSONObject;

import java.io.BufferedReader;

public class RegisterStationAction extends Action {
    JSONObject data;

    public RegisterStationAction(JSONObject data) {
        super("@REGISTER_STATION", "@CONCRETE_RESOLVER");
        this.data = data;
    }

    public JSONObject getJSONObject() {
        return data;
    }

    @Override
    public String toString() {
        return "RegisterStationAction{" +
                "data=" + data +
                '}';
    }
}
