package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.FARule;
import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import net.kimleo.computation.automata.finite.nondeterministic.NFARulebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Choose implements Pattern {

    private final Pattern first;
    private final Pattern second;

    public Choose(Pattern first, Pattern second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int precedence() {
        return 0;
    }

    @Override
    public String toString() {
        return first.bracket(precedence()) + "|" + second.bracket(precedence());
    }

    @Override
    public NFADesign<Object> toNfaDesign() {

        NFADesign<Object> firstNfa = first.toNfaDesign();
        NFADesign<Object> secondNfa = second.toNfaDesign();

        Object startState = new Object();

        Set<Object> acceptedStates = new HashSet<>();
        acceptedStates.addAll(firstNfa.acceptStates());
        acceptedStates.addAll(secondNfa.acceptStates());

        ArrayList<FARule<Object>> rules = new ArrayList<>();
        rules.addAll(firstNfa.rulebook().rules());
        rules.addAll(secondNfa.rulebook().rules());

        rules.add(new FARule<>(startState, null, firstNfa.startState()));
        rules.add(new FARule<>(startState, null, secondNfa.startState()));

        return new NFADesign<>(startState, acceptedStates, new NFARulebook<>(rules));
    }
}
