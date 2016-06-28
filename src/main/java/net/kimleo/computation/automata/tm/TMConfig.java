package net.kimleo.computation.automata.tm;

public class TMConfig<T, C> {
    private Tape<C> tape;
    private T state;

    public TMConfig(Tape<C> tape, T state) {
        this.tape = tape;
        this.state = state;
    }

    public Tape<C> tape() {
        return tape;
    }

    public T state() {
        return state;
    }
}
