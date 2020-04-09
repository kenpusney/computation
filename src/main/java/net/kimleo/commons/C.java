package net.kimleo.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class C {

    @SafeVarargs
    public static <T> Set<T> setOf(T... ts) {
        return new HashSet<>(Arrays.asList(ts));
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    public static <T> List<T> reverse(Collection<T> collection) {
        List<T> elems = new ArrayList<>(collection);
        Collections.reverse(elems);
        return elems;
    }
}