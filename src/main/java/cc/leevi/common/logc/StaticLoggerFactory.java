package cc.leevi.common.logc;

public class StaticLoggerFactory implements ILoggerFactory {

    private LoggerContext loggerContext;

    public StaticLoggerFactory() {
        ContextInitializer.autoconfig();
        loggerContext = ContextInitializer.getDefautLoggerContext();
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    @Override
    public Logger getLogger(String name) {
        Logger logger = loggerContext.getLoggerCache().get(name);
        if(logger == null){
            logger = newLogger(name);
        }
        return logger;
    }

    @Override
    public Logger newLogger(String name) {
        LogcLogger logger = new LogcLogger();
        logger.setName(name);
        Logger parent = null;
        //拆分包名，向上查找parent logger
        for (int i = name.lastIndexOf("."); i >= 0; i = name.lastIndexOf(".",i-1)) {
            String parentName = name.substring(0,i);
            parent = loggerContext.getLoggerCache().get(parentName);
            if(parent != null){
                break;
            }
        }
        if(parent == null){
            parent = loggerContext.getRoot();
        }
        logger.setParent(parent);
        logger.setLoggerContext(loggerContext);
        return logger;
    }
}
