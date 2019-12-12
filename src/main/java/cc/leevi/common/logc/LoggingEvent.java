package cc.leevi.common.logc;

public class LoggingEvent {
    public long timestamp;
    private Level level;
    private Object message;
    private String threadName;
    private long threadId;
    private String loggerName;

    public LoggingEvent(Level level, Object message, String loggerName) {
        this.level = level;
        this.message = message;
        this.loggerName = loggerName;
        this.timestamp = System.currentTimeMillis();
        Thread ct = Thread.currentThread();
        this.threadId = ct.getId();
        this.threadName = ct.getName();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public String toString() {
        return "LoggingEvent{" +
                "timestamp=" + timestamp +
                ", level=" + level +
                ", message=" + message +
                ", threadName='" + threadName + '\'' +
                ", threadId=" + threadId +
                ", loggerName='" + loggerName + '\'' +
                '}';
    }
}
