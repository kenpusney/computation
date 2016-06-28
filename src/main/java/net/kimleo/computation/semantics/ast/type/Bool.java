package net.kimleo.computation.semantics.ast.type;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;

public class Bool implements Valuable<Boolean> {
    private final boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s", value);
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
