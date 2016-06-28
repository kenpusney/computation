package net.kimleo.computation.semantics.denotational;

import net.kimleo.computation.semantics.ast.operator.Add;
import net.kimleo.computation.semantics.ast.operator.LessThan;
import net.kimleo.computation.semantics.ast.operator.Multiply;
import net.kimleo.computation.semantics.ast.statement.*;
import net.kimleo.computation.semantics.ast.type.Num;
import net.kimleo.computation.semantics.ast.var.Variable;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MachineTest {
    @Test
    public void testValue() throws Exception {
        String js = new Num(1).toJS();

        assertThat(js, is("(e) => (1)"));
    }

    @Test
    public void testVariable() throws Exception {
        String js = new Variable<>("x").toJS();

        assertThat(js, is("(e) => (e['x'])"));
    }

    @Test
    public void testOperators() throws Exception {
        String js = new Add(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4))).toJS();

        assertThat(js,
                is("(e) => (((e) => (((e) => (1))(e) * ((e) => (2))(e)))(e) + ((e) => (((e) => (3))(e) * ((e) => (4))(e)))(e))"));
    }

    @Test
    public void testAssign() throws Exception {
        String js = new Assign<>("x", new Num(1)).toJS();

        assertThat(js,
                is("(e) => (e['x'] = (((e) => (1))(e)), e)"));
    }

    @Test
    public void testIf() throws Exception {
        String js = new If(new Variable<>("x"), new Assign<>("x", new Num(0)), new NoOp()).toJS();
        assertThat(js,
                is("(e) => ((((e) => (e['x']))(e) ? ((e) => (e['x'] = (((e) => (0))(e)), e))(e) : ((e) => (e))()), e)"));
    }

    @Test
    public void testSequence() throws Exception {
        String js = new Sequence(
                new Assign<>("x", new Num(1)),
                new Assign<>("x", new Add(new Variable<>("x"), new Num(1)))).toJS();

        assertThat(js,
                is("(e) => (((e) => (e['x'] = (((e) => (((e) => (e['x']))(e) + ((e) => (1))(e)))(e)), e))(((e) => (e['x'] = (((e) => (1))(e)), e))(e)), e)"));
    }

    @Test
    public void testWhile() throws Exception {
        String js = new While(
                new LessThan(new Variable<>("x"), new Num(5)),
                new Assign<>("x", new Add(new Variable<>("x"), new Num(1)))).toJS();

        assertThat(js,
                is("(e) => { while(((e) => (((e) => (e['x']))(e) < ((e) => (5))(e)))(e)) { ((e) => (e['x'] = (((e) => (((e) => (e['x']))(e) + ((e) => (1))(e)))(e)), e))(e); } return e; }"));
    }
}