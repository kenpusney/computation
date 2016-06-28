package net.kimleo.computation.semantics.ast.var;

import net.kimleo.computation.semantics.ast.concept.Valuable;

import java.util.Map;

public class Environment {
    private final Map<String, Valuable> map;

    public Environment(Map<String, Valuable> map) {
        this.map = map;
    }

    @SuppressWarnings("unchecked")
    public <T> Valuable<T> get(String name) {
        return (Valuable<T>) map.get(name);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public <T> void add(String name, Valuable<T> valuable) {
        map.put(name, valuable);
    }
}
