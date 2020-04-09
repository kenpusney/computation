package net.kimleo.computation.automata.pushdown.parsing;

import net.kimleo.computation.automata.pushdown.PDARule;
import net.kimleo.computation.automata.pushdown.nondeterministic.NPDADesign;
import net.kimleo.computation.automata.pushdown.nondeterministic.NPDARulebook;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.kimleo.commons.C.listOf;

public class SimpleParser {

    private final NPDARulebook<Integer> rulebook;
    private final List<PDARule<Integer>> rules;
    private final NPDADesign<Integer> npdaDesign;

    public SimpleParser() {
        PDARule<Integer> startRule = new PDARule<>(1, null, 2, '$', listOf('S', '$'));

        List<PDARule<Integer>> parserRule = listOf(
                // <stmt> ::= <while> | <assign>
                new PDARule<>(2, null, 2, 'S', listOf('W')),
                new PDARule<>(2, null, 2, 'S', listOf('A')),

                // <while> := w ( <expr> ) { <stmt> }
                new PDARule<>(2, null, 2, 'W', listOf('w', '(', 'E', ')', '{', 'S', '}')),

                // <assign> := v = <expr>
                new PDARule<>(2, null, 2, 'A', listOf('v', '=', 'E')),

                // <expr> := <less-than>
                new PDARule<>(2, null, 2, 'E', listOf('L')),

                // <less-than> := <multiply> '<' <less-than> | <multiply>
                new PDARule<>(2, null, 2, 'L', listOf('M', '<', 'L')),
                new PDARule<>(2, null, 2, 'L', listOf('M')),

                // <multiply> := <term> * <multiply> | <term>
                new PDARule<>(2, null, 2, 'M', listOf('T', '*', 'M')),
                new PDARule<>(2, null, 2, 'M', listOf('T')),

                // <term> := n | v
                new PDARule<>(2, null, 2, 'T', listOf('n')),
                new PDARule<>(2, null, 2, 'T', listOf('v')));

        List<PDARule<Integer>> tokenRule = LexicalAnalyzer.GRAMMAR.stream().map(rule ->
                new PDARule<>(2, rule.token, 2, rule.token, listOf())).collect(toList());

        PDARule<Integer> stopRule = new PDARule<>(2, null, 3, '$', listOf('$'));

        rules = listOf(startRule, stopRule);
        rules.addAll(parserRule);
        rules.addAll(tokenRule);

        rulebook = new NPDARulebook<>(rules);

        npdaDesign = new NPDADesign<>(1, '$', listOf(3), rulebook);
    }

    public boolean accepts(String program) {
        try {
            List<Character> tokens = new LexicalAnalyzer(program).analyze();
            String tokenString = tokens.stream().map(Object::toString).collect(joining());
            return npdaDesign.accepts(tokenString);
        } catch (MatchException e) {
            return false;
        }
    }
}
