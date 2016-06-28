package net.kimleo.computation.lambda;

import java.util.Objects;

public class LCVariable implements LCTerm {
    private String name;

    public LCVariable(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public LCTerm replace(String name, LCTerm replacement) {
        if (Objects.equals(this.name, name)) {
            return replacement;
        } else {
            return this;
        }
    }
}
