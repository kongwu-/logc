package cc.leevi.common.logc.layout.pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PatternParserTest {

    private PatternParser patternParser;

    @Before
    public void setUp() throws Exception {
        patternParser = new PatternParser("LOGC %d %t %p %c - %m%n");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parse() {
        List<Node> nodes = patternParser.parse();
        for (Node node : nodes) {
            System.out.println(node.converter.getClass().getName());
        }
    }
}