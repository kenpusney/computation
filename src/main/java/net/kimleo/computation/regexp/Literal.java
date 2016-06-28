package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.FARule;
import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import net.kimleo.computation.automata.finite.nondeterministic.NFARulebook;

import static net.kimleo.commons.$C.listOf;
import static net.kimleo.commons.$C.setOf;

public class Literal implements Pattern {

    final String repr;

    public Literal(String repr) {
        this.repr = repr;
    }

    @Override
    public int precedence() {
        return 3;
    }

    @Override
    public String toString() {
        return repr;
    }

    @Override
    public NFADesign<Object> toNfaDesign() {
        Object start = new Object();
        Object accepted = new Object();

        NFARulebook<Object> rulebook =
                new NFARulebook<>(listOf(new FARule<>(start, repr.charAt(0), accepted)));
        return new NFADesign<>(start, setOf(accepted), rulebook);
    }
}
