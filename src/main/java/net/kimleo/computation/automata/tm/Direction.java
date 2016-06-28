package net.kimleo.computation.automata.tm;

public enum Direction {
    Left(-1),
    Right(1);
    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public int direction() {
        return direction;
    }
}
