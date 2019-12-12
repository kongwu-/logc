package cc.leevi.common.logc.layout;

import cc.leevi.common.logc.Level;
import cc.leevi.common.logc.LoggingEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatternLayoutTest {

    private PatternLayout patternLayout;

    @Before
    public void setUp() throws Exception {
        patternLayout = new PatternLayout();
        patternLayout.setPattern("LOGC %d %t %p %c - %m%n");
    }

    @Test
    public void doLayout() {
        for (int i = 0; i < 10; i++) {
            LoggingEvent e = new LoggingEvent(Level.INFO,"hello logc",PatternLayoutTest.class.getName());
            String body = patternLayout.doLayout(e);
            System.out.println(body);
        }

    }
}