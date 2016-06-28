package net.kimleo.computation.automata.finite.nondeterministic;

import net.kimleo.computation.automata.finite.FARule;
import org.junit.Test;

import java.util.Set;

import static net.kimleo.commons.$C.listOf;
import static net.kimleo.commons.$C.setOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class MachineTest {
    @Test
    public void testNFA() throws Exception {
        NFARulebook<Integer> rulebook = new NFARulebook<>(listOf(
            new FARule<>(1, 'a', 1),
            new FARule<>(2, 'a', 3),
            new FARule<>(3, 'a', 4),
            new FARule<>(1, 'b', 1),
            new FARule<>(1, 'b', 2),
            new FARule<>(2, 'b', 3),
            new FARule<>(3, 'b', 4)));

        assertThat(rulebook.nextStates('b', 1),  hasItems(1, 2));
        assertThat(rulebook.nextStates('a', 1, 2), hasItems(1, 3));
        assertThat(rulebook.nextStates('b', 1 ,3), hasItems(1, 2, 4));

        NFA nfa = new NFA<>(setOf(1), setOf(4), rulebook);

        assertFalse(nfa.isAcceptable());

        nfa.input('b');
        assertFalse(nfa.isAcceptable());

        nfa.input('a');
        assertFalse(nfa.isAcceptable());

        nfa.input('b');
        assertTrue(nfa.isAcceptable());

        nfa = new NFA<>(setOf(1), setOf(4), rulebook);

        assertFalse(nfa.isAcceptable());
        nfa.input("bbbb");
        assertTrue(nfa.isAcceptable());

        NFADesign nfaDesign = new NFADesign<>(1, setOf(4), rulebook);

        assertTrue(nfaDesign.accepts("bbbbb"));
        assertFalse(nfaDesign.accepts("bbabb"));
    }

    @Test
    public void testFreeMove() throws Exception {
        NFARulebook<Integer> rulebook = new NFARulebook<>(listOf(
                new FARule<>(1, null, 2),
                new FARule<>(1, null, 4),
                new FARule<>(2, 'a', 3),
                new FARule<>(3, 'a', 2),
                new FARule<>(4, 'a', 5),
                new FARule<>(5, 'a', 6),
                new FARule<>(6, 'a', 4)));

        assertThat(rulebook.nextStates(null, 1), hasItems(2, 4));

        Set<Integer> states = rulebook.followFreeMoves(setOf(1));

        assertThat(states, hasItems(1, 2, 4));


        NFADesign nfaDesign = new NFADesign<>(1, setOf(2, 4), rulebook);

        assertTrue(nfaDesign.accepts("aa"));
        assertTrue(nfaDesign.accepts("aaa"));
        assertFalse(nfaDesign.accepts("aaaaa"));
        assertTrue(nfaDesign.accepts("aaaaaa"));
    }
}