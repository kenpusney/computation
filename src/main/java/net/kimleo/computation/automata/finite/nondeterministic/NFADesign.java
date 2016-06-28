package net.kimleo.computation.automata.finite.nondeterministic;

import java.util.HashSet;
import java.util.Set;

public class NFADesign<T> {
    final T startState;
    final Set<T> acceptStates;
    final NFARulebook<T> rulebook;

    public NFADesign(T startState, Set<T> acceptStates, NFARulebook<T> rulebook) {
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public NFA<T> toNFA() {
        return toNFA(new HashSet<T>(){{ add(startState); }});
    }

    public NFA<T> toNFA(Set<T> current_states) {
        return new NFA<>(current_states, acceptStates, rulebook);

    }

    public boolean accepts(String input) {
        NFA nfa = toNFA();
        nfa.input(input);
        return nfa.isAcceptable();
    }

    public T startState() {
        return startState;
    }

    public Set<T> acceptStates() {
        return acceptStates;
    }

    public NFARulebook<T> rulebook() {
        return rulebook;
    }
}
