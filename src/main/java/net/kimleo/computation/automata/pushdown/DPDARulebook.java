package net.kimleo.computation.automata.pushdown;

import java.util.List;

public class DPDARulebook<T> {
    private List<PDARule<T>> rules;

    public DPDARulebook(List<PDARule<T>> rules) {
        this.rules = rules;
    }

    public PDAConfiguration<T> nextConfig(PDAConfiguration<T> config, Character input) {
        PDARule<T> tpdaRule = ruleFor(config, input);
        return tpdaRule.follow(config);
    }

    private PDARule<T> ruleFor(PDAConfiguration<T> config, Character input) {
        return rules.stream().filter(rule -> rule.appliedTo(config, input)).findFirst().orElse(null);
    }

    public boolean isAppliedTo(PDAConfiguration<T> config, Character input) {
        return ruleFor(config, input) != null;
    }

    public PDAConfiguration<T> followFreeMoves(PDAConfiguration<T> configuration) {
        if (isAppliedTo(configuration, null)) {
            return followFreeMoves(nextConfig(configuration, null));
        } else {
            return configuration;
        }
    }
}
