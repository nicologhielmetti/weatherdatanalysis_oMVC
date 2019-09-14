package Actions;

import java.net.URL;

public class RegisterStationAction extends Action {
    URL url;
    Integer timeInterval;

    public RegisterStationAction(URL url, int timeInterval) {
        super("@REGISTER_STATION", "@CONCRETE_RESOLVER");
        this.url = url;
        this.timeInterval = timeInterval;
    }

    public URL getUrl() {
        return url;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    @Override
    public String toString() {
        return "RegisterStationAction{" +
                "url=" + url +
                ", timeInterval=" + timeInterval +
                '}';
    }
}
