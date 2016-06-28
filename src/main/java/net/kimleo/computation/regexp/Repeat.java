package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.FARule;
import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import net.kimleo.computation.automata.finite.nondeterministic.NFARulebook;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Repeat implements Pattern {

    private Pattern pattern;

    public Repeat(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public int precedence() {
        return 2;
    }

    @Override
    public NFADesign<Object> toNfaDesign() {

        NFADesign<Object> nfa = pattern.toNfaDesign();
        Object startState = new Object();

        HashSet<Object> acceptedStates = new HashSet<>();
        acceptedStates.addAll(nfa.acceptStates());
        acceptedStates.add(startState);

        List<FARule<Object>> rules = new ArrayList<>();
        rules.addAll(nfa.rulebook().rules());
        rules.addAll(nfa.acceptStates().stream()
                .map(state -> new FARule<>(state, null, nfa.startState()))
                .collect(toList()));
        rules.add(new FARule<>(startState, null, nfa.startState()));

        return new NFADesign<>(startState, acceptedStates, new NFARulebook<>(rules));
    }

    @Override
    public String toString() {
        return pattern.bracket(precedence()) + "*";
    }
}
