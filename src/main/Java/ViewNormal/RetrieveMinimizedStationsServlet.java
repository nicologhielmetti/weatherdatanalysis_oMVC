package ViewNormal;

import Actions.Action;
import Actions.RetrieveMinimizedStationsAction;
import State.State;
import Stores.Store;
import Utils.ServerOutcome;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

@WebServlet(name = "RetrieveMinimizedStationsServlet")
public class RetrieveMinimizedStationsServlet extends HttpServlet implements PropertyChangeListener {
    HttpServletRequest request;
    HttpServletResponse response;
    RetrieveMinimizedStationsAction retrieveMinimizedStationsAction;
    Integer requestIdentifier;

    @Override
    public void init() throws ServletException {
        Store.getInstance().observeState(this);
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        retrieveMinimizedStationsAction = new RetrieveMinimizedStationsAction();
        //requestIdentifier = Generator.generateNumber();

        // populate retrieveMinimizedStationsAction

        Store.getInstance().propagateAction(retrieveMinimizedStationsAction);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        State oldState = (State) evt.getOldValue();
        State newState = (State) evt.getNewValue();
        if (evt.getPropertyName().equals(retrieveMinimizedStationsAction.getActionIdentifier())) {
            ServerOutcome serverOutcome = State.getInstance().getServerOutcomeMap().get(requestIdentifier);
            if (serverOutcome.getException() == null) {
                // the request has been completed successfully.
            } else {
                // something went wrong, notify user.
            }
        }
    }
}
