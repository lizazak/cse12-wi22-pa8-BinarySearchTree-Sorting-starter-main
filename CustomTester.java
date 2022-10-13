/* Name: Liza Zakharova
 * ID: A16907410
 * Email: ezakharova@ucsd.edu
 * Sources used: None
 * 
 * This file contains a class CustomTester responsible for testing methods
 * in the MyBST, MyBSTIterator, and MyCalendar classes.
 */

// import statements
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * This class contains JUnit tests responsible for testing edge cases/cases
 * not in the PublicTester class for methods in the MyBST, MyBSTIterator, 
 * and MyCalendar classes.
 */
public class CustomTester {
    MyBST<Integer, Integer> tree;
    boolean thrown;
    MyBST.MyBSTNode<Integer, Integer> root;

    /**
     * This setup creates a binary search tree like the one below, to be used
     * in the following tests. It also creates a boolean thrown for 
     * the try-catch statements.
     *          8
     *       /     \
     *      5       10
     *    /  \     /  \
     *   2    7   9   16
     *       /
     *      6
     */
    @Before
    public void setup() {
        thrown = false;
        tree = new MyBST<>();
        this.root = new MyBST.MyBSTNode(8, 1, null);
        tree.root = root;
        // set level 1
        MyBST.MyBSTNode<Integer, Integer> five = 
            new MyBST.MyBSTNode(5, 2, root);
        MyBST.MyBSTNode<Integer, Integer> ten = 
            new MyBST.MyBSTNode(10, 3, root);
        root.setLeft(five);
        root.setRight(ten);
        // set level 2
        MyBST.MyBSTNode<Integer, Integer> two = 
            new MyBST.MyBSTNode(2, 4, five);
        MyBST.MyBSTNode<Integer, Integer> seven = 
            new MyBST.MyBSTNode(7, 5, five);
        five.setLeft(two);
        five.setRight(seven);
        MyBST.MyBSTNode<Integer, Integer> nine = 
            new MyBST.MyBSTNode(9, 6, ten);
        MyBST.MyBSTNode<Integer, Integer> sixteen = 
            new MyBST.MyBSTNode(16, 7, ten);
        ten.setLeft(nine);
        ten.setRight(sixteen);
        // set level 3
        MyBST.MyBSTNode<Integer, Integer> six = 
            new MyBST.MyBSTNode(6, 8, seven);
        seven.setLeft(six);
        this.tree.size = 8;

    }

    /**
     * Tests the predecessor() method in MyBSTNode when
     * there is no smaller key. Should return null.
     */
    @Test 
    public void testPredecessorSmallestNode() {
        MyBST.MyBSTNode<Integer, Integer> smallest =
            tree.root.getLeft().getLeft();
        assertEquals(null, smallest.predecessor());
    }

    /**
     * Tests the insert() method in MyBST when the key
     * parameter is null.
     */
    @Test
    public void testInsertNullKey() {
        try {
            tree.insert(null, 20);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assertTrue(thrown);
        assertEquals(8, tree.size);
    }

    /**
     * Tests insert() method when inserting a node
     * with a key that already exists (should overwrite).
     */
    @Test
    public void testInsertExistingKey() {
        assertEquals((Integer)6, tree.insert(9, 100));
        assertEquals((Integer)9, root.getRight().getLeft().getKey());
        assertEquals((Integer)100, root.getRight().getLeft().getValue());
        assertEquals(8, tree.size);
    }

    /**
     * Tests search() method in MyBST when the key is null
     */
    @Test
    public void testSearchNullKey() {
        assertEquals(null, tree.search(null));
    }

    /**
     * Tests remove() method in MyBST when the key
     * is not in the tree
     */
    @Test
    public void testRemoveNonexistingKey() {
        assertEquals(null, tree.remove(1));
        assertEquals(8, tree.size);
    }

    /**
     * Tests remove() method in MyBST when the key is null
     */
    @Test
    public void testRemoveNullKey() {
        assertEquals(null, tree.remove(null));
        assertEquals(8, tree.size);
    }

    /**
     * Tests remove() method when removing a node with two
     * children
     */
    @Test
    public void testRemoveTwoChildren() {
        assertEquals((Integer)2, tree.remove(5));
        assertNull(root.getLeft().getRight().getLeft());
        assertEquals((Integer)6, root.getLeft().getKey());
        assertEquals((Integer)8, root.getLeft().getValue());
        assertEquals((Integer)7, root.getLeft().getRight().getKey());
        assertSame(root.getLeft(), root.getLeft().getRight().getParent());
        assertEquals(7, tree.size);

    }

    /**
     * Tests inorder() method on an empty tree 
     */
    @Test
    public void testInorderEmpty() {
        MyBST<Integer, Integer> emptyTree = new MyBST<>();
        assertEquals(new ArrayList<Integer>(), emptyTree.inorder());
    }

    /**
     * Tests MyBSTIterator class' nextNode() method when there is no
     * next node. Should throw exception
     */
    @Test
    public void testIterNoNextNode() {
        
        MyBSTIterator<Integer, Integer> bstIter = new MyBSTIterator();
        bstIter.root = tree.root;
        MyBSTIterator<Integer, Integer>.MyBSTValueIterator keyIter = 
            bstIter.new MyBSTValueIterator(bstIter.root);

        keyIter.nextNode();
        assertSame(bstIter.root.successor(), keyIter.next); 
        keyIter.nextNode();
        assertSame(bstIter.root.getRight(), keyIter.next);
        assertSame(bstIter.root.getRight(), keyIter.nextNode());
        keyIter.nextNode();
        try {
            keyIter.nextNode();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);
    } 

    /**
     * Tests MyCalendar class' book method on illegal arguments
     * start less than 0 and start greater or equal to end.
     */
    @Test
    public void testCalendarIllegalArg() {
        MyCalendar calendar = new MyCalendar();
        // start < 0
        try {
            calendar.book(-1, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        // start = end
        thrown = false;
        try {
            calendar.book(5, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        // start > end
        thrown = false;
        try {
            calendar.book(10, 9);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
