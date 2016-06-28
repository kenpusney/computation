package net.kimleo.computation.semantics.ast.operator;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.type.Num;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;

public class Add implements ReducibleExpression<Integer> {

    private final Valuable<Integer> left;
    private final Valuable<Integer> right;

    public Add(Valuable<Integer> left, Valuable<Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer value() {
        return right.value() + left.value();
    }

    @Override
    public String toString() {
        return String.format("%s + %s", left, right);
    }

    @Override
    public Valuable<Integer> reduce(Environment environment) {
        if (left instanceof ReducibleExpression) {
            return new Add(((ReducibleExpression<Integer>) left).reduce(environment), right);
        } else if (right instanceof ReducibleExpression) {
            return new Add(left, ((ReducibleExpression<Integer>) right).reduce(environment));
        } else {
            return new Num(left.value() + right.value());
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public Statement evaluate(Environment environment) {
        Valuable<Integer> left = (Valuable<Integer>) this.left.evaluate(environment);
        Valuable<Integer> right = (Valuable<Integer>) this.right.evaluate(environment);

        return new Num(left.value() + right.value());
    }

    @Override
    public String toJS() {
        return String.format("(e) => ((%s)(e) + (%s)(e))", left.toJS(), right.toJS());
    }
}
