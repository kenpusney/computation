package net.kimleo.computation.semantics.ast.concept;

import net.kimleo.computation.semantics.ast.var.Environment;
import net.kimleo.computation.semantics.denotational.Representable;
import net.kimleo.computation.semantics.operational.big.concept.Evaluable;

public interface Statement extends Evaluable<Statement>, Representable{
    @Override
    Statement evaluate(Environment environment);
}
