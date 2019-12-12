package cc.leevi.common.logc.config;

import cc.leevi.common.logc.Logger;
import cc.leevi.common.logc.LoggerContext;
import cc.leevi.common.logc.LoggerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XMLConfiguratorTest {

    private Configurator configurator;

    private LoggerContext loggerContext = new LoggerContext();

    @Before
    public void setUp() throws Exception {

        configurator = new XMLConfigurator(getClass().getClassLoader().getResource("logc.xml"),loggerContext);
    }

    @Test
    public void doConfigure() {
        configurator.doConfigure();
        System.out.println(loggerContext);
        System.out.println("done!");
    }

    @After
    public void after(){
    }
}