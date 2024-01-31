package edu.trinity.cpsc215f23.tree;

import java.util.Iterator;

/**
 * An interface for a tree where nodes can have an arbitrary number of
 * children.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface Tree<E> extends Iterable<E> {

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    Position<E> root();

    /**
     * Returns the Position of position's parent (or null if position is root).
     *
     * @param position a valid Position within the tree
     * @return Position of position's parent (or null if position is root)
     * @throws IllegalArgumentException if position is not a valid Position
     */
    Position<E> parent(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns an iterable collection of the Positions representing position's children.
     *
     * @param position a valid Position within the tree
     * @return iterable collection of the Positions of position's children
     * @throws IllegalArgumentException if position is not a valid Position
     */
    Iterable<Position<E>> children(Position<E> position)
            throws IllegalArgumentException;

    /**
     * Returns the number of children of the given position.
     *
     * @param position a valid Position within the tree
     * @return number of children of the given position
     * @throws IllegalArgumentException if position is not a valid Position
     */
    int numChildren(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns true if the given position has one or more children.
     *
     * @param position a valid Position within the tree
     * @return true if position has at least one child, false otherwise
     * @throws IllegalArgumentException if position is not a valid Position
     */
    boolean isInternal(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns true if the given position does not have any children.
     *
     * @param position a valid Position within the tree
     * @return true if position has zero children, false otherwise
     * @throws IllegalArgumentException if position is not a valid Position
     */
    boolean isExternal(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns true if the given position represents the root of the tree.
     *
     * @param position a valid Position within the tree
     * @return true if position is the root of the tree, false otherwise
     * @throws IllegalArgumentException if position is not a valid Position
     */
    boolean isRoot(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    int size();

    /**
     * Tests whether the tree is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns an iterator of the elements stored in the tree.
     *
     * @return iterator of the tree's elements
     */
    Iterator<E> iterator();

    /**
     * Returns an iterable collection of the positions of the tree.
     *
     * @return iterable collection of the tree's positions
     */
    Iterable<Position<E>> positions();

}
