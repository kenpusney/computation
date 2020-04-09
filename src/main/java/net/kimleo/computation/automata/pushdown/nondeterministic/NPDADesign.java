package net.kimleo.computation.automata.pushdown.nondeterministic;

import net.kimleo.computation.automata.pushdown.PDAConfiguration;
import net.kimleo.computation.automata.pushdown.Stack;

import java.util.List;

import static net.kimleo.commons.C.listOf;
import static net.kimleo.commons.C.setOf;

public class NPDADesign<T> {
    private T startState;
    private Character bottomCharacter;
    private List<T> acceptStates;
    private NPDARulebook<T> rulebook;

    public NPDADesign(T startState, Character bottomCharacter, List<T> acceptStates, NPDARulebook<T> rulebook) {
        this.startState = startState;
        this.bottomCharacter = bottomCharacter;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public boolean accepts(String input) {
        NPDA<T> npda = toNpda();
        npda.input(input);
        return npda.isAcceptable();
    }

    private NPDA<T> toNpda() {
        Stack<Character> startStack = new Stack<>(listOf(bottomCharacter));
        PDAConfiguration<T> startConfig = new PDAConfiguration<>(startState, startStack);
        return new NPDA<>(setOf(startConfig), acceptStates, rulebook);
    }
}
