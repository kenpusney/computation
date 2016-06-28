package net.kimleo.computation.semantics.ast.statement;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.Reducible;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleStatement;

public class Sequence implements ReducibleStatement{

    private final Statement first;
    private final Statement second;

    public Sequence(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Statement reduce(Environment environment) {
        if (first instanceof Reducible) {
            return new Sequence(((ReducibleStatement) first).reduce(environment), second);
        } else {
            return second;
        }
    }

    @Override
    public String toString() {
        return String.format("%s; %s", first, second);
    }

    @Override
    public Statement evaluate(Environment environment) {
        first.evaluate(environment);
        return second.evaluate(environment);
    }

    @Override
    public String toJS() {
        return String.format("(e) => ((%s)((%s)(e)), e)", second.toJS(), first.toJS());
    }
}
