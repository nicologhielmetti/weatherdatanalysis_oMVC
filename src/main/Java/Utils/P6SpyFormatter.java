package Utils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyFormatter implements MessageFormattingStrategy{

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        return connectionId + " | " + category + " | " + elapsed +  " | " + now + " | " + sql;
    }
}
