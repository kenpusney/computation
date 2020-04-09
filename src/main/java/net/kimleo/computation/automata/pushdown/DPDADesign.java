package net.kimleo.computation.automata.pushdown;

import java.util.Set;

import static net.kimleo.commons.C.listOf;

public class DPDADesign<T> {
    private T startState;
    private Character bottomCharacter;
    private Set<T> acceptStates;
    private DPDARulebook<T> rulebook;

    public DPDADesign(T startState, Character bottomCharacter, Set<T> acceptStates, DPDARulebook<T> rulebook) {
        this.startState = startState;
        this.bottomCharacter = bottomCharacter;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public boolean accepts(String input) {
        DPDA<T> dpda = toDpda();
        dpda.input(input);
        return dpda.isAcceptable();
    }

    public DPDA<T> toDpda() {
        Stack<Character> stack = new Stack<>(listOf(bottomCharacter));
        PDAConfiguration<T> config = new PDAConfiguration<>(startState, stack);
        return new DPDA<>(config, acceptStates, rulebook);
    }
}
