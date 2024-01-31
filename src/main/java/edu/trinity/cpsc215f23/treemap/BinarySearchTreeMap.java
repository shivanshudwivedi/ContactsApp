package edu.trinity.cpsc215f23.treemap;

import edu.trinity.cpsc215f23.map.Entry;
import edu.trinity.cpsc215f23.map.Map;
import edu.trinity.cpsc215f23.tree.LinkedBinaryTree;
import edu.trinity.cpsc215f23.tree.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Realization of a map by means of a binary search tree.
 *
 * @author Takunari Miyazaki
 * @author Shivanshu Dwivedi
 * @version 1.0.0 9th. 9th December 2023
 */
public class BinarySearchTreeMap<K, V> extends LinkedBinaryTree<Entry<K, V>> implements Map<K, V> {

    /**
     * It is used to compare elements in the class
     */
    protected final Comparator<K> comparator; // comparator

    /**
     * It stores the position where the action has to be performed
     */
    protected Position<Entry<K, V>> actionPos; // a node variable

    /**
     * Creates a BinarySearchTreeMap with a default comparator.
     */
    public BinarySearchTreeMap() {
        comparator = new DefaultComparator<>();
        addRoot(null);
    }

    /**
     * Creates a BinarySearchTreeMap with comparator parameter
     *
     * @param comparator Stores the comparison value of objects in the class
     */
    public BinarySearchTreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
        addRoot(null);
    }

    /**
     * In this collection only internal nodes have entries.
     *
     * @return the number of entries in the map.
     */
    public int size() {
        return ((super.size() - 1) / 2);
    }

    /**
     * Extracts the key of the entry at a given node of the tree.
     *
     * @param position Stores the position whose key is to be extracted
     * @return Returns the key value of the position
     */
    protected K key(Position<Entry<K, V>> position) {
        return position.getElement().getKey();
    }

    /**
     * Extracts the value of the entry at a given node of the tree.
     *
     * @param position Stores the position whose value is to be extracted
     * @return It returns the Value of the position
     */
    protected V value(Position<Entry<K, V>> position) {
        return position.getElement().getValue();
    }

    /**
     * Extracts the entry at a given node of the tree.
     *
     * @param position Stores the position whose entry is to be extracted
     * @return Returns the entry of the specified position
     */
    protected Entry<K, V> entry(Position<Entry<K, V>> position) {
        return position.getElement();
    }

    /**
     * Replaces an entry with a new entry (and reset the entry's location)
     *
     * @param position Stores the position whose entry is to be replaced
     * @param entry    Stores the new values that replace old one
     * @return Returns the previous value at the position
     */
    protected V replaceEntry(Position<Entry<K, V>> position, Entry<K, V> entry) {
        ((BSTEntry<K, V>) entry).position = position;
        return set(position, entry).getValue();
    }

    /**
     * Checks whether a given key is valid.
     *
     * @param key Stores the key to be checked
     */
    protected void checkKey(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
    }

    /**
     * Checks whether a given entry is valid.
     *
     * @param entry Checks if the entry values are valid
     */
    protected void checkEntry(Entry<K, V> entry) throws IllegalArgumentException {
        if (!(entry instanceof BSTEntry)) {
            throw new IllegalArgumentException("Entry is invalid, expecting type BSTEntry.");
        }
    }

    /**
     * Auxiliary method for inserting an entry at an external node. Inserts a given entry at a given external position,
     * expanding the external node to be internal with empty external children, and then returns the inserted entry.
     * (page 11.1.2)
     *
     * @param position The external position
     * @param entry    The entry to add to the external
     * @return The entry added
     */
    protected Entry<K, V> insertAtExternal(Position<Entry<K, V>> position, Entry<K, V> entry) {
        set(position, entry);
        addLeft(position, null);
        addRight(position, null);
        return entry;
    }

    /**
     * Auxiliary method for removing an external node and its parent. Removes a given external node and its
     * parent, replacing external's parent with external's sibling.
     *
     * @param external The position to remove.
     */
    protected void removeExternal(Position<Entry<K, V>> external) {
        if (!isExternal(external)) {
            throw new IllegalArgumentException("Given position is not external");
        } else if (isRoot(external)) {
            remove(external);
        } else {
            Position<Entry<K, V>> parent = parent(external);
            remove(external);
            remove(parent);
        }
    }

    /**
     * Search the tree, starting at the root.
     *
     * @param key The node to search for.
     * @return The found node position
     */
    private Position<Entry<K, V>> treeSearch(K key) {
        return treeSearch(key, root());
    }


    /**
     * An auxiliary method used by get, put, and remove.
     *
     * @param key      The node to search for.
     * @param position The starting tree position
     * @return The found node position
     */
    protected Position<Entry<K, V>> treeSearch(K key, Position<Entry<K, V>> position) {
        if (isExternal(position)) {
            return position;
        }
        K curKey = key(position);
        int comp = comparator.compare(key, curKey);
        if (comp < 0) {
            return treeSearch(key, left(position));
        } else if (comp > 0) {
            return treeSearch(key, right(position));
        }
        return position;
    }

    /**
     * Returns a value whose associated key is k.
     *
     * @param key The key whose value is to be found
     * @return Returns the value of the associated key
     */
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> currentPos = treeSearch(key);
        actionPos = currentPos;

        return isInternal(currentPos) ? value(currentPos) : null;
    }

    /**
     * Inserts an entry with a given key and value v into the map, returning
     * the old value whose associated key is key if it exists.
     *
     * @param key   : The key whose value is to be replaced
     * @param value : The new value that replaces the old one
     * @return : Returns the old value associated to the key
     */
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> insPos = treeSearch(key);
        BSTEntry<K, V> entry = new BSTEntry<>(key, value, insPos);
        actionPos = insPos;
        if (isExternal(insPos)) {
            insertAtExternal(insPos, entry).getValue();
            return null;
        }

        return replaceEntry(insPos, entry);
    }

    /**
     * Removes from the map the entry whose key is k, returning the value of
     * the removed entry.
     *
     * @param key : The key whose value is to be removed
     * @return : Returns the removed value
     */
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> remPos = treeSearch(key);
        if (isExternal(remPos)) {
            return null;
        }
        Entry<K, V> toReturn = entry(remPos);
        if (isExternal(left(remPos))) {
            remPos = left(remPos);
        } else if (isExternal(right(remPos))) {
            remPos = right(remPos);
        } else {
            Position<Entry<K, V>> swapPos = remPos;
            remPos = left(swapPos);
            do {
                remPos = right(remPos);
            } while (isInternal(remPos));
            replaceEntry(swapPos, parent(remPos).getElement());
        }
        actionPos = sibling(remPos);
        removeExternal(remPos);

        return toReturn.getValue();
    }

    /**
     * Returns an iterable collection of the keys of all entries stored in the
     * map.
     *
     * @return : Returns an iterable collection of key set
     */
    public Iterable<K> keySet() {
        List<K> keyList = new ArrayList<>();
        for (Entry<K, V> entryValue : entrySet()) {
            keyList.add(entryValue.getKey());
        }
        return keyList;
    }

    /**
     * Returns an iterable collection of the values of all entries stored in
     * the map.
     *
     * @return : Returns an iterable collection of value set
     */
    public Iterable<V> values() {
        List<V> valueList = new ArrayList<>();
        for (Entry<K, V> entryValue : entrySet()) {
            valueList.add(entryValue.getValue());
        }
        return valueList;
    }

    /**
     * Returns an iterable collection of all entries stored in the map. The sentinels are excluded.
     *
     * @return : Returns an iterable collection of entries
     */
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> entryValue = new ArrayList<>(size);
        if (!isEmpty()) {
            inorderAddEntries(root(), entryValue);
        }
        return entryValue;
    }

    /**
     * This method traverses a tree in a tree inorder and stores the value as a list
     *
     * @param pos         The position from where the inorder traversal is to be done
     * @param entriesList The list to which inorder traversal is added
     */
    protected void inorderAddEntries(Position<Entry<K, V>> pos, List<Entry<K, V>> entriesList) {
        if (left(pos) != null) {
            inorderAddEntries(left(pos), entriesList);
        }
        if (isInternal(pos)) {
            entriesList.add(entry(pos));
        }
        if (right(pos) != null) {
            inorderAddEntries(right(pos), entriesList);
        }
    }

    /**
     * This class converts a tree into a String
     *
     * @return Returns a string representation of a tree
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry<K, V> entry : entrySet()) {
            sb.append("(");
            sb.append(entry.getKey());
            sb.append(", ");
            sb.append(entry.getValue());
            sb.append(")");
            sb.append("\n");
        }
        sb.substring(0, sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Nested class for location-aware binary search tree entries
     *
     * @author Shivanshu Dwivedi
     * @version 1.0.0, 9th December 2023
     */
    protected static class BSTEntry<K, V> implements Entry<K, V> {
        /**
         * Stores the key of the node
         */
        protected final K key;
        /**
         * Stores the value of the node
         */
        protected final V value;
        /**
         * Stores the position of the node
         */
        protected Position<Entry<K, V>> position;

        /**
         * Creates an instance of the class BSTEntry
         *
         * @param key      : The key value of a node
         * @param value    : The value of the node
         * @param position : Stores the position of the node
         */
        BSTEntry(K key, V value, Position<Entry<K, V>> position) {
            this.key = key;
            this.value = value;
            this.position = position;
        }

        /**
         * This method is used to access the key of the node
         *
         * @return : Returns the key value of the node
         */
        public K getKey() {
            return key;
        }

        /**
         * This method is used to access the value of the node
         *
         * @return : Returns the value of the node
         */
        public V getValue() {
            return value;
        }

        /**
         * This method is used to access the position of the node
         *
         * @return : Returns the position of the node
         */
        public Position<Entry<K, V>> position() {
            return position;
        }
    }
}
