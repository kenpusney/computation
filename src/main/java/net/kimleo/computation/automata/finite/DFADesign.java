package net.kimleo.computation.automata.finite;

import java.util.Set;

public class DFADesign<T> {
    private final T currentState;
    private final Set<T> acceptedStates;
    private final DFARulebook<T> rulebook;

    public DFADesign(T currentState, Set<T> acceptedStates, DFARulebook<T> rulebook) {
        this.currentState = currentState;
        this.acceptedStates = acceptedStates;
        this.rulebook = rulebook;
    }

    public DFA<T> toDFA() {
        return new DFA<>(currentState, acceptedStates, rulebook);
    }

    public boolean accepts(String input) {
        DFA dfa = toDFA();
        dfa.input(input);
        return dfa.isAcceptable();
    }
}
