package Actions;

import java.io.BufferedReader;
import java.net.URL;

public class RegisterStationAction extends Action {
    BufferedReader br;

    public RegisterStationAction(BufferedReader br) {
        super("@REGISTER_STATION", "@CONCRETE_RESOLVER");
        this.br = br;
    }

    public BufferedReader getBufferedReader() {
        return br;
    }

    @Override
    public String toString() {
        return "RegisterStationAction{" +
                "bufferedReader=" + br +
                '}';
    }
}
