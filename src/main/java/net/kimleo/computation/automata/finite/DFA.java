package net.kimleo.computation.automata.finite;

import java.util.Set;

public class DFA<T> {
    private T currentState;
    private final Set<T> acceptableStates;
    private final DFARulebook<T> rulebook;

    public DFA(T currentState, Set<T> acceptableStates, DFARulebook<T> rulebook) {
        this.currentState = currentState;
        this.acceptableStates = acceptableStates;
        this.rulebook = rulebook;
    }

    public boolean isAcceptable() {
        return acceptableStates.contains(currentState);
    }

    public void input(Character input) {
        currentState = rulebook.ruleFor(currentState, input).follow();
    }

    public void input(String input) {
        input.chars().forEach((code) -> input((char)code));
    }
}
