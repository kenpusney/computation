package net.kimleo.computation.automata.tm;

import java.util.Set;

public class DTM<T, C> {
    private TMConfig<T, C> currentConfiguration;
    private Set<T> acceptedStates;
    private DTMRulebook<T, C> rulebook;

    public DTM(TMConfig<T, C> currentConfiguration, Set<T> acceptedStates, DTMRulebook<T, C> rulebook) {
        this.currentConfiguration = currentConfiguration;
        this.acceptedStates = acceptedStates;
        this.rulebook = rulebook;
    }

    public boolean isAcceptable() {
        return acceptedStates.contains(currentConfiguration.state());
    }

    public void step() {
        currentConfiguration = rulebook.nextConfiguration(currentConfiguration);
    }

    public void run() {
        while(!(isAcceptable() || isStucked())) {
            step();
        }
    }

    private boolean isStucked() {
        return !isAcceptable() && !rulebook.appliesTo(currentConfiguration);
    }

    public TMConfig<T, C> currentConfiguration() {
        return currentConfiguration;
    }
}
