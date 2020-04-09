package net.kimleo.computation.automata.pushdown;

import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PDARuleTest {
    @Test
    public void testPDA() throws Exception {
        PDARule<Integer> rule = new PDARule<>(1, '(', 2, '$', listOf('b', '$'));

        PDAConfiguration<Integer> configuration = new PDAConfiguration<>(1, new Stack<>(listOf('$')));

        assertThat(rule.appliedTo(configuration, '('), is(true));

        rule.follow(configuration);
    }
}