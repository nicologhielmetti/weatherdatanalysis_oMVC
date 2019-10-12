package ViewNormal;

import Actions.RetrieveMinimizedStationsAction;
import State.ArchState;
import State.Model.MinimizedStation;
import State.WebAppState;
import Stores.Store;
import Utils.ServerOutcome;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "LoadMinimizedStationsServlet")
public class LoadMinimizedStationsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final Long requestIdentifier = new Date().getTime();
        RetrieveMinimizedStationsAction retrieveMinimizedStationsAction = new RetrieveMinimizedStationsAction();
        ArchState archState = ArchState.getInstance();
        archState.getActions().put(requestIdentifier, retrieveMinimizedStationsAction);
        archState.getRequests().put(requestIdentifier, request);
        archState.getResponses().put(requestIdentifier, response);

        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(String actionId, Long requestId) {
                try {
                    if (requestId.equals(requestIdentifier)) {
                        ArchState archState = ArchState.getInstance();
                        HttpServletRequest request = archState.getRequests().get(requestId);
                        HttpServletResponse response = archState.getResponses().get(requestId);
                        ServerOutcome serverOutcome = archState.getWebAppState().getServerOutcomeMap().get(requestId);
                        if (serverOutcome.getException() == null) {
                            String json = (String)serverOutcome.getOutcome();
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setContentType("application/json");
                                response.setCharacterEncoding("UTF-8");
                                response.getWriter().write(json);
                            } else {
                                // this branch is taken only if the request is a non-ajax request, this happens only when we need to reload the Homepage
                                request.setAttribute("stations", json);
                                request.getRequestDispatcher("/Homepage").forward(request, response);
                            }
                        } else {

                        }
                    }
                } catch (IOException | ServletException e) {

                }
            }
        };

        /*WebAppStateChangeObserver webAppStateChangeObserver = new WebAppStateChangeObserver(requestIdentifier, retrieveMinimizedStationsAction.getActionIdentifier(), new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(WebAppState oldState, WebAppState newState, String actionId, Long requestId) throws IOException, ServletException {

                if (requestId.equals(requestIdentifier)) {
                    ArchState archState = ArchState.getInstance();
                    HttpServletRequest request = archState.getRequests().get(requestId);
                    HttpServletResponse response = archState.getResponses().get(requestId);
                    ServerOutcome serverOutcome = newState.getServerOutcomeMap().get(requestId);
                    if (serverOutcome.getException() == null) {
                        List<MinimizedStation> results = (List<MinimizedStation>) serverOutcome.getOutcome();
                        ObjectMapper mapper = new ObjectMapper();
                        String json = mapper.writeValueAsString(results);
                        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(json);
                        } else {
                            // this branch is taken only if the request is a non-ajax request, this happens only when we need to reload the Homepage
                            request.setAttribute("stations", json);
                            request.getRequestDispatcher("/Homepage").forward(request, response);
                        }
                    } else {

                    }
                }
            }
        });*/

        Store.getInstance().propagateAction(retrieveMinimizedStationsAction, requestIdentifier, webAppStateChange);
    }
}
