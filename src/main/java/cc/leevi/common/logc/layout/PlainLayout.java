package cc.leevi.common.logc.layout;

import cc.leevi.common.logc.LoggingEvent;
import cc.leevi.common.logc.layout.Layout;

/**
 * 纯文本布局，直接调用{@link cc.leevi.common.logc.LoggingEvent#toString()}
 */
public class PlainLayout implements Layout {

    @Override
    public String doLayout(LoggingEvent e) {
        return e.toString();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
