package Actions;

import java.io.Serializable;

public abstract class Action implements Serializable {
    protected final String actionIdentifier;
    protected final String actionGroupIdentifier;
    private String outcome;


    public Action(String actionIdentifier, String actionGroupIdentifier) {
        this.actionIdentifier = actionIdentifier;
        this.actionGroupIdentifier = actionGroupIdentifier;
    }

    public String getActionIdentifier() {
        return actionIdentifier;
    }

    public String getActionGroupIdentifier() {
        return actionGroupIdentifier;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
