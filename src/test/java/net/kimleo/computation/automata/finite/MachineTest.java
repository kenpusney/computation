package net.kimleo.computation.automata.finite;

import org.junit.Before;
import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static net.kimleo.commons.C.setOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MachineTest {

    private DFARulebook rulebook;

    @Before
    public void setUp() throws Exception {
        rulebook = new DFARulebook(listOf(
            new FARule(1, 'a', 2),
            new FARule(2, 'a', 2),
            new FARule(3, 'a', 3),
            new FARule(1, 'b', 1),
            new FARule(2, 'b', 3),
            new FARule(3, 'b', 3)));
    }

    @Test
    public void testDFARules() throws Exception {

        DFA dfa = new DFA(1, setOf(3), rulebook);
        assertFalse(dfa.isAcceptable());

        dfa.input('b');
        assertFalse(dfa.isAcceptable());

        for (int i = 0; i < 3; i++) {
            dfa.input('a');
        }
        assertFalse(dfa.isAcceptable());

        dfa.input('b');
        assertTrue(dfa.isAcceptable());
    }

    @Test
    public void testDFARules2() throws Exception {
        DFA dfa = new DFA(1, setOf(3), rulebook);
        assertFalse(dfa.isAcceptable());

        dfa.input("baaab");
        assertTrue(dfa.isAcceptable());
    }
}