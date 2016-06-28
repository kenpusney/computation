package net.kimleo.computation.semantics.operational.small.concept;

import net.kimleo.computation.semantics.ast.var.Environment;

public interface Reducible<T> {
    T reduce(Environment environment);
}
