package ViewNormal;

import Actions.CreateStationAction;
import State.ArchState;

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

@WebServlet(name = "CreateStationServlet")
public class CreateStationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        JSONObject data = null;
        try {
            data = new JSONObject(br.readLine());
        } catch (IOException e) {

        }
        CreateStationAction createStationAction = new CreateStationAction(data);
        ArchState archState = ArchState.getInstance();
        archState.getActions().put(requestIdentifier, createStationAction);
        archState.getRequests().put(requestIdentifier, request);
        archState.getResponses().put(requestIdentifier, response);
        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(Long requestId) throws IOException, ServletException {
                if (requestId.equals(requestIdentifier)) {
                    ArchState archState = ArchState.getInstance();
                    HttpServletRequest request = archState.getRequests().get(requestId);
                    HttpServletResponse response = archState.getResponses().get(requestId);
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write((String)archState.getWebAppState().getServerOutcomeMap().get(requestId).getOutcome());
                }
            }
        };

        Store.getInstance().propagateAction(createStationAction, requestIdentifier, webAppStateChange);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Error", "Error! GET request not supported!");
        getServletContext().getRequestDispatcher("/Error").forward(request, response);
    }
}
