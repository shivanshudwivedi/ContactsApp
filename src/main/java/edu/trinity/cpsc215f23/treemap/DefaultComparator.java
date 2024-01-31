package edu.trinity.cpsc215f23.treemap;

import java.util.Comparator;

/**
 * Comparator based on the compareTo method of a Comparable element type.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class DefaultComparator<E> implements Comparator<E> {
    /**
     * Compares two elements.
     *
     * @return a negative integer if a is less than b, zero if a equals b, or
     * a positive integer if a is greater than b.
     */
    @SuppressWarnings("unchecked")
    public int compare(E a, E b) throws ClassCastException {
        return ((Comparable<E>) a).compareTo(b);
    }
}
