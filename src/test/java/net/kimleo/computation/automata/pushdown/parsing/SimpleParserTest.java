package net.kimleo.computation.automata.pushdown.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleParserTest {

    @Test
    public void testSimpleParser() throws Exception {
        SimpleParser simpleParser = new SimpleParser();

        assertTrue(simpleParser.accepts("while (i < 5) { i = i * 2 }"));
        assertTrue(simpleParser.accepts("a = 1"));
        assertFalse(simpleParser.accepts("1 = a"));
    }
}