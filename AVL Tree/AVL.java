import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of an AVL Tree.
 *
 * @author John Blasco jblasco6
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can not be null");
        }

        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Can not add a null "
                        + "element to the AVL tree");
            }
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }

        root = addHelper(data, root);
    }

    /**
     * Helper method to add a value to the tree recursively
     * @param data Data to add to the AVL tree
     * @param curr Current node in traversal of the AVL tree using
     *             pointer reinforcement
     * @return Node to link the AVL tree back together
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            ++size;
            return new AVLNode<>(data);
        } else if (curr.getData().compareTo(data) < 0) {
            // input data is larger go right
            // set data in right
            curr.setRight(addHelper(data, curr.getRight()));
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        }

        //recalculate height and BF
        recalculateHeightBF(curr);
        return checkBF(curr);
    }

    /**
     * Check the branch factor of a node in the AVL tree. Balances the
     * node if the absolute value is greater than 1.
     * @param curr The node to check the branch factor of
     * @return The correct node to link after balancing for pointer
     *         reinforcement
     */
    private AVLNode<T> checkBF(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            //right heavy
            if (curr.getRight() != null
                    && curr.getRight().getBalanceFactor() >= 1) {
                // right-left rotation
                curr.setRight(rightRotation(curr.getRight()));
            }
            return leftRotation(curr);

        } else if (curr.getBalanceFactor() > 1) {
            // left heavy -- right rotation
            if (curr.getLeft() != null
                    && curr.getLeft().getBalanceFactor() <= -1) {
                //left-right rotation
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            return rightRotation(curr);
        }
        return curr;
    }

    /**
     * Performs a left rotation on the input node.
     * @param curr Node to perform a left rotation on.
     * @return The node to be linked for pointer reinforcement after the left
     *         rotation.
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        // rotate nodes
        AVLNode<T> temp = curr.getRight();
        curr.setRight(temp.getLeft());
        temp.setLeft(curr);

        // recalculate heights and BF for temp and curr
        recalculateHeightBF(curr);
        recalculateHeightBF(temp);

        return temp;
    }

    /**
     * Performs a right rotation on the input node.
     * @param curr Node to perform a right rotation on.
     * @return The node to be linked for pointer reinforcement after a right
     *         rotation is performed.
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        curr.setLeft(temp.getRight());
        temp.setRight(curr);

        // recalculate heights and BF for temp and curr
        recalculateHeightBF(curr);
        recalculateHeightBF(temp);

        return temp;
    }

    /**
     * Calculated the height and branch factor of the input node by analyzing
     * the height of it's children.
     * @param curr Node to calculate the height and branch factor of.
     */
    private void recalculateHeightBF(AVLNode<T> curr) {
        if (curr != null) {
            if (curr.getLeft() != null) {
                if (curr.getRight() != null) {
                    // do Normal thing
                    curr.setBalanceFactor(curr.getLeft().getHeight()
                            - curr.getRight().getHeight());
                    if (curr.getLeft().getHeight()
                            > curr.getRight().getHeight()) {
                        curr.setHeight(curr.getLeft().getHeight() + 1);
                    } else {
                        curr.setHeight(curr.getRight().getHeight() + 1);
                    }
                } else {
                    // left non null
                    curr.setHeight(curr.getLeft().getHeight() + 1);
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            } else {
                if (curr.getRight() != null) {
                    // right non null
                    curr.setHeight(curr.getRight().getHeight() + 1);
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                } else {
                    curr.setBalanceFactor(0);
                    curr.setHeight(0);
                }
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can not be null");
        }

        AVLNode<T> temp = new AVLNode<>(null);
        root = removeHelper(data, root, temp);

        if (temp.getData() == null) {
            throw new java.util.NoSuchElementException("Data is not in "
                    + "the AVL tree");
        }

        return temp.getData();
    }

    /**
     * Helper method for removing a node from the AVL tree.
     * @param data Data value to search for.
     * @param curr Current node in the traversal of the tree.
     * @param temp Dummy node to store the value of the removed node.
     * @return A node used to link the tree together using pointer reinforcement
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr, AVLNode<T> temp) {
        if (curr == null) {
            // element is not in the AVL
            return curr;
        }

        if (curr.getData().compareTo(data) == 0) {
            --size;
            temp.setData(curr.getData());
            // data is found check children
            if (curr.getLeft() == null && curr.getRight() == null) {
                // replace leaf
                return null;
            } else if (curr.getLeft() == null) {
                // 1 child - right
                return curr.getRight();
            } else if (curr.getRight() == null) {
                // 1 child - left
                return curr.getLeft();
            } else {
                //2 children
                AVLNode<T> replacement = new AVLNode<>(null);
                curr.setRight(replaceSuccessor(curr.getRight(), replacement));
                replacement.setRight(curr.getRight());
                replacement.setLeft(curr.getLeft());
                //recalculateHeightBF(replacement);
                curr = replacement;
                //return checkBF(replacement);
            }
        } else if (curr.getData().compareTo(data) > 0) {
            // go left
            curr.setLeft(removeHelper(data, curr.getLeft(), temp));
        } else {
            // go right
            curr.setRight(removeHelper(data, curr.getRight(), temp));
        }

        // update Height and BF then check the Branch Factor for rotations
        recalculateHeightBF(curr);
        return checkBF(curr);
    }

    /**
     * Finds a replacement for a removed node using the sucessor of that node.
     * @param curr Current node in the traversal of the AVL tree.
     * @param replacement Dummy node to store the value of the replacement.
     * @return The node to link the AVL tree together using pointer
     *         reinforcement.
     */
    private AVLNode<T> replaceSuccessor(AVLNode<T> curr,
                                        AVLNode<T> replacement) {
        if (curr.getLeft() == null) {
            replacement.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(replaceSuccessor(curr.getLeft(), replacement));
        }

        recalculateHeightBF(curr);
        return checkBF(curr);
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can not be null");
        }
        T temp = getHelper(data, root);
        if (temp == null) {
            throw new java.util.NoSuchElementException("Input data is "
                    + "not in the AVL tree");
        }
        return temp;
    }

    /**
     * Helper method for get() in order to traverse the AVL tree.
     * @param data Data to search for in the AVL tree.
     * @param curr The current node in the traversal of AVL tree.
     * @return The value of the node being searched for.
     */
    private T getHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (curr.getData().compareTo(data) > 0) {
            return getHelper(data, curr.getLeft());
        } else {
            return getHelper(data, curr.getRight());
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can not be null");
        }
        return containsHelper(data, root);
    }

    /**
     * Helper method for contains() used to traverse through the AVL tree.
     * @param data Data to search the AVL tree for.
     * @param curr the current node in the traversal of the AVL tree.
     * @return If the node is contained in the AVL tree.
     */
    private boolean containsHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) == 0) {
            return true;
        } else if (curr.getData().compareTo(data) > 0) {
            return containsHelper(data, curr.getLeft());
        } else {
            return containsHelper(data, curr.getRight());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> preList = new ArrayList<>();
        preorderHelper(root, preList);
        return preList;
    }

    /**
     * Helper method for preorder() to traverse the AVL tree.
     * @param curr The current node in the traversal of the AVL tree.
     * @param list A list of data in preorder of the AVL tree.
     */
    private void preorderHelper(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> postList = new ArrayList<>();
        postorderHelper(root, postList);
        return postList;
    }

    /**
     * Helper method for postorder() to traverse the AVL tree.
     * @param curr The current node in the traversal of the AVL tree.
     * @param list A list of the data postorder of the AVL tree.
     */
    private void postorderHelper(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    @Override
    public Set<T> threshold(T lower, T upper) {
        if (lower == null || upper == null) {
            throw new IllegalArgumentException("Lower and upper can"
                    + "not be null");
        }
        Set<T> set = new HashSet<>();
        thresholdHelper(lower, upper, root, set);

        return set;
    }

    /**
     * Helper method for threshold to traverse the AVL tree.
     * @param lower Lower bounds of data elements to search for.
     * @param upper Upper bounds of data elements to search for.
     * @param curr The current node in the traversal of the AVL tree.
     * @param set A set of data elements between the upped and lower bounds.
     */
    private void thresholdHelper(T lower, T upper, AVLNode<T> curr,
                                 Set<T> set) {
        if (curr != null) {
            if (lower.compareTo(curr.getData()) >= 0) {
                thresholdHelper(lower, upper, curr.getRight(), set);
            }

            // Check if current node's data is with in the range
            if (lower.compareTo(curr.getData()) < 0
                    && upper.compareTo(curr.getData()) > 0) {

                thresholdHelper(lower, upper, curr.getLeft(), set);
                set.add(curr.getData());
                thresholdHelper(lower, upper, curr.getRight(), set);

            }

            if (upper.compareTo(curr.getData()) <= 0) {
                thresholdHelper(lower, upper, curr.getLeft(), set);
            }
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> levelList = new ArrayList<>();
        Queue<AVLNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            if (queue.peek() != null) {
                queue.add(queue.peek().getLeft());
                queue.add(queue.peek().getRight());
                levelList.add(queue.remove().getData());
            } else {
                queue.remove();
            }
        }
        return levelList;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
