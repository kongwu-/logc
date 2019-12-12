package cc.leevi.common.logc.appender;

import cc.leevi.common.logc.LifeCycle;
import cc.leevi.common.logc.LoggingEvent;

public interface Appender extends LifeCycle {

    String getName();

    void setName(String name);

    void append(LoggingEvent e);
}
