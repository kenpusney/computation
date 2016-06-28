package net.kimleo.computation.semantics.denotational;

public interface Representable {
    default String toJS() {
        return String.format("(e) => (%s)", this);
    }
}
