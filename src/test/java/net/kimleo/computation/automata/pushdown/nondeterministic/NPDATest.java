package net.kimleo.computation.automata.pushdown.nondeterministic;

import net.kimleo.computation.automata.pushdown.PDAConfiguration;
import net.kimleo.computation.automata.pushdown.PDARule;
import net.kimleo.computation.automata.pushdown.Stack;
import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static net.kimleo.commons.C.setOf;
import static org.junit.Assert.*;

public class NPDATest {
    @Test
    public void testNPDA() throws Exception {
        NPDARulebook<Integer> rulebook = new NPDARulebook<>(
                listOf(
                        new PDARule<>(1, 'a', 1, '$', listOf('a', '$')),
                        new PDARule<>(1, 'a', 1, 'a', listOf('a', 'a')),
                        new PDARule<>(1, 'a', 1, 'b', listOf('a', 'b')),
                        new PDARule<>(1, 'b', 1, '$', listOf('b', '$')),
                        new PDARule<>(1, 'b', 1, 'a', listOf('b', 'a')),
                        new PDARule<>(1, 'b', 1, 'b', listOf('b', 'b')),
                        new PDARule<>(1, null, 2, '$', listOf('$')),
                        new PDARule<>(1, null, 2, 'a', listOf('a')),
                        new PDARule<>(1, null, 2, 'b', listOf('b')),
                        new PDARule<>(2, 'a', 2, 'a', listOf()),
                        new PDARule<>(2, 'b', 2, 'b', listOf()),
                        new PDARule<>(2, null, 3, '$', listOf('$'))
                )
        );

        PDAConfiguration<Integer> configuration = new PDAConfiguration<>(1, new Stack<>(listOf('$')));

        NPDA<Integer> npda = new NPDA<>(setOf(configuration), listOf(3), rulebook);


        assertTrue(npda.isAcceptable());
        npda.input("abb");
        assertFalse(npda.isAcceptable());

        npda.input("a");
        assertTrue(npda.isAcceptable());

        NPDADesign<Integer> npdaDesign = new NPDADesign<>(1, '$', listOf(3), rulebook);

        assertTrue(npdaDesign.accepts("abba"));
        assertTrue(npdaDesign.accepts("babbaabbab"));

        assertFalse(npdaDesign.accepts("abb"));
        assertFalse(npdaDesign.accepts("baabaa"));
    }


}