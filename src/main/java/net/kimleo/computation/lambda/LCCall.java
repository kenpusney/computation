package net.kimleo.computation.lambda;

public class LCCall implements LCTerm, LCReducible {
    private LCTerm left;
    private LCTerm right;

    public LCCall(LCTerm left, LCTerm right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", left.toString(), right.toString());
    }

    @Override
    public LCTerm replace(String name, LCTerm replacement) {
        return new LCCall(left.replace(name, replacement), right.replace(name, replacement));
    }

    @Override
    public LCTerm reduce() {
        if (left instanceof LCReducible) {
            return new LCCall(((LCReducible) left).reduce(), right);
        } else if (right instanceof LCReducible) {
            return new LCCall(left, ((LCReducible) right).reduce());
        }
        if (left instanceof LCCallable) {
            return ((LCCallable) left).call(right);
        }
        return null;
    }
}
