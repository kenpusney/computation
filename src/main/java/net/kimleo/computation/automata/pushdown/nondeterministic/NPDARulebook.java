package net.kimleo.computation.automata.pushdown.nondeterministic;

import net.kimleo.computation.automata.pushdown.PDAConfiguration;
import net.kimleo.computation.automata.pushdown.PDARule;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class NPDARulebook<T> {
    private List<PDARule<T>> rules;

    public NPDARulebook(List<PDARule<T>> rules) {
        this.rules = rules;
    }

    Set<PDAConfiguration<T>> nextConfigurations(Set<PDAConfiguration<T>> configs, Character input) {
        return configs.stream().flatMap(config -> follow_rules_for(config, input).stream()).collect(toSet());
    }

    public Set<PDAConfiguration<T>> follow_rules_for(PDAConfiguration<T> config, Character input) {
        return rules_for(config, input).stream().map(rule -> rule.follow(config)).collect(toSet());
    }

    public List<PDARule<T>> rules_for(PDAConfiguration<T> config, Character input) {
        return rules.stream().filter(rule -> rule.appliedTo(config, input)).collect(toList());
    }

    public Set<PDAConfiguration<T>> follow_free_moves(Set<PDAConfiguration<T>> configs) {
        Set<PDAConfiguration<T>> moreConfigs = nextConfigurations(configs, null);

        if (configs.containsAll(moreConfigs)) {
            return configs;
        }
        moreConfigs.addAll(configs);
        return follow_free_moves(moreConfigs);
    }
}
