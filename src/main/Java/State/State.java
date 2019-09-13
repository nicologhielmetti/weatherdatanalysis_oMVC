package State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import State.Model.Station;

/**
 * This class should contains the
 */

public class State implements Serializable {
    private static ArrayList<Station> state;

    private State(){ state = new ArrayList<>(); }

    public static ArrayList<Station> getInstance() { return state; }
}
