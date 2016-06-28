package net.kimleo.computation.automata.finite;

import java.util.Objects;

public class FARule<T> {
    private final T state;
    private final Character input;
    private final T nextState;

    public FARule(T state, Character input, T nextState) {
        this.state = state;
        this.input = input;
        this.nextState = nextState;
    }

    public T follow() {
        return nextState;
    }

    public boolean isAppliedTo(T state, Character input) {
        return Objects.equals(this.state, state) && this.input == input;
    }

    public Character input() {
        return input;
    }
}
