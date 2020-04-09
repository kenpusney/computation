package net.kimleo.computation.automata.finite.nondeterministic;

import net.kimleo.commons.Pair;
import net.kimleo.computation.automata.finite.DFADesign;
import net.kimleo.computation.automata.finite.DFARulebook;
import net.kimleo.computation.automata.finite.FARule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static net.kimleo.commons.C.setOf;

public class NFASimulation<T> {
    private NFADesign<T> nfaDesign;

    public NFASimulation(NFADesign<T> nfaDesign) {
        this.nfaDesign = nfaDesign;
    }

    public Set<T> nextState(Set<T> state, Character input) {
        NFA<T> nfa = nfaDesign.toNFA(state);
        nfa.input(input);
        return nfa.currentStates();
    }

    public List<FARule<Set<T>>> ruleFor(Set<T> state) {
        List<Character> alphabet = nfaDesign.rulebook().alphabet();

        return alphabet.stream()
                .map(input -> new FARule<>(state, input, nextState(state, input)))
                .collect(toList());
    }

    public Pair<Set<Set<T>>, List<FARule<Set<T>>>> discoverStatesAndRules(Set<Set<T>> states) {
        List<FARule<Set<T>>> rules = states.stream().flatMap(state -> ruleFor(state).stream()).collect(toList());
        Set<Set<T>> moreStates = rules.stream().map(FARule::follow).collect(toSet());

        if (states.containsAll(moreStates)) {
            return new Pair<>(states, rules);
        } else {
            HashSet<Set<T>> allStates = new HashSet<>();
            allStates.addAll(states);
            allStates.addAll(moreStates);
            return discoverStatesAndRules(allStates);
        }
    }

    public DFADesign<Set<T>> toDFADesign() {
        Set<T> startState = nfaDesign.toNFA().currentStates();
        Pair<Set<Set<T>>, List<FARule<Set<T>>>> discovered = discoverStatesAndRules(setOf(startState));
        Set<Set<T>> states = discovered.first();
        List<FARule<Set<T>>> rules = discovered.second();

        Set<Set<T>> acceptedStates = states.stream().filter(state -> nfaDesign.toNFA(state).isAcceptable()).collect(toSet());

        return new DFADesign<>(startState, acceptedStates, new DFARulebook<>(rules));
    }

}
