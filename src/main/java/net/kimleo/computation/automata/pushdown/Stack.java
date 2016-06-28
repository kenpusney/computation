package net.kimleo.computation.automata.pushdown;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static net.kimleo.commons.$C.listOf;

public class Stack<T>{
    private final List<T> content;

    public Stack(List<T> element) {
        content = element;
    }

    public Stack<T> push(T element) {
        List<T> list = listOf(element);
        list.addAll(content);
        return new Stack<>(list);
    }

    public T top() {
        return content.get(0);
    }

    public Stack<T> pop() {
        return new Stack<>(content.stream().skip(1).collect(toList()));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (T element: content) {
            if (first) {
                first = false;
                builder.append(String.format("(%s)", element));
                continue;
            }
            builder.append(element);
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stack<?> stack = (Stack<?>) o;

        return content != null ? content.equals(stack.content) : stack.content == null;

    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }
}
