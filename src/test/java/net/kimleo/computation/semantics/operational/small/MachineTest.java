package net.kimleo.computation.semantics.operational.small;

import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.operator.Add;
import net.kimleo.computation.semantics.ast.operator.LessThan;
import net.kimleo.computation.semantics.ast.operator.Multiply;
import net.kimleo.computation.semantics.ast.statement.*;
import net.kimleo.computation.semantics.ast.type.Num;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.ast.var.Variable;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class MachineTest {

    public static final Environment DEFAULT_ENVIRONMENT = new Environment(new HashMap<>());

    @Test
    public void testAddNumbers() throws Exception {
        Valuable value = new Add(
                new Multiply(new Num(1), new Num(3)),
                new Num(2));

        assertThat(value.toString(), is("1 * 3 + 2"));
    }

    @Test
    public void testReduce() throws Exception {
        Valuable value = new Add(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4)));
        Environment environment = DEFAULT_ENVIRONMENT;

        assertThat(value.toString(), is("1 * 2 + 3 * 4"));

        value = ((ReducibleExpression)value).reduce(environment);
        assertThat(value.toString(), is("2 + 3 * 4"));

        value = ((ReducibleExpression) value).reduce(environment);
        assertThat(value.toString(), is("2 + 12"));

        value = ((ReducibleExpression) value).reduce(environment);
        assertThat(value.toString(), is("14"));

        assertFalse(value instanceof ReducibleExpression);
    }

    @Test
    public void testMachineRun() throws Exception {
        Machine machine = new Machine(new Add(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4))), DEFAULT_ENVIRONMENT);

        machine.run();
    }

    @Test
    public void testBool() throws Exception {
        Machine machine = new Machine(new LessThan(
                new Multiply(new Num(1), new Num(2)),
                new Multiply(new Num(3), new Num(4))), DEFAULT_ENVIRONMENT);

        machine.run();
    }

    @Test
    public void testRunWithVariable() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
            put("y", new Num(3));
        }});

        Machine machine = new Machine(new LessThan(
                new Multiply(new Variable<>("x"), new Num(2)),
                new Multiply(new Num(3), new Variable<>("y"))), environment);

        machine.run();
    }

    @Test
    public void testAssignment() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});
        Machine machine = new Machine(new Assign<>("x", new Add(new Variable<>("x"), new Num(1))), environment);

        machine.run();
    }

    @Test
    public void testIfStatement() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});

        Machine machine = new Machine(
                new If(
                        new LessThan(new Variable<>("x"), new Num(15)),
                        new Assign<>("x", new Num(15)),
                        new NoOp()),
                environment);

        machine.run();
    }

    @Test
    public void testSequenceStatement() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});

        Machine machine = new Machine(
                new Sequence(
                        new Assign<>("x", new Add(new Variable<>("x"), new Num(1))),
                        new Sequence(new If(
                                new LessThan(new Variable<>("x"), new Num(15)),
                                new Assign<>("x", new Num(15)),
                                new NoOp()), new Assign<>("y", new Variable<>("x")))), environment);

        machine.run();
    }

    @Test
    public void testWhileStatement() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});

        Machine machine = new Machine(
                new While(new LessThan(new Variable<>("x"), new Num(5)), new Assign<>("x", new Multiply(new Variable<>("x"), new Num(3)))), environment
        );

        machine.run();
    }
}