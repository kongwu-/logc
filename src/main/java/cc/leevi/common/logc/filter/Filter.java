package cc.leevi.common.logc.filter;

import cc.leevi.common.logc.LifeCycle;
import cc.leevi.common.logc.LoggingEvent;

public interface Filter extends LifeCycle {
    boolean doFilter(LoggingEvent event);
}
