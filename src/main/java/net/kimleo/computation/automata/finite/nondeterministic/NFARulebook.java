package net.kimleo.computation.automata.finite.nondeterministic;

import net.kimleo.computation.automata.finite.FARule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class NFARulebook<T> {
    private final List<FARule<T>> rules;

    public NFARulebook(List<FARule<T>> rules) {
        this.rules = rules;
    }

    @SafeVarargs
    public final Set<T> nextStates(Character input, T... states) {
        HashSet<T> nexts = new HashSet<>();
        for (T state : states) {
            nexts.addAll(followRulesFor(state, input));
        }
        return nexts;
    }

    public Set<T> nextStates(Character input, Set<T> states) {
        HashSet<T> nexts = new HashSet<>();
        for (T state : states) {
            nexts.addAll(followRulesFor(state, input));
        }
        return nexts;
    }

    public Set<T> followRulesFor(T state, Character input) {
        return ruleFor(state, input).stream().map(FARule<T>::follow).collect(Collectors.toSet());
    }

    public Set<FARule> ruleFor(T state, Character input) {
        return rules.stream()
                .filter(rule ->
                        rule.isAppliedTo(state, input))
                .collect(toSet());
    }

    public Set<T> followFreeMoves(Set<T> states) {
        Set<T> moreStates = nextStates(null, states);
        if (states.containsAll(moreStates)) {
            return states;
        } else {
            moreStates.addAll(states);
            return followFreeMoves(moreStates);
        }
    }


    public List<FARule<T>> rules() {
        return rules;
    }

    public List<Character> alphabet() {
        return rules.stream().map(FARule::input).distinct().collect(Collectors.toList());
    }
}
