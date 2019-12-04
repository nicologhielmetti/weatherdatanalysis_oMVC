package ViewNormal;

import Actions.QueryDataGraphAction;
import State.WebAppState;
import Stores.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "QueryDataGraphServlet")
public class QueryDataGraphServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Error", "Error! POST request not supported");
        getServletContext().getRequestDispatcher("/Error").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();
        if (request.getParameter("station0") == null || request.getParameter("station0").isEmpty() ||
                request.getParameter("weatherDimension") == null || request.getParameter("weatherDimension").isEmpty() ||
                request.getParameter("startDate") == null || request.getParameter("startDate").isEmpty() ||
                request.getParameter("endDate") == null || request.getParameter("endDate").isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": \"false\", \"text\": \"You have to fill all the form to get a result.\"}");
        } else {
            boolean isAvgRequired = false;
            if (request.getParameter("showAvg") != null && request.getParameter("showAvg").equals("true"))
                isAvgRequired = true;
            ArrayList<Integer> stationIds = new ArrayList<>();
            int id = 0;
            long beginTimestamp = 0L;
            long endTimestamp = 0L;
            while (request.getParameter("station" + id) != null && !request.getParameter("station" + id).isEmpty())
                stationIds.add(Integer.valueOf(request.getParameter("station" + id++)));
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date begin_date = dateFormat.parse(request.getParameter("startDate"));
                Date end_date = dateFormat.parse(request.getParameter("endDate"));
                beginTimestamp = begin_date.getTime() / 1000;
                endTimestamp = end_date.getTime() / 1000;
            } catch (Exception e) { //this generic but you can control another types of exception
                // look the origin of exception
            }

            QueryDataGraphAction queryDataGraphAction = new QueryDataGraphAction(stationIds, beginTimestamp, endTimestamp, request.getParameter("weatherDimension"), isAvgRequired);
            WebAppState webAppState = WebAppState.getInstance();
            webAppState.getActions().put(requestIdentifier, queryDataGraphAction);
            webAppState.getRequests().put(requestIdentifier, request);
            webAppState.getResponses().put(requestIdentifier, response);

            WebAppStateChange webAppStateChange = new WebAppStateChange() {
                @Override
                public void onWebAppStateChange(Long requestId) throws IOException, ServletException {
                    if (requestId.equals(requestIdentifier)) {
                        WebAppState webAppState = WebAppState.getInstance();
                        HttpServletRequest request = webAppState.getRequests().get(requestId);
                        HttpServletResponse response = webAppState.getResponses().get(requestId);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write((String) webAppState.getStateForPolicies().getServerOutcomeMap().get(requestId).getOutcome());

                    }
                }
            };

            Store.getInstance().propagateAction(queryDataGraphAction, requestIdentifier, webAppStateChange);
        }
    }
}
