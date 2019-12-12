package cc.leevi.common.logc;

import cc.leevi.common.logc.config.Configurator;
import cc.leevi.common.logc.config.XMLConfigurator;
import cc.leevi.common.logc.config.YAMLConfigurator;

import java.net.URL;

public class ContextInitializer {
    final public static String AUTOCONFIG_FILE = "logc.xml";
    final public static String YAML_FILE = "logc.yml";

    private static final LoggerContext DEFAULT_LOGGER_CONTEXT = new LoggerContext();

    static {
    }



    public static void autoconfig() {
        URL url = getConfigURL();
        if(url == null){
            System.err.println("config[logc.xml or logc.yml] file not found!");
            return ;
        }
        String urlString = url.toString();
        Configurator configurator = null;

        if(urlString.endsWith("xml")){
            configurator = new XMLConfigurator(url,DEFAULT_LOGGER_CONTEXT);
        }
        if(urlString.endsWith("yml")){
            configurator = new YAMLConfigurator(url,DEFAULT_LOGGER_CONTEXT);
        }
        configurator.doConfigure();
    }

    private static URL getConfigURL(){
        URL url = null;
        ClassLoader classLoader = ContextInitializer.class.getClassLoader();
        url = classLoader.getResource(AUTOCONFIG_FILE);
        if(url != null){
            return url;
        }
        url = classLoader.getResource(YAML_FILE);
        if(url != null){
            return url;
        }
        return null;
    }

    public static LoggerContext getDefautLoggerContext(){
        return DEFAULT_LOGGER_CONTEXT;
    }

}
