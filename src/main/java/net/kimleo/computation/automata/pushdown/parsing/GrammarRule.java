package net.kimleo.computation.automata.pushdown.parsing;

public class GrammarRule {
    public final char token;
    public final String pattern;

    public GrammarRule(char token, String pattern) {
        this.token = token;
        this.pattern = pattern;
    }
}
