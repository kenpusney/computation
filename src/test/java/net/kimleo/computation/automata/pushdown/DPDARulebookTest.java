package net.kimleo.computation.automata.pushdown;

import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static net.kimleo.commons.C.setOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DPDARulebookTest {
    @Test
    public void testRuleBook() throws Exception {
        PDAConfiguration<Integer> configuration = new PDAConfiguration<>(1, new Stack<>(listOf('$')));

        DPDARulebook<Integer> rulebook = new DPDARulebook<>(listOf(
                new PDARule<>(1, '(', 2, '$', listOf('b', '$')),
                new PDARule<>(2, '(', 2, 'b', listOf('b', 'b')),
                new PDARule<>(2, ')', 2, 'b', listOf()),
                new PDARule<>(2, null, 1, '$', listOf('$'))
        ));

        DPDA<Integer> dpda = new DPDA<>(configuration, setOf(1), rulebook);

        assertTrue(dpda.isAcceptable());
        dpda.input("(()");
        assertFalse(dpda.isAcceptable());

        dpda.input(")");
        assertTrue(dpda.isAcceptable());

        dpda = new DPDA<>(configuration, setOf(1), rulebook);

        dpda.input("())");
        assertFalse(dpda.isAcceptable());
        assertTrue(dpda.stucked());
    }
}