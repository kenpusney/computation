package net.kimleo.computation.automata.finite;

import java.util.List;

public class DFARulebook<T> {
    private final List<FARule<T>> rules;

    public DFARulebook(List<FARule<T>> rules) {
        this.rules = rules;
    }

    public FARule<T> ruleFor(T state, Character input) {
        return rules.stream()
                .filter(rule -> rule.isAppliedTo(state, input))
                .findFirst()
                .orElse(null);
    }

    public T nextState(T state, Character input) {
        FARule<T> rule = ruleFor(state, input);
        if (rule != null) {
            return rule.follow();
        }
        return null;
    }
}
