package net.kimleo.computation.semantics.ast.statement;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.Reducible;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleStatement;

public class Assign<T> implements ReducibleStatement {

    private final String name;
    private final Valuable<T> value;

    public Assign(String name, Valuable<T> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Statement reduce(Environment environment) {
        if (value instanceof Reducible) {
            return new Assign<>(name, ((ReducibleExpression<T>) value).reduce(environment));
        } else {
            environment.add(name, value);
            return new NoOp();
        }
    }

    @Override
    public String toString() {
        return String.format("%s := %s", name, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Statement evaluate(Environment environment) {
        environment.add(name,((Valuable<T>) value.evaluate(environment)));
        return new NoOp();
    }

    @Override
    public String toJS() {
        return String.format("(e) => (e['%s'] = ((%s)(e)), e)", name, value.toJS());
    }
}
