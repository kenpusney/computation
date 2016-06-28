package net.kimleo.computation.semantics.ast.operator;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.type.Bool;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;

public class LessThan implements ReducibleExpression<Boolean> {
    private final Valuable<Integer> left;
    private final Valuable<Integer> right;

    public LessThan(Valuable<Integer> left, Valuable<Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Valuable<Boolean> reduce(Environment environment) {
        if (left instanceof ReducibleExpression) {
            return new LessThan(((ReducibleExpression<Integer>) left).reduce(environment), right);
        } else if (right instanceof ReducibleExpression) {
            return new LessThan(left, ((ReducibleExpression<Integer>) right).reduce(environment));
        } else {
            return new Bool(left.value() < right.value());
        }
    }

    @Override
    public Boolean value() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s < %s", left, right);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Statement evaluate(Environment environment) {
        Valuable<Integer> left = (Valuable<Integer>) this.left.evaluate(environment);
        Valuable<Integer> right = (Valuable<Integer>) this.right.evaluate(environment);

        return new Bool(left.value() < right.value());
    }

    @Override
    public String toJS() {
        return String.format("(e) => ((%s)(e) < (%s)(e))", left.toJS(), right.toJS());
    }
}
