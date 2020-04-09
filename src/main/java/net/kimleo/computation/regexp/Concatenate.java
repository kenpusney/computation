package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.FARule;
import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import net.kimleo.computation.automata.finite.nondeterministic.NFARulebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static net.kimleo.commons.C.listOf;

public class Concatenate implements Pattern {

    private final Pattern left;
    private final Pattern right;

    public Concatenate(Pattern left, Pattern right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int precedence() {
        return 1;
    }

    @Override
    public String toString() {
        return listOf(left, right).stream().map(pattern -> pattern.bracket(precedence())).reduce("", (s, s2) -> s+s2);
    }

    @Override
    public NFADesign<Object> toNfaDesign() {

        NFADesign<Object> leftNfa = left.toNfaDesign();
        NFADesign<Object> rightNfa = right.toNfaDesign();

        Object startState = leftNfa.startState();
        Set<Object> acceptStates = rightNfa.acceptStates();

        List<FARule<Object>> rules = new ArrayList<>();
        rules.addAll(leftNfa.rulebook().rules());
        rules.addAll(rightNfa.rulebook().rules());

        rules.addAll(leftNfa.acceptStates().stream().map(
                state -> new FARule<>(state, null, rightNfa.startState())
        ).collect(toList()));

        return new NFADesign<>(startState, acceptStates, new NFARulebook<>(rules));
    }
}
