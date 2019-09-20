package Utils;

public class ServerOutcome {
    private Object outcome;
    private Exception exception;

    public ServerOutcome(Exception exception, String outcome) {
        this.outcome = outcome;
        this.exception = exception;
    }

    public Object getOutcome() {
        return outcome;
    }

    public Exception getException() {
        return exception;
    }
}
