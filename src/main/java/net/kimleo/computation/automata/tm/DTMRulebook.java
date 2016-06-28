package net.kimleo.computation.automata.tm;

import java.util.List;

public class DTMRulebook<T, C> {
    private List<TMRule<T, C>> rules;

    public DTMRulebook(List<TMRule<T, C>> rules) {
        this.rules = rules;
    }

    public TMConfig<T, C> nextConfiguration(TMConfig<T, C> config) {
        return rule_for(config).follow(config);
    }

    private TMRule<T, C> rule_for(TMConfig<T, C> config) {
        return rules.stream().filter(rule -> rule.isAppliedTo(config)).findFirst().orElse(null);
    }

    public boolean appliesTo(TMConfig<T, C> config) {
        return rule_for(config) != null;
    }
}
