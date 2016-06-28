package net.kimleo.computation.semantics.ast.statement;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.Reducible;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleStatement;

public class If implements ReducibleStatement {
    private final Valuable<Boolean> condition;
    private final Statement consequence;
    private final Statement alternative;

    public If(Valuable<Boolean> condition, Statement consequence, Statement alternative) {
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    @Override
    public Statement reduce(Environment environment) {
        if (condition instanceof Reducible) {
            return new If(((ReducibleExpression<Boolean>) condition).reduce(environment), consequence, alternative);
        } else {
            if (condition.value()) {
                return consequence;
            } else {
                return alternative;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("if (%s) { %s } else { %s }", condition, consequence, alternative);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Statement evaluate(Environment environment) {
        if (((Valuable<Boolean>)condition.evaluate(environment)).value()) {
            return consequence.evaluate(environment);
        } else {
            return alternative.evaluate(environment);
        }
    }

    @Override
    public String toJS() {
        return String.format("(e) => (((%s)(e) ? (%s)(e) : (%s)()), e)", condition.toJS(), consequence.toJS(), alternative.toJS());
    }
}
