package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public class LineSeparatorConverter extends KeywordConverter {
    @Override
    public String convert(LoggingEvent e) {
        return System.lineSeparator();
    }
}
