package net.kimleo.computation.automata.pushdown.parsing;

import net.kimleo.commons.function.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static net.kimleo.commons.$C.listOf;

public class LexicalAnalyzer {
    public static final List<GrammarRule> GRAMMAR =
            listOf( new GrammarRule('i', "if"),
                    new GrammarRule('e', "else"),
                    new GrammarRule('w', "while"),
                    new GrammarRule('d', "do-nothing"),
                    new GrammarRule('(', "\\("),
                    new GrammarRule(')', "\\)"),
                    new GrammarRule('{', "\\{"),
                    new GrammarRule('}', "\\}"),
                    new GrammarRule(';', ";"),
                    new GrammarRule('=', "="),
                    new GrammarRule('+', "\\+"),
                    new GrammarRule('*', "\\*"),
                    new GrammarRule('<', "<"),
                    new GrammarRule('n', "[0-9]+"),
                    new GrammarRule('b', "true|false"),
                    new GrammarRule('v', "[a-z]+"));

    private String source;

    public LexicalAnalyzer(String source) {
        this.source = source;
    }

    List<Character> analyze() throws MatchException {
        List<Character> tokens = new ArrayList<>();
        while (moreToken()) {
            tokens.add(next_token());
        }
        return tokens;
    }

    private Character next_token() throws MatchException {
        Pair<GrammarRule, Matcher> result = ruleMatch(source.trim());
        if (result != null) {
            source = afterMatch(result.second());
            return result.first().token;
        }
        return null;
    }

    private String afterMatch(Matcher result) {
        return result.replaceAll("").trim();
    }

    private Pair<GrammarRule, Matcher> ruleMatch(String source) throws MatchException {
        Stream<Pair<GrammarRule, Matcher>> matches = GRAMMAR.stream().map(rule -> matchAtBeginning(rule, source));
        return matches
                .filter(match -> match.second().find())
                .sorted((m1, m2) -> m1.second().end() - m2.second().end())
                .findFirst().orElseThrow(() -> new MatchException("unexpected"));
    }

    private Pair<GrammarRule, Matcher> matchAtBeginning(GrammarRule pattern, String source) {
        Pattern regex = Pattern.compile("^"+pattern.pattern);

        return new Pair<>(pattern, regex.matcher(source));
    }

    private boolean moreToken() {
        return !source.isEmpty();
    }



}
