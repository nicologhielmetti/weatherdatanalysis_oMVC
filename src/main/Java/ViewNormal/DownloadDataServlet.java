package ViewNormal;

import Actions.DownloadDataAction;
import State.WebAppState;
import Stores.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "DownloadDataServlet")
public class DownloadDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Error", "Error! POST request not supported");
        getServletContext().getRequestDispatcher("/Error").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Long requestIdentifier = new Date().getTime();

        long beginTimestamp = 0L;// = Long.parseLong(request.getParameter("begin_timestamp")) / 1000; //1565340900L;
        long endTimestamp = 0L;// = Long.parseLong(request.getParameter("end_timestamp")) / 1000;  //1565343600L;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date begin_date = dateFormat.parse(request.getParameter("begin_date"));
            Date end_date = dateFormat.parse(request.getParameter("end_date"));
            beginTimestamp = begin_date.getTime() / 1000;
            endTimestamp = end_date.getTime() / 1000;
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of exception
        }
        Integer stationId = Integer.parseInt(request.getParameter("station_id")); //1;
        DownloadDataAction downloadDataAction = new DownloadDataAction(stationId, beginTimestamp, endTimestamp);

        WebAppState webAppState = WebAppState.getInstance();
        webAppState.getActions().put(requestIdentifier, downloadDataAction);
        webAppState.getRequests().put(requestIdentifier, request);
        webAppState.getResponses().put(requestIdentifier, response);

        WebAppStateChange webAppStateChange = new WebAppStateChange() {
            @Override
            public void onWebAppStateChange(Long requestId) throws IOException, ServletException {
                if (requestId.equals(requestIdentifier)) {
                    WebAppState webAppState = WebAppState.getInstance();
                    HttpServletRequest request = webAppState.getRequests().get(requestId);
                    HttpServletResponse response = webAppState.getResponses().get(requestId);

                    long beginTimestamp = ((DownloadDataAction) webAppState.getActions().get(requestId)).getBeginTimestamp();
                    long endTimestamp = ((DownloadDataAction) webAppState.getActions().get(requestId)).getEndTimestamp();

                    //create the file
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    response.setContentType("Application/CSV");
                    response.setHeader("Content-Disposition", "attachment; filename=" + "data_" + format.format(new Date(beginTimestamp)) + "_" +
                            format.format(new Date(endTimestamp)) + ".csv");
                    OutputStream out = response.getOutputStream();
                    FileInputStream in = new FileInputStream("data_" + format.format(new Date(beginTimestamp)) + "_" +
                            format.format(new Date(endTimestamp)) + ".csv");
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.flush();
                }
            }
        };

        Store.getInstance().propagateAction(downloadDataAction, requestIdentifier, webAppStateChange);
    }
}
