package net.kimleo.computation.automata.tm;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static net.kimleo.commons.$C.listOf;

public class Tape<C> {
    private List<C> left;
    private C middle;
    private List<C> right;
    private C blank;

    public Tape(List<C> left, C middle, List<C> right, C blank) {
        this.left = left;
        this.middle = middle;
        this.right = right;
        this.blank = blank;
    }

    public String toString() {
        return String.format("%s(%s)%s",
                left.stream().map(Object::toString).collect(joining()),
                middle,
                right.stream().map(Object::toString).collect(joining()));
    }

    public Tape<C> write(C character) {
        return new Tape<>(left, character, right, blank);
    }

    public Tape<C> moveLeft() {
        List<C> right = listOf(this.middle);
        right.addAll(this.right);
        List<C> left;
        C middle;
        if (this.left.isEmpty()) {
            middle = blank;
            left = listOf();
        } else {
            left = this.left.subList(0, this.left.size() - 1);
            middle = this.left.isEmpty() ? blank : this.left.get(this.left.size() - 1);
        }
        return new Tape<>(left, middle, right, blank);
    }

    public Tape<C> moveRight() {
        List<C> left = listOf();
        left.addAll(this.left);
        left.add(this.middle);
        C middle;
        List<C> right;
        if (this.right.isEmpty()) {
            middle = blank;
            right = listOf();
        } else {
            middle = this.right.get(0);
            right = this.right.subList(1, this.right.size());
        }

        return new Tape<>(left, middle, right, blank);
    }

    public C middle() {
        return middle;
    }
}
