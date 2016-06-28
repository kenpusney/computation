package net.kimleo.computation.automata.pushdown.parsing;

import org.junit.Test;

import java.util.List;

import static net.kimleo.commons.$C.listOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class LexicalAnalyzerTest {
    @Test
    public void testRegex() throws Exception {

    }

    @Test
    public void testAnalyse() throws Exception {
        LexicalAnalyzer analyzer = new LexicalAnalyzer("while a if { if () else 1 < 2 } false");

        List<Character> analyze = analyzer.analyze();

        assertThat(analyze, is(listOf('w', 'v', 'i', '{', 'i', '(', ')', 'e', 'n', '<', 'n', '}', 'b')));
    }
}