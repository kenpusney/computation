package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;

public interface Pattern {
    int precedence();

    default String bracket(int precedence) {
        if (precedence > precedence()) {
            return String.format("(%s)", toString());
        }
        return toString();
    }

    NFADesign<Object> toNfaDesign();

    default boolean match(String input) {
        return toNfaDesign().accepts(input);
    }

}
