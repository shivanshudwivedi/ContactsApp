package edu.trinity.cpsc215f23.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract base class providing some functionality of the Tree interface.
 * The following three methods remain abstract, and must be implemented by a
 * concrete subclass: root, parent, children. Other methods implemented in
 * this class may be overridden to provide a more direct and efficient
 * implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public abstract class AbstractTree<E> implements Tree<E> {

    /**
     * Returns true if Position p has one or more children.
     *
     * @param position a valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position
     */
    public boolean isInternal(Position<E> position) {
        return numChildren(position) > 0;
    }

    /**
     * Returns true if Position p does not have any children.
     *
     * @param position a valid Position within the tree
     * @return true if p has zero children, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position
     */
    public boolean isExternal(Position<E> position) {
        return numChildren(position) == 0;
    }

    /**
     * Returns true if Position p represents the root of the tree.
     *
     * @param position a valid Position within the tree
     * @return true if p is the root of the tree, false otherwise
     */
    public boolean isRoot(Position<E> position) {
        return position == root();
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param position a valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position
     */
    public int numChildren(Position<E> position) {
        int count = 0;
        for (Position<E> child : children(position)) {
            count++;
        }
        return count;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    public int size() {
        int count = 0;
        for (Position<E> position : positions()) {
            count++;
        }
        return count;
    }

    /**
     * Tests whether the tree is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of levels separating Position from the root.
     *
     * @param position a valid Position within the tree
     * @throws IllegalArgumentException if position is not a valid Position
     * @return Returns the depth of the tree
     */
    public int depth(Position<E> position) throws IllegalArgumentException {
        return isRoot(position) ? 0 : 1 + depth(parent(position));
    }

    /**
     * Returns the height of the tree, using depth().
     *
     * @return the height of the tree, using depth().
     */
    private int heightBad() {
        int height = 0;
        for (Position<E> position : positions()) {
            if (isExternal(position)) {
                height = Math.max(height, depth(position));
            }
        }
        return height;
    }

    /**
     * Returns the height of the subtree rooted at Position.
     *
     * @param position a valid Position within the tree
     * @throws IllegalArgumentException if position is not a valid Position
     * @return Returns the height of the tree
     */
    public int height(Position<E> position) throws IllegalArgumentException {
        int height = 0;                          // base case if position is external
        for (Position<E> child : children(position)) {
            height = Math.max(height, 1 + height(child));
        }
        return height;
    }

    /**
     * Returns an iterator of the elements stored in the tree.
     *
     * @return iterator of the tree's elements
     */
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns an iterable collection of the positions of the tree.
     *
     * @return iterable collection of the tree's positions
     */
    public Iterable<Position<E>> positions() {
        return preorder();
    }

    /**
     * Adds positions of the subtree rooted at the given position to the given snapshot
     * using a preorder traversal
     *
     * @param position Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preorderSubtree(Position<E> position, List<Position<E>> snapshot) {
        snapshot.add(position);
        for (Position<E> c : children(position)) {
            preorderSubtree(c, snapshot);
        }
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in
     * preorder.
     *
     * @return iterable collection of the tree's positions in preorder
     */
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            preorderSubtree(root(), snapshot);   // fill the snapshot recursively
        }
        return snapshot;
    }

    /**
     * This nested class adapts the iteration produced by positions() to return elements.
     *
     * @version 1.0.0, 10th December 2023
     * @author Shivanshu Dwivedi
     */
    private class ElementIterator implements Iterator<E> {

        /**
         * Stores the iterator for every position
         */
        final Iterator<Position<E>> posIterator = positions().iterator();

        /**
         * To check if there is element in the next position
         *
         * @return True if the next element is non-null; else false
         */
        public boolean hasNext() {
            return posIterator.hasNext();
        }

        /**
         * It returns the next element
         *
         * @return The next element of the type E
         */
        public E next() {
            return posIterator.next().getElement();
        }

        @Override
        public void remove() {
            posIterator.remove();
        }
    }

}
