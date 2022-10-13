/* Name: Liza Zakharova
 * ID: A16907410
 * Email: ezakharova@ucsd.edu
 * Sources used: Zybooks 8.5 used to help write search, insert, and remove
 * methods
 * 
 * This file contains a class MyBST which uses generics and extends
 * the Comparable class. MyBST also contains a nested MyBSTNode static class.
 */

import java.util.ArrayList;

/**
 * This class represents a Binary Search Tree, containing variables
 * representing the root and size, as well as methods, that perform different
 * functions of a binary search tree. This class also contains a static
 * nested MyBSTNode class which respresents the individual nodes of 
 * the binary search tree.
 */
public class MyBST<K extends Comparable<K>,V>{
    MyBSTNode<K,V> root = null;
    int size = 0;

    /**
     * Returns the number of nodes currently in the tree
     */
    public int size(){
        return size;
    }

    /**
     * Inserts a new node into the tree if key doesn't exist and returns
     * null. If key exists, replaces node with that key with new value
     * and returns value replaced. Throws exception if key was null.
     * @param key key of node being inserted/replaced 
     * @param value value of node being inserted
     * @return old value of replaced node, or null
     */
    public V insert(K key, V value){
        if (key == null) {
            throw new NullPointerException();
        }
        // if tree empty, sets new node to root (parent will be null)
        if (root == null) {
            root = new MyBSTNode<K,V>(key, value, null);
            size++;
            return null;
        }
        // calls recursive insert method
        return insertHelper(root, key, value);
    }

    /**
     * Helper method to the insert() method. Inserts node at the 
     * valid place in the binary search tree. Returns the value
     * replaced by the new value, or null if a new node was added.
     * @param parent parent node of node being checked to insert
     * @param key key of node being inserted/replaced
     * @param value value of node being inserted
     * @return value replaced by the inserted node, or null
     */
    private V insertHelper(MyBSTNode<K,V> parent, K key, V value) {
        // if parent value equals value, replaces parent value
        if (parent.getKey().equals(key)) {
            V oldVal = parent.getValue();
            parent.setValue(value);
            return oldVal;
        }
        MyBSTNode<K,V> node = new MyBSTNode<K,V>(key, value, parent);
        // checks which subtree to insert in
        if (key.compareTo(parent.getKey()) < 0) {
            // inserts new node if key doesn't exist
            if (parent.getLeft() == null) {
                parent.setLeft(node); 
                this.size++;
                return null;
            }
            // continues search for key or leaf parent in left subtree
            return insertHelper(parent.getLeft(), key, value);
        } else {
            // inserts new node if key doesn't exist
            if (parent.getRight() == null) {
                parent.setRight(node); 
                this.size++;
                return null;
            }
            // continues search for key or leaf parent in right subtree
            return insertHelper(parent.getRight(), key, value);
        }
    }

    /**
     * Returns value of node with specified key. If key is null or not
     * in the tree, returns null.
     * @param key key of node being searched for 
     * @return value of node with specified key
     */
    public V search(K key){
        if (key == null) {
            return null;
        }
        // calls recursive search method
        return searchHelper(root, key);
    }

    /**
     * Recusive method for search(). Returns value of node with
     * specified key, or null if it does not exist.
     * @param node node subtree where node with key being searched for
     * @param key key of node being searched for 
     * @return
     */
    private V searchHelper(MyBSTNode<K,V> node, K key) {
        if (node == null) {
            return null;
        }
        // checks if key is found
        if (node.getKey().equals(key)) {
            return node.getValue();
        }
        // determines which subtree of node to search based on key
        if (node.getKey().compareTo(key) > 0) {
            return searchHelper(node.getLeft(), key);
        } else {
            return searchHelper(node.getRight(), key);
        }
    }

    /**
     * Removes node with specified key, or null if key not in tree or 
     * if key is null.
     * @param key key of node being removed
     * @return value of node being removed
     */
    public V remove(K key){
        if (key == null) {
            return null;
        }
        if (root == null) {
            return null;
        }
        // calls recursive remove method
        return removeHelper(root, root.getParent(), key);
    }

