package net.kimleo.computation.semantics.ast.type;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;

public class Num implements Valuable<Integer> {
    private final int value;

    public Num(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    @Override
    public Statement evaluate(Environment environment) {
        return this;
    }

    @Override
    public String toJS() {
        return String.format("(e) => (%s)", this);
    }
}
