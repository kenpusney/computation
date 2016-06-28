package net.kimleo.computation.automata.pushdown;

import java.util.Set;

public class DPDA<T> {
    private PDAConfiguration<T> currentConfiguration;
    private final Set<T> acceptStates;
    private final DPDARulebook<T> rulebook;

    public DPDA(PDAConfiguration<T> currentConfiguration,
                Set<T> acceptStates,
                DPDARulebook<T> rulebook) {
        this.currentConfiguration = currentConfiguration;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public boolean isAcceptable() {
        return acceptStates.contains(currentConfiguration().state());
    }

    public void input(Character character) {
        currentConfiguration = nextConfig(character);
    }

    public void input(String s) {
        for (char c : s.toCharArray()) {
            if (!stucked()) {
                input(c);
            }
        }
    }

    public PDAConfiguration<T> currentConfiguration() {
        return rulebook.followFreeMoves(currentConfiguration);
    }

    public boolean stucked() {
        return currentConfiguration().stucked();
    }

    public PDAConfiguration<T> nextConfig(Character character) {
        if (rulebook.isAppliedTo(currentConfiguration(), character)) {
            return rulebook.nextConfig(currentConfiguration, character);
        } else {
            return currentConfiguration().stuck();
        }
    }

}
