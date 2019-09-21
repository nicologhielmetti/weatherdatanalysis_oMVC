public class Logger {
    private Logger instance;
    public Logger getInstance(){
        if(instance == null)
            instance = new Logger();
        return instance;
    }

    private Logger(){

    }
}
