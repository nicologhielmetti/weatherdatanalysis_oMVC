package ViewNormal;

import Actions.RegisterStationAction;
import State.WebAppState;
import Stores.Store;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@WebServlet(name = "RegisterStationServlet")
public class RegisterStationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        JSONObject data = null;
        try {
            data = new JSONObject(br.readLine());
        } catch (IOException e) {

        }
        RegisterStationAction registerStationAction = new RegisterStationAction(data);
        WebAppState webAppState = WebAppState.getInstance();
        webAppState.getActions().put(requestIdentifier, registerStationAction);
        webAppState.getRequests().put(requestIdentifier, request);
        webAppState.getResponses().put(requestIdentifier, response);

        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(Long requestId) throws IOException, ServletException {
                if (requestId.equals(requestIdentifier)) {
                    WebAppState webAppState = WebAppState.getInstance();
                    HttpServletRequest request = webAppState.getRequests().get(requestId);
                    HttpServletResponse response = webAppState.getResponses().get(requestId);

                    response.getWriter().write((String) webAppState.getStateForPolicies().getServerOutcomeMap().get(requestId).getOutcome());
                }
            }
        };

        Store.getInstance().propagateAction(registerStationAction, requestIdentifier, webAppStateChange);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Error", "Error! GET request not supported");
        getServletContext().getRequestDispatcher("/Error").forward(request, response);
    }
}
