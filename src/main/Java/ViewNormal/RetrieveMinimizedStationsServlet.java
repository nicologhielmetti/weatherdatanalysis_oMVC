package ViewNormal;

import Actions.RetrieveMinimizedStationsAction;
import State.ArchState;
import State.WebAppState;
import Stores.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "RetrieveMinimizedStationsServlet")
public class RetrieveMinimizedStationsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long requestIdentifier = new Date().getTime();
        RetrieveMinimizedStationsAction retrieveMinimizedStationsAction = new RetrieveMinimizedStationsAction();
        ArchState archState = ArchState.getInstance();
        archState.getActions().put(requestIdentifier, retrieveMinimizedStationsAction);
        archState.getRequests().put(requestIdentifier, request);
        archState.getResponses().put(requestIdentifier, response);
        new WebAppStateChangeObserver(requestIdentifier, retrieveMinimizedStationsAction.getActionIdentifier(), new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(WebAppState oldState, WebAppState newState, String actionId, Long requestId) {

            }
        });

        Store.getInstance().propagateAction(retrieveMinimizedStationsAction, requestIdentifier);
    }
}
