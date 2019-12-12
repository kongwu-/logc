package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public class LoggerConverter extends KeywordConverter {
    @Override
    public String convert(LoggingEvent e) {
        return e.getLoggerName();
    }
}
