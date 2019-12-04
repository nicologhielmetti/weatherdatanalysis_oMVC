package Utils;

import State.WebAppState;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Represents a lambda MVC Logger
 */
public class StoreLogger {
    private final Logger logger;
    private  static StoreLogger instance;

    public static StoreLogger getInstance(){
        if (instance == null){
            instance = new StoreLogger();
        }
        return instance;
    }

    private StoreLogger(){
        this.logger = Logger.getLogger("STORE_LOGGER");
        this.initLogger();
    }

    /**
     * Init the {@link Logger} associated to this class with a file handler
     */
    private void initLogger() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(date);
        File file = new File(formattedDate+".log");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileHandler fh = new FileHandler(formattedDate+".log",true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fh.setFormatter(simpleFormatter);
            this.logger.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logPreActionPropagation(Long requestIdentifier) throws IOException {
        WebAppState webAppState = WebAppState.getInstance();

        this.logger.log(Level.INFO, "@@BEGIN_LOG_ITEM@@");
        this.logger.log(Level.INFO, "@@ACTION@@");
        this.logger.log(Level.INFO, webAppState.getActions().get(requestIdentifier).toString());
        this.logger.log(Level.INFO, "@@PRE_TIMESTAMP@@");
        this.logger.log(Level.INFO, new Date().getTime() + "");
        this.logger.log(Level.INFO, "@@PRE_STATE@@");
        this.logger.log(Level.INFO, "HttpServletRequest: " + new HttpServletRequestSerialized(
                webAppState.getRequests().get(requestIdentifier), requestIdentifier).serialize()
        );
    }


    public void logPostActionPropagation(Long requestIdentifier) throws IOException {
        WebAppState webAppState = WebAppState.getInstance();
        ServerOutcome serverOutcome = webAppState.getStateForPolicies().getServerOutcomeMap().get(requestIdentifier);
        this.logger.log(Level.INFO, "@@POST_TIMESTAMP@@");
        this.logger.log(Level.INFO, new Date().getTime()+"");
        this.logger.log(Level.INFO, "@@POST_STATE@@");
        this.logger.log(Level.INFO, "Outcome: " + serverOutcome.getOutcome());
        if (serverOutcome.getException() == null) {
            this.logger.log(Level.INFO, "QueryPerformed: " + webAppState.getStateForPolicies().getLogMap().get(requestIdentifier));
        }
        this.logger.log(Level.INFO, "@@END_LOG_ITEM@@");
    }

}
