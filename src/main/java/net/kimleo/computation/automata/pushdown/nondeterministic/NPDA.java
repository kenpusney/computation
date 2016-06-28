package net.kimleo.computation.automata.pushdown.nondeterministic;

import net.kimleo.computation.automata.pushdown.PDAConfiguration;

import java.util.List;
import java.util.Set;

public class NPDA<T> {
    private Set<PDAConfiguration<T>> currentConfigurations;
    private List<T> acceptStates;
    private NPDARulebook<T> rulebook;

    public NPDA(Set<PDAConfiguration<T>> currentConfigurations, List<T> acceptStates, NPDARulebook<T> rulebook) {
        this.currentConfigurations = currentConfigurations;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public boolean isAcceptable() {
        return currentConfigurations().stream().
                anyMatch(config -> acceptStates.contains(config.state()));
    }

    public void input(Character input) {
        currentConfigurations =
                rulebook.nextConfigurations(currentConfigurations(), input);
    }

    public void input(String input) {
        for (char c : input.toCharArray()) {
            input(c);
        }
    }

    public Set<PDAConfiguration<T>> currentConfigurations() {
        return rulebook.follow_free_moves(currentConfigurations);
    }
}
