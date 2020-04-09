package net.kimleo.computation.automata.pushdown;

import org.junit.Test;

import static net.kimleo.commons.C.listOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void testStack() throws Exception {
        Stack<Object> stack = new Stack<>(listOf(1, 2, 3));

        assertThat(stack.top(), is(1));
        assertThat(stack.push(5).top(), is(5));

        assertThat(stack.push(2).push(1).pop().top(), is(2));

        assertThat(stack.toString(), is("(1)23"));
    }
}