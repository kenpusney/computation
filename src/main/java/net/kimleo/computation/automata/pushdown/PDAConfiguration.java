package net.kimleo.computation.automata.pushdown;

public class PDAConfiguration<T> {

    private static final Object STUCK = new Object();

    private T state;
    private Stack<Character> stack;

    public PDAConfiguration(T state, Stack<Character> stack) {
        this.state = state;
        this.stack = stack;
    }

    public T state() {
        return state;
    }

    public Stack<Character> stack() {
        return stack;
    }

    @SuppressWarnings("unchecked")
    public PDAConfiguration<T> stuck() {
        return new PDAConfiguration<>((T)STUCK, stack);
    }

    public boolean stucked() {
        return state.equals(STUCK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PDAConfiguration<?> that = (PDAConfiguration<?>) o;

        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        return stack != null ? stack.equals(that.stack) : that.stack == null;

    }

    @Override
    public int hashCode() {
        int result = state != null ? state.hashCode() : 0;
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }
}
