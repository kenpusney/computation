package net.kimleo.computation.semantics.operational.big.concept;

import net.kimleo.computation.semantics.ast.var.Environment;

public interface Evaluable<T> {
    T evaluate(Environment environment);
}
