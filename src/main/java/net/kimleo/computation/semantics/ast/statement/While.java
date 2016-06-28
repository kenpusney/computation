package net.kimleo.computation.semantics.ast.statement;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.ReducibleStatement;


public class While implements ReducibleStatement {
    private final Valuable<Boolean> condition;
    private final Statement body;

    public While(Valuable<Boolean> condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while ("+condition+") { "+ body + " }";
    }

    @Override
    public Statement reduce(Environment environment) {
        return new If(condition, new Sequence(body, this), new NoOp());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Statement evaluate(Environment environment) {
        if (((Valuable<Boolean>) condition.evaluate(environment)).value()) {
            body.evaluate(environment);
            return evaluate(environment);
        } else {
            return new NoOp();
        }
    }

    @Override
    public String toJS() {
        return "(e) => { while(("+condition.toJS()+")(e)) { ("+body.toJS()+")(e); } return e; }";
    }
}
