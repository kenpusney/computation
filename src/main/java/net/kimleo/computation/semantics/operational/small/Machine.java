package net.kimleo.computation.semantics.operational.small;

import net.kimleo.computation.semantics.ast.concept.Statement;
import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.operational.small.concept.Reducible;

public class Machine {

    private Statement statement;
    private final Environment environment;

    public Machine(Statement statement, Environment environment) {
        this.statement = statement;
        this.environment = environment;
    }


    public void run() {
        while (statement instanceof Reducible) {
            System.out.println(String.format("%s => %s", statement, environment));
            statement = (Statement) ((Reducible) statement).reduce(environment);
        }
        System.out.println(String.format("%s => %s", statement, environment));
    }

}
