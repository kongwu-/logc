package cc.leevi.common.logc;

public enum Level {
    ERROR(40000, "ERROR"), WARN(30000, "WARN"), INFO(20000, "INFO"), DEBUG(10000, "DEBUG"), TRACE(5000, "TRACE");

    private int levelInt;
    private String levelStr;

    Level(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public static Level parse(String level) {
        return valueOf(level.toUpperCase());
    }

    public int toInt() {
        return levelInt;
    }

    public String toString() {
        return levelStr;
    }

    public boolean isGreaterOrEqual(Level level) {
        return levelInt>=level.toInt();
    }

}
