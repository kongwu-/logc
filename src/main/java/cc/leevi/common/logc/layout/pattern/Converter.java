package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public interface Converter {

    String convert(LoggingEvent e);

}
