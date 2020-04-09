package net.kimleo.computation.automata.finite.nondeterministic;

import net.kimleo.commons.Pair;
import net.kimleo.computation.automata.finite.DFADesign;
import net.kimleo.computation.automata.finite.FARule;
import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static net.kimleo.commons.C.setOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class NFASimulationTest {
    @Test
    public void testSimulation() throws Exception {

        NFARulebook<Integer> rulebook = new NFARulebook<>(listOf(
            new FARule<>(1, null, 2),
            new FARule<>(1, null, 4),
            new FARule<>(2, 'a', 3),
            new FARule<>(3, 'a', 2),
            new FARule<>(4, 'a', 5),
            new FARule<>(5, 'a', 6),
            new FARule<>(6, 'a', 4)));

        NFADesign<Integer> nfaDesign = new NFADesign<>(1, setOf(2, 4), rulebook);

        NFASimulation<Integer> sim = new NFASimulation<>(nfaDesign);

        Pair pair = sim.discoverStatesAndRules(setOf(nfaDesign.toNFA().currentStates()));

        assertNotNull(pair);
    }

    @Test
    public void testNFAtoDFA() throws Exception {
        NFARulebook<Integer> rulebook = new NFARulebook<>(listOf(
                new FARule<>(1, null, 2),
                new FARule<>(1, null, 4),
                new FARule<>(2, 'a', 3),
                new FARule<>(3, 'a', 2),
                new FARule<>(4, 'a', 5),
                new FARule<>(5, 'a', 6),
                new FARule<>(6, 'a', 4)));

        NFADesign<Integer> nfaDesign = new NFADesign<>(1, setOf(2, 4), rulebook);

        NFASimulation sim = new NFASimulation<>(nfaDesign);

        DFADesign dfaDesign = sim.toDFADesign();

        assertTrue(dfaDesign.accepts(""));
        assertTrue(dfaDesign.accepts("aa"));

        assertFalse(dfaDesign.accepts("a"));
        assertFalse(dfaDesign.accepts("aaaaa"));
    }
}