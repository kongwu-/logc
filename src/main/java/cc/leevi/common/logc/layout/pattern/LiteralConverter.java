package cc.leevi.common.logc.layout.pattern;

import cc.leevi.common.logc.LoggingEvent;

public class LiteralConverter implements Converter {

    private String literal;

    @Override
    public String convert(LoggingEvent e) {
        return literal;
    }

    public LiteralConverter(String literal) {
        this.literal = literal;
    }
}
