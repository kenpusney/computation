package net.kimleo.computation.semantics.ast.var;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleExpression;

public class Variable<T> implements ReducibleExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Valuable<T> reduce(Environment environment) {
        return environment.get(name);
    }

    @Override
    public T value() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Statement evaluate(Environment environment) {
        return environment.get(name);
    }

    @Override
    public String toJS() {
        return String.format("(e) => (e['%s'])", name);
    }
}
