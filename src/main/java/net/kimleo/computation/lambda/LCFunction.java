package net.kimleo.computation.lambda;

import java.util.Objects;

public class LCFunction implements LCTerm, LCCallable {
    private String param;
    private LCTerm body;

    public LCFunction(String param, LCTerm body) {
        this.param = param;
        this.body = body;
    }

    @Override
    public String toString() {
        return param + " => " + body;
    }

    @Override
    public LCTerm replace(String name, LCTerm replacement) {
        if (Objects.equals(param, name)) {
            return this;
        } else {
            return new LCFunction(param, body.replace(name, replacement));
        }
    }

    @Override
    public LCTerm call(LCTerm argument) {
        return body.replace(param, argument);
    }
}
