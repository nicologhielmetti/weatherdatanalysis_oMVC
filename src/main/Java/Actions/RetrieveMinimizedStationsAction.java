package Actions;

public class RetrieveMinimizedStationsAction extends Action{

    public RetrieveMinimizedStationsAction() {
        super("@RETRIEVE_MIN_STATIONS", "@CONCRETE_RESOLVER");
    }

    @Override
    public String toString() {
        return "RetrieveMinimizedStationsAction{}";
    }
}
