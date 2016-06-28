package net.kimleo.computation.semantics.operational.big;

import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.operator.Add;
import net.kimleo.computation.semantics.ast.operator.LessThan;
import net.kimleo.computation.semantics.ast.operator.Multiply;
import net.kimleo.computation.semantics.ast.statement.Assign;
import net.kimleo.computation.semantics.ast.statement.Sequence;
import net.kimleo.computation.semantics.ast.statement.While;
import net.kimleo.computation.semantics.ast.type.Bool;
import net.kimleo.computation.semantics.ast.type.Num;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.ast.var.Variable;
import net.kimleo.computation.semantics.operational.small.Machine;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MachineTest {
    private static final Environment DEFAULT_ENVIRONMENT = new Environment(new HashMap<>());

    @Test
    @SuppressWarnings("unchecked")
    public void testEvaluateBasicValue() throws Exception {
        Valuable<Integer> num = (Valuable<Integer>) new Num(1).evaluate(DEFAULT_ENVIRONMENT);

        assertThat(num.value(), is(1));

        Valuable<Boolean> bool = (Valuable<Boolean>) new Bool(false).evaluate(DEFAULT_ENVIRONMENT);

        assertThat(bool.value(), is(false));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEvaluateExpression() throws Exception {
        Valuable<Integer> addition = (Valuable<Integer>) new Add(new Num(1), new Num(2)).evaluate(DEFAULT_ENVIRONMENT);
        assertThat(addition.value(), is(3));
    }

    @Test
    public void testStatement() throws Exception {
        Environment environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});
        While statement = new While(new LessThan(new Variable<>("x"), new Num(15)), new Sequence(
                new Assign<>("x", new Add(new Variable<>("x"), new Num(1))),
                new Assign<>("x", new Multiply(new Variable<>("x"), new Variable<>("x")))
        ));
        new Machine(statement, environment).run();

        environment = new Environment(new HashMap<String, Valuable>() {{
            put("x", new Num(1));
        }});

        statement.evaluate(environment);
        assertThat(environment.get("x").value(), is(25));
    }
}