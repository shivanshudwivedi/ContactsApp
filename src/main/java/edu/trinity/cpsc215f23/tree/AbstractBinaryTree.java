package edu.trinity.cpsc215f23.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class providing some functionality of the BinaryTree
 * interface. The following five methods remain abstract, and must be
 * implemented by a concrete subclass: size, root, parent, left, right.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public abstract class AbstractBinaryTree<E>
        extends AbstractTree<E> implements BinaryTree<E> {

    /**
     * Returns the Position of position's sibling (or null if no sibling exists).
     *
     * @param position a valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if position is not a valid Position
     */
    public Position<E> sibling(Position<E> position) {
        Position<E> parent = parent(position);

        // Ensure the position is not the root
        if (parent == null) {
            return null;
        }

        return position == left(parent) ? right(parent) : left(parent); // (right child might be null)
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param position a valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position
     */
    @Override
    public int numChildren(Position<E> position) {
        int count = 0;
        if (left(position) != null) {
            count++;
        }
        if (right(position) != null) {
            count++;
        }
        return count;
    }

    /**
     * Returns an iterable collection of the Positions representing p's
     * children.
     *
     * @param position a valid Position within the tree
     * @return iterable collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position
     */
    public Iterable<Position<E>> children(Position<E> position) {
        List<Position<E>> snapshot = new ArrayList<>(2);
        if (left(position) != null) {
            snapshot.add(left(position));
        }
        if (right(position) != null) {
            snapshot.add(right(position));
        }
        return snapshot;
    }

}
