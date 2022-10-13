/* Name: Liza Zakharova
 * ID: A16907410
 * Email: ezakharova@ucsd.edu
 * Sources used: None
 * 
 * This file contains a class MyBSTIterator which contains a nested 
 * abstract class MyBSTNodeIterator, and nested clases MyBSTKeyIterator 
 * and MyBSTValueIterator, which function as iterators for the whole tree,
 * nodes, keys, and values, respectively.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class contains three nested classes, MyBSTNodeIterator (abstract), 
 * MyBSTKeyIterator, and MyBSTValueIterator, and is responsible for iterating
 * through a binary search tree. This class extends Comparable and MyBST.
 */
public class MyBSTIterator<K extends Comparable<K>, V> extends MyBST<K, V> {

    /**
     * This is an abstract nested class responsible for iterating through
     * a tree and keeping track of the lastVisited (current) node and
     * next node. This class implements the Iterator interface.
     */
    abstract class MyBSTNodeIterator<T> implements Iterator<T> {
        MyBSTNode<K, V> next;
        MyBSTNode<K, V> lastVisited;

        /**
         * Constructor that initializes the node iterator
         *
         * @param first The initial node that next points
         */
        MyBSTNodeIterator(MyBSTNode<K, V> first) {
            next = first;
            lastVisited = null;
        }

        /**
         * This method is used for determining if the next pointer in the
         * iterator points to null.
         *
         * @return If next is null based on the current position of iterator
         */
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Advances iterator to the next node by changing next to its 
         * successor and changing lastVisited to current next. If 
         * next is null, throws exception because can't iterate up.
         * @return lastVisited node in the tree
         */
        MyBSTNode<K, V> nextNode() {
            if (next == null) {
                throw new NoSuchElementException();
            }
            MyBSTNode<K,V> successor = next.successor();
            this.lastVisited = this.next;
            this.next = successor; 
            return lastVisited;
        }

        /**
         * TODO: add inline comments for this method to demonstrate your
         *   understanding of this method.
         *
         * This method removes the last visited node from the tree.
         */
        public void remove() {
            // checks if lastVisited is null because cannot remove null node
            if (lastVisited == null) {
                // throws IllegalState exception
                throw new IllegalStateException();
            }
            // checks if lastVisited has children
            if (lastVisited.getRight() != null &&
                    lastVisited.getLeft() != null) {
                /* sets next to lastVisited so children are maintained
                and tree structure is maintained*/
                next = lastVisited;
            }
            // removes lastVisited node using MyBST remove method
            MyBSTIterator.this.remove(lastVisited.getKey());
            // sets lastVisited to null so you can't remove twice in a row
            lastVisited = null;
        }
    }

    /**
     * BST Key iterator class that extends the node iterator.
     */
    class MyBSTKeyIterator extends MyBSTNodeIterator<K> {

        /**
         * Calls the constructor method from the node iterator
         * 
         * @param first the initial value that next points to
         */
        MyBSTKeyIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node key
         *
         * @return K the next key
         */
        public K next() {
            return super.nextNode().getKey();
        }
    }

    /**
     * BST value iterator class that extends the node iterator.
     */
    class MyBSTValueIterator extends MyBSTNodeIterator<V> {

        /**
         * Call the constructor method from node iterator
         *
         * @param first The initial value that next points
         */
        MyBSTValueIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node value
         *
         * @return V the next value
         */
        public V next() {
            return super.nextNode().getValue();
        }
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTKeyIterator getKeyIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTKeyIterator(curr);
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTValueIterator getValueIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTValueIterator(curr);
    }
}