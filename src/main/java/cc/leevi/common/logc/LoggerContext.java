package cc.leevi.common.logc;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个全局的上下文对象
 */
public class LoggerContext {

    /**
     * 根logger
     */
    private Logger root;

    /**
     * logger缓存，存放解析配置文件后生成的logger对象，以及通过程序手动创建的logger对象
     */
    private Map<String,Logger> loggerCache = new HashMap<>();

    public void addLogger(String name,Logger logger){
        loggerCache.put(name,logger);
    }

    public void addLogger(Logger logger){
        loggerCache.put(logger.getName(),logger);
    }

    public Logger getRoot() {
        return root;
    }

    public void setRoot(Logger root) {
        this.root = root;
    }

    public Map<String, Logger> getLoggerCache() {
        return loggerCache;
    }

    public void setLoggerCache(Map<String, Logger> loggerCache) {
        this.loggerCache = loggerCache;
    }
}
