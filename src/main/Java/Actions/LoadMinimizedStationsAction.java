package Actions;

public class LoadMinimizedStationsAction extends Action{

    public LoadMinimizedStationsAction() {
        super("@LOAD_MIN_STATIONS", "@CONCRETE_RESOLVER");
    }

    @Override
    public String toString() {
        return "LoadMinimizedStationsAction{}";
    }
}
