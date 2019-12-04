package ViewNormal;

import Actions.UploadDataAction;
import State.WebAppState;
import Stores.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "UploadDataServlet")
@MultipartConfig
public class UploadDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();
        UploadDataAction uploadDataAction = new UploadDataAction(Integer.parseInt(request.getParameter("idStation")), request.getPart("newData"));
        WebAppState webAppState = WebAppState.getInstance();
        webAppState.getActions().put(requestIdentifier, uploadDataAction);
        webAppState.getRequests().put(requestIdentifier, request);
        webAppState.getResponses().put(requestIdentifier, response);
        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(Long requestId) throws IOException, ServletException {
                if (requestId.equals(requestIdentifier)) {
                    WebAppState webAppState = WebAppState.getInstance();
                    HttpServletRequest request = webAppState.getRequests().get(requestId);
                    HttpServletResponse response = webAppState.getResponses().get(requestId);
                    request.setAttribute("outcomeUpload", (String) webAppState.getStateForPolicies().getServerOutcomeMap().get(requestId).getOutcome());
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
