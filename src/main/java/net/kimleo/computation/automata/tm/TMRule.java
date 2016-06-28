package net.kimleo.computation.automata.tm;

public class TMRule<T, C> {
    private final T state;
    private final C character;
    private final T nextState;
    private final C writeCharacter;
    private final Direction direction;

    public TMRule(T state, C character, T nextState, C writeCharacter, Direction direction) {
        this.state = state;
        this.character = character;
        this.nextState = nextState;
        this.writeCharacter = writeCharacter;
        this.direction = direction;
    }

    public TMConfig<T, C> follow(TMConfig<T, C> configuration) {
        return new TMConfig<>(nextTape(configuration), state);
    }

    private Tape<C> nextTape(TMConfig<T, C> configuration) {
        Tape<C> writtenTape = configuration.tape().write(writeCharacter);

        switch (direction) {
            case Left:
                return writtenTape.moveLeft();
            case Right:
                return writtenTape.moveRight();
        }
        return writtenTape;
    }

    public boolean isAppliedTo(TMConfig<T, C> config) {
        return state.equals(config.state()) && character.equals(config.tape().middle());
    }
}
