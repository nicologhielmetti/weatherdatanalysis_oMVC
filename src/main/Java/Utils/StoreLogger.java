package Utils;

import State.ArchState;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.DataFormatException;


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
        ArchState archState = ArchState.getInstance();

        this.logger.log(Level.INFO, "@@BEGIN_LOG_ITEM@@");
        this.logger.log(Level.INFO, "@@ACTION@@");
        this.logger.log(Level.INFO, archState.getActions().get(requestIdentifier).toString());
        this.logger.log(Level.INFO, "@@PRE_TIMESTAMP@@");
        this.logger.log(Level.INFO, new Date().getTime() + "");
        this.logger.log(Level.INFO, "@@PRE_STATE@@");
        this.logger.log(Level.INFO, "HttpServletRequest: " + new HttpServletRequestSerialized(
                archState.getRequests().get(requestIdentifier), requestIdentifier).serialize()
        );
    }


    public void logPostActionPropagation(Long requestIdentifier) throws IOException {
        ArchState archState = ArchState.getInstance();
        ServerOutcome serverOutcome = archState.getWebAppState().getServerOutcomeMap().get(requestIdentifier);
        this.logger.log(Level.INFO, "@@POST_TIMESTAMP@@");
        this.logger.log(Level.INFO, new Date().getTime()+"");
        this.logger.log(Level.INFO, "@@POST_STATE@@");
        this.logger.log(Level.INFO, "HttpServletRequest: " + new HttpServletRequestSerialized(archState.getRequests().get(requestIdentifier), requestIdentifier).serialize());
        this.logger.log(Level.INFO, "Outcome: " + serverOutcome.getOutcome());
        if (serverOutcome.getException() == null) {
            this.logger.log(Level.INFO, "ModifiedDBInfo: " + archState.getWebAppState().getModifiedDbInfo().get(requestIdentifier));
        }
        this.logger.log(Level.INFO, "@@END_LOG_ITEM@@");
    }

}
