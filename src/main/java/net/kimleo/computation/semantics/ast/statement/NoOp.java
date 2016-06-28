package net.kimleo.computation.semantics.ast.statement;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.var.Environment;

public class NoOp implements Statement {

    public NoOp() {
    }

    @Override
    public String toString() {
        return "<No-Op>";
    }

    @Override
    public Statement evaluate(Environment environment) {
        return this;
    }

    @Override
    public String toJS() {
        return "(e) => (e)";
    }
}
