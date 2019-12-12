
package cc.leevi.common.logc.appender;

/**
 * Interface for attaching appenders to objects.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 0.9.1
 */
public interface AppenderAttachable {

    void addAppender(Appender newAppender);

    Appender getAppender(String name);

    boolean isAttached(Appender appender);

    void removeAppender(Appender appender);

    void removeAppender(String name);
}

