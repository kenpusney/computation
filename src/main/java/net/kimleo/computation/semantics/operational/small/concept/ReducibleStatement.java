package net.kimleo.computation.semantics.operational.small.concept;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.var.Environment;

public interface ReducibleStatement extends Reducible<Statement>, Statement {
    @Override
    Statement reduce(Environment environment);
}
