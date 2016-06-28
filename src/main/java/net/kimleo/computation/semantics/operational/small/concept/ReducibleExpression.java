package net.kimleo.computation.semantics.operational.small.concept;

import net.kimleo.computation.semantics.ast.concept.Valuable;
import net.kimleo.computation.semantics.ast.var.Environment;

public interface ReducibleExpression<T> extends Reducible<Valuable<T>>, Valuable<T> {
    @Override
    Valuable<T> reduce(Environment environment);
}
