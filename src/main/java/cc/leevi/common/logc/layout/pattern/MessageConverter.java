package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public class MessageConverter extends KeywordConverter {
    @Override
    public String convert(LoggingEvent e) {
        return String.valueOf(e.getMessage());
    }
}
