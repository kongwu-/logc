package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public class LevelConverter implements Converter {

    @Override
    public String convert(LoggingEvent e) {
        return e.getLevel().toString();
    }

}