    /**
     * Recursive method for remove(). Removes node at specified index and 
     * updates binary search tree accordingly. Returns value of the node
     * being removed, or null if key not found.
     * @param node node being checked against the key
     * @param parent parent of node being checked
     * @param key key of node to be removed
     * @return value of the node that was removed
     */
    private V removeHelper(MyBSTNode<K,V> node, MyBSTNode<K,V> parent, K key) {
        if (node == null) {
            return null;
        }
        // checks if node key equal to key
        if (node.getKey().equals(key)) {
            V nodeValue = node.getValue();

            // if node has two children
            if (node.getLeft() != null && node.getRight() != null) {
                MyBSTNode<K,V> suc = node.successor();
                // recursively removes successor node from tree
                removeHelper(suc, suc.getParent(), suc.getKey());
                // replaces key and value of node with successor's key/value
                node.setKey(suc.getKey());
                node.setValue(suc.getValue());
            } 
            // if root node that has less than 2 children
            else if (node.equals(root)) {
                // figures out which node to become root
                if (node.getLeft() != null) {
                    this.root = node.getLeft();
                } else {
                    this.root = node.getRight();
                }
                this.size--;
            }
            // if node has left child only (not root)
            else if (node.getLeft() != null) {
                // checks which child of parent is to be removed
                if (parent.getLeft() != null && 
                        parent.getLeft().equals(node)) {
                    // sets parent left child to left child of removed node
                    parent.setLeft(node.getLeft());
                    parent.getLeft().setParent(parent);
                } else {
                    // sets parent's right child to left child of removed node
                    parent.setRight(node.getLeft());
                    parent.getRight().setParent(parent);
                }
                this.size--;
            }
            // if internal node with right child or leaf
            else {
                if (parent.getLeft() != null && 
                        parent.getLeft().equals(node)) {
                    // sets parent's left child to right child of removed node
                    parent.setLeft(node.getRight());
                    if (parent.getLeft() != null) {
                        parent.getLeft().setParent(parent);
                    }
                } else {
                    // sets parent right child to right child of removed node
                    parent.setRight(node.getRight());
                    if (parent.getRight() != null) {
                        parent.getRight().setParent(parent);
                    }
                }
                this.size--;
            }
            return nodeValue;
        } else {
             // if node not found, searches left or right subtree to remove
            if (key.compareTo(node.getKey()) < 0) {
                return removeHelper(node.getLeft(), node, key);
            } else {
                return removeHelper(node.getRight(), node, key);
            }
        }
    }

    /**
     * Returns ArrayList of the in-order traversal of the binary
     * search tree (smallest to largest key), or empty list if tree
     * has no nodes.
     * @return ArrayList of list of nodes in tree in (in-order) order.
     */
    public ArrayList<MyBSTNode<K, V>> inorder(){
        ArrayList<MyBSTNode<K,V>> orderedList = new ArrayList<>();
        if (root == null) {
            return orderedList;
        }
        MyBSTNode<K,V> curr = root;
        // finds smallest node in tree
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        // adds smallest node to list
        orderedList.add(curr);
        // iterates through successor of each node and adds to list
        while (curr.successor() != null) {
            curr = curr.successor();
            orderedList.add(curr);
        }
        return orderedList;
    }

    /**
     * This static class contains instance variables, fields, 
     * and methods meant to simulate the function of node in binary tree,
     * where it has 2 possibly children and a parent, as well as a key 
     * and value. This class is also nested inside the MyBST class.
     */
    static class MyBSTNode<K,V>{
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K,V> parent;
        private MyBSTNode<K,V> left = null;
        private MyBSTNode<K,V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         * @param key the key the MyBSTNode<K,V> will
         * @param value the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent; 
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey(){
            return key;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue(){
            return value;
        }

        /**
         * Return the parent
         * @return the parent
         */
        public MyBSTNode<K,V> getParent(){
            return parent;
        }

        /**
         * Return the left child 
         * @return left child
         */
        public MyBSTNode<K,V> getLeft(){
            return left;
        }

        /**
         * Return the right child 
         * @return right child
         */
        public MyBSTNode<K,V> getRight(){
            return right;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         * @param newKey the key to be stored
         */
        public void setKey(K newKey){
            this.key = newKey;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         * @param newValue the data to be stored
         */
        public void setValue(V newValue){
            this.value = newValue;
        }

        /**
         * Set the parent
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K,V> newParent){
            this.parent = newParent;
        }

        /**
         * Set the left child
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K,V> newLeft){
            this.left = newLeft;
        }

        /**
         * Set the right child
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K,V> newRight){
            this.right = newRight;
        }

        /**
         * TODO: add inline comments for this method to demonstrate your
         *   understanding of this method. The predecessor can be implemented
         *   in a similar way.
         *
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor(){
            /* if node contains right child, smallest node in the right
            child's subtree will be the successor*/
            if(this.getRight() != null){
                // sets curr to right child to look in left subtree
                MyBSTNode<K,V> curr = this.getRight();
                // goes through left children until reaches successor
                while(curr.getLeft() != null){
                    curr = curr.getLeft();
                }
                return curr;
            }
            else{
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                // iterates up the tree until curr is parent's left child
                while(parent != null && curr == parent.getRight()){
                    curr = parent;
                    parent = parent.getParent();
                }
                /* returns parent of child who's right subtree contains
                the initial node (one we're looking for the successor of)*/
                return parent;
            }
        }

        /**
         * Returns the in order predecessor of the the current node.
         * @return the predecessor node of the current BST node
         */
        public MyBSTNode<K, V> predecessor(){
            // checks if contains left child
            if (this.getLeft() != null) {
                // sets curr to left child to look in its right subtree
                MyBSTNode<K,V> curr = this.getLeft();
                // iterates through right children until end (successor)
                while (curr.getRight() != null) {
                    curr = curr.getRight();
                }
                return curr;
            } else {
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                // iterates up the tree until curr is parent's right child
                while (parent != null && curr == parent.getLeft()) {
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /** This method compares if two node objects are equal.
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj){
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K,V> comp = (MyBSTNode<K,V>)obj;
            
            return( (this.getKey() == null ? comp.getKey() == null : 
                this.getKey().equals(comp.getKey())) 
                && (this.getValue() == null ? comp.getValue() == null : 
                this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         * @return "Key:Value" that represents the node object
         */
        public String toString(){
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}