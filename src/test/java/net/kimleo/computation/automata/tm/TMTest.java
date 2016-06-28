package net.kimleo.computation.automata.tm;

import org.junit.Test;

import static net.kimleo.commons.$C.listOf;
import static net.kimleo.commons.$C.setOf;
import static net.kimleo.computation.automata.tm.Direction.Left;
import static net.kimleo.computation.automata.tm.Direction.Right;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TMTest {
    @Test
    public void testTape() throws Exception {
        Tape<Character> tape = new Tape<>(listOf('a', 'b', 'c'), 'd', listOf('e', 'f', 'g'), '_');

        assertThat(tape.toString(), is("abc(d)efg"));
        tape = tape.moveLeft();
        assertThat(tape.toString(), is("ab(c)defg"));
        tape = tape.moveRight().moveRight();
        assertThat(tape.toString(), is("abcd(e)fg"));
    }

    @Test
    public void testDTMRulebook() throws Exception {
        DTMRulebook<Integer, Character> rulebook = new DTMRulebook<>(
                listOf(
                        new TMRule<>(1, '0', 2, '1', Right),
                        new TMRule<>(1, '1', 1, '0', Left),
                        new TMRule<>(1, '_', 2, '1', Right),
                        new TMRule<>(2, '0', 2, '0', Right),
                        new TMRule<>(2, '1', 2, '1', Right),
                        new TMRule<>(2, '_', 3, '_', Left)
                ));

        Tape<Character> tape = new Tape<>(listOf('1', '2', '1'), '1', listOf(), '_');

        TMConfig<Integer, Character> config = new TMConfig<>(tape, 1);

        DTM<Integer, Character> dtm = new DTM<>(config, setOf(3), rulebook);

        dtm.run();

        assertThat(dtm.currentConfiguration().tape().toString(), is("1(2)00"));

    }

    @Test
    public void testInternalStorage() throws Exception {
        DTMRulebook<Integer, Character> rulebook = new DTMRulebook<>(listOf(
                new TMRule<>(1, 'a', 2, 'a', Right),
                new TMRule<>(1, 'b', 3, 'b', Right),
                new TMRule<>(1, 'c', 4, 'c', Right),

                new TMRule<>(2, 'a', 2, 'a', Right),
                new TMRule<>(2, 'b', 2, 'b', Right),
                new TMRule<>(2, 'c', 2, 'c', Right),
                new TMRule<>(2, '_', 5, 'a', Right),

                new TMRule<>(3, 'a', 3, 'a', Right),
                new TMRule<>(3, 'b', 3, 'b', Right),
                new TMRule<>(3, 'c', 3, 'c', Right),
                new TMRule<>(3, '_', 5, 'b', Right),

                new TMRule<>(4, 'a', 4, 'a', Right),
                new TMRule<>(4, 'b', 4, 'b', Right),
                new TMRule<>(4, 'c', 4, 'c', Right),
                new TMRule<>(4, '_', 5, 'c', Right)
        ));

        Tape<Character> tape = new Tape<>(listOf(), 'b', listOf('c', 'b', 'c', 'a'), '_');

        assertThat(tape.toString(), is("(b)cbca"));
        DTM<Integer, Character> dtm = new DTM<>(new TMConfig<>(tape, 1), setOf(5), rulebook);

        dtm.run();

        assertThat(dtm.currentConfiguration().tape().toString(), is("bcbca(_)"));

    }

    @Test
    public void testSubRoutine() throws Exception {


    }
}