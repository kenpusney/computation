package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PatternTest {
    @Test
    public void testPatternToString() throws Exception {
        Pattern pattern =
                new Repeat(
                        new Choose(
                                new Concatenate(
                                        new Literal("a"),
                                        new Literal(("b"))),
                                new Literal("a")));

        assertThat(pattern.toString(), is("(ab|a)*"));
    }

    @Test
    public void testEmptySemantics() throws Exception {
        Pattern pattern = new Empty();

        NFADesign nfa = pattern.toNfaDesign();
        assertTrue(nfa.accepts(""));
    }

    @Test
    public void testLiteralSemantics() throws Exception {
        Pattern pattern = new Literal("a");

        NFADesign nfa = pattern.toNfaDesign();
        assertTrue(nfa.accepts("a"));
        assertFalse(nfa.accepts("b"));
    }

    @Test
    public void testConcatenateSemantics() throws Exception {
        Pattern pattern = new Concatenate(new Literal("a"), new Literal("b"));

        assertTrue(pattern.match("ab"));
        assertFalse(pattern.match("bac"));
    }

    @Test
    public void testChooseSemantics() throws Exception {
        Pattern pattern = new Choose(new Literal("a"), new Literal("b"));

        assertTrue(pattern.match("a"));
        assertTrue(pattern.match("b"));
        assertFalse(pattern.match("abc"));
    }


    @Test
    public void testRepeatSemantics() throws Exception {
        Pattern pattern = new Repeat(new Literal("a"));

        assertTrue(pattern.match("a"));
        assertTrue(pattern.match("aaa"));
        assertFalse(pattern.match("abc"));
        assertTrue(pattern.match(""));
    }

    @Test
    public void testIntegratedSemantics() throws Exception {
        Pattern pattern =
                new Repeat(
                        new Choose(
                                new Concatenate(
                                        new Literal("a"),
                                        new Literal(("b"))),
                                new Literal("a")));

        assertThat(pattern.toString(), is("(ab|a)*"));

        assertTrue(pattern.match("ab"));
        assertTrue(pattern.match("ababab"));
        assertTrue(pattern.match("abaaab"));
        assertTrue(pattern.match("aaabaa"));
        assertTrue(pattern.match("aaabab"));

        assertFalse(pattern.match("abba"));
    }
}