package net.kimleo.computation.regexp;

import net.kimleo.computation.automata.finite.nondeterministic.NFADesign;
import net.kimleo.computation.automata.finite.nondeterministic.NFARulebook;

import java.util.HashSet;
import java.util.LinkedList;

public class Empty implements Pattern {
    @Override
    public int precedence() {
        return 3;
    }

    @Override
    public NFADesign<Object> toNfaDesign() {
        NFARulebook<Object> rulebook = new NFARulebook<>(new LinkedList<>());

        Object state = new Object();
        return new NFADesign<>(state, new HashSet<Object>(){{ add(state); }}, rulebook);
    }

    @Override
    public String toString() {
        return "";
    }


}
