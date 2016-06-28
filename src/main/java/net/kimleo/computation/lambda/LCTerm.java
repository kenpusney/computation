package net.kimleo.computation.lambda;

public interface LCTerm {
    LCTerm replace(String name, LCTerm replacement);
}
