package net.kimleo.computation.automata.pushdown;

import net.kimleo.commons.C;

import java.util.List;

public class PDARule<T> {
    private T state;
    private Character character;
    private T nextState;
    private Character popCharacter;
    private List<Character> pushCharacters;

    public PDARule(T state, Character character, T nextState, Character popCharacter, List<Character> pushCharacters) {
        this.state = state;
        this.character = character;
        this.nextState = nextState;
        this.popCharacter = popCharacter;
        this.pushCharacters = pushCharacters;
    }

    public boolean appliedTo(PDAConfiguration<T> configuration, Character input) {
        return state.equals(configuration.state())
                && popCharacter.equals(configuration.stack().top())
                && (character != null ? character.equals(input) : (input == null) );
    }

    public Stack<Character> next_stack(PDAConfiguration<T> configuration) {
        Stack<Character> popedStack = configuration.stack().pop();
        List<Character> chars = C.reverse(pushCharacters);
        return chars.stream().reduce(popedStack, Stack::push, (s, s1) -> s );
    }

    public PDAConfiguration<T> follow(PDAConfiguration<T> configuration) {
        return new PDAConfiguration<>(nextState, next_stack(configuration));
    }
}
