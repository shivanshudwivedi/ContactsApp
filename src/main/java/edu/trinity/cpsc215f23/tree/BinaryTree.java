package edu.trinity.cpsc215f23.tree;

/**
 * An interface for a binary tree, in which each node has at most two
 * children.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public interface BinaryTree<E> extends Tree<E> {

    /**
     * Returns the Position of position's left child (or null if no child exists).
     *
     * @param position a valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if position is not a valid Position
     */
    Position<E> left(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns the Position of position's right child (or null if no child exists).
     *
     * @param position a valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if position is not a valid Position
     */
    Position<E> right(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns the Position of position's sibling (or null if no sibling exists).
     *
     * @param position a valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if position is not a valid Position
     */
    Position<E> sibling(Position<E> position) throws IllegalArgumentException;

}
