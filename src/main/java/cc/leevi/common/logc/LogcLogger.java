package cc.leevi.common.logc;

import cc.leevi.common.logc.appender.AppenderAttachableImpl;

public class LogcLogger implements Logger ,LifeCycle{

    private String name;
    private Level level = Level.TRACE;
    private int effectiveLevelInt;
    private LogcLogger parent;
    private AppenderAttachableImpl aai;

    private LoggerContext loggerContext;

    @Override
    public void trace(String msg) {
        filterAndLog(Level.TRACE,msg);
    }

    @Override
    public void info(String msg) {
        filterAndLog(Level.INFO,msg);
    }

    @Override
    public void debug(String msg) {
        filterAndLog(Level.DEBUG,msg);
    }

    @Override
    public void warn(String msg) {
        filterAndLog(Level.WARN,msg);
    }

    @Override
    public void error(String msg) {
        filterAndLog(Level.ERROR,msg);
    }

    @Override
    public String getName() {
        return name;
    }

    private void filterAndLog(Level level,String msg){
        LoggingEvent e = new LoggingEvent(level, msg,getName());
        for (LogcLogger l = this;l != null;l = l.parent){
            if(l.aai == null){
                continue;
            }
            if(level.toInt()>effectiveLevelInt){
                l.aai.appendLoopOnAppenders(e);
            }
            //优先使用当前logger，如果当前没有则向上查找，找到就跳出
            //默认情况下，如果不配置完整类名的logger，这里都需要向上查找，直至root
            //比如name=x.y.z.AClass，则配置logger name="x.y.z"，则Aclass会使用x.y.z这个logger
            break;
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(Level level) {
        this.effectiveLevelInt = level.toInt();
        this.level = level;
    }

    public void setEffectiveLevelInt(int effectiveLevelInt) {
        this.effectiveLevelInt = effectiveLevelInt;
    }

    public void setParent(Logger parent) {
        this.parent = (LogcLogger) parent;
    }

    public void setAai(AppenderAttachableImpl aai) {
        this.aai = aai;
    }

    public void setLoggerContext(LoggerContext loggerContext) {
        this.loggerContext = loggerContext;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
