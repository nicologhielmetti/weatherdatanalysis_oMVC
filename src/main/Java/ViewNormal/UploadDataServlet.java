package ViewNormal;

import Actions.CreateStationAction;
import Actions.UploadDataAction;
import State.ArchState;
import State.WebAppState;
import Stores.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@WebServlet(name = "UploadDataServlet")
@MultipartConfig
public class UploadDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();
        UploadDataAction uploadDataAction = new UploadDataAction(Integer.parseInt(request.getParameter("idStation")), request.getPart("newData"));
        ArchState archState = ArchState.getInstance();
        archState.getActions().put(requestIdentifier, uploadDataAction);
        archState.getRequests().put(requestIdentifier, request);
        archState.getResponses().put(requestIdentifier, response);
        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(String actionId, Long requestId) throws IOException, ServletException {
                if (requestId.equals(requestIdentifier)) {
                    ArchState archState = ArchState.getInstance();
                    HttpServletRequest request = archState.getRequests().get(requestId);
                    HttpServletResponse response = archState.getResponses().get(requestId);
                    request.setAttribute("outcomeUpload", (String)archState.getWebAppState().getServerOutcomeMap().get(requestId).getOutcome());
                    request.getRequestDispatcher("/LoadStations").forward(request,response);
                }
            }
        };
        Store.getInstance().propagateAction(uploadDataAction, requestIdentifier, webAppStateChange);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Error", "Error! GET request not supported");
        getServletContext().getRequestDispatcher("/Error").forward(request, response);
    }
}
