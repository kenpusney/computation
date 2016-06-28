package net.kimleo.computation.automata.finite.nondeterministic;

import java.util.Set;

public class NFA<T> {
    private Set<T> currentStates;
    private final Set<T> acceptableStates;
    private final NFARulebook<T> rulebook;

    public NFA(Set<T> currentStates, Set<T> acceptableStates, NFARulebook<T> rulebook) {
        this.currentStates = currentStates;
        this.acceptableStates = acceptableStates;
        this.rulebook = rulebook;
    }

    public Set<T> currentStates() {
        return rulebook.followFreeMoves(currentStates);
    }

    public boolean isAcceptable() {
        return currentStates().stream().anyMatch(acceptableStates::contains);
    }

    public void input(Character input) {
        currentStates = rulebook.nextStates(input, currentStates());
    }

    public void input(String input) {
        input.chars().forEach((code) -> input((char)code));
    }
}
