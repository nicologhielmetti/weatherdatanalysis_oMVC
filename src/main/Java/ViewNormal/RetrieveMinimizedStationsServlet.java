package ViewNormal;

import Actions.RetrieveMinimizedStationsAction;
import State.ArchState;
import State.WebAppState;
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
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@WebServlet(name = "RetrieveMinimizedStationsServlet")
public class RetrieveMinimizedStationsServlet extends HttpServlet implements PropertyChangeListener {


    @Override
    public void init() throws ServletException {
        Store.getInstance().observeState(this);
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long requestIdentifier = new Date().getTime();
        RetrieveMinimizedStationsAction retrieveMinimizedStationsAction = new RetrieveMinimizedStationsAction();
        ArchState archState = ArchState.getInstance();
        archState.getActions().put(requestIdentifier, retrieveMinimizedStationsAction);
        archState.getRequests().put(requestIdentifier, request);
        archState.getResponses().put(requestIdentifier, response);

        Store.getInstance().propagateAction(retrieveMinimizedStationsAction, requestIdentifier);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        WebAppState oldWebAppState = (WebAppState) evt.getOldValue();
        WebAppState newWebAppState = (WebAppState) evt.getNewValue();
        if (evt.getPropertyName().equals(retrieveMinimizedStationsAction.getActionIdentifier())) {
            ServerOutcome serverOutcome = WebAppState.getInstance().getServerOutcomeMap().get(requestIdentifier);
            if (serverOutcome.getException() == null) {
                // the request has been completed successfully.
            } else {
                // something went wrong, notify user.
            }

        }
    }
}
