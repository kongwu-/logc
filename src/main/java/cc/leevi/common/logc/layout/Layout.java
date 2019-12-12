package cc.leevi.common.logc.layout;

import cc.leevi.common.logc.LifeCycle;
import cc.leevi.common.logc.LoggingEvent;

public interface Layout extends LifeCycle {
    String doLayout(LoggingEvent e);
}
