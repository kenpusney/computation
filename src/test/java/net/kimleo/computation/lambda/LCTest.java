package net.kimleo.computation.lambda;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LCTest {
    @Test
    public void testOne() throws Exception {
        LCFunction one = fn("p",
                fn("x", call(p("p"), p("x"))));

        assertThat(one.toString(), is("p => x => p(x)"));

        LCFunction increment = fn("n", fn("p", fn("x", call(p("p"), call(call(p("n"), p("p")), p("x"))))));

        assertThat(increment.toString(), is("n => p => x => p(n(p)(x))"));

        LCFunction add = fn("m", fn("n", call(call(p("n"), increment), p("m"))));

        assertThat(add.toString(), is("m => n => n(n => p => x => p(n(p)(x)))(m)"));

        LCTerm expr = call(call(add, one), one);

        expr = call(call(expr, p("inc")), p("zero"));

        try {
            while (expr != null && expr instanceof LCReducible) {
                System.out.println(expr);
                expr = ((LCReducible) expr).reduce();
            }
        } catch (NullPointerException ignored) {}
    }

    private LCFunction fn(String param, LCTerm body) {
        return new LCFunction(param, body);
    }

    private LCCall call(LCTerm left, LCTerm right) {
        return new LCCall(left, right);
    }

    private LCVariable p(String p) {
        return new LCVariable(p);
    }

}