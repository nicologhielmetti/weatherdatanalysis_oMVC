package Actions;

import java.io.Serializable;

public abstract class Action implements Serializable {
    private final String actionIdentifier;
    private final String actionGroupIdentifier;



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

}
