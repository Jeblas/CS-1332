import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.util.NoSuchElementException;

/**
 * Your implementation of a binary search tree.
 *
 * @author John Blasco jblasco6
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }
        for (T inData : data) {
            add(inData);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }

        root = addHelper(data, root);
    }

    /**
     * Helper function called by add() to assist in recursive calls
     * @param data data to add the the BST
     * @param curr current node of the path being traversed
     * @return the node to link using pointer reinforcement
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            // Add Node with data
            ++size;
            return new BSTNode<>(data);
        }

        // data already exists within BST
        if (curr.getData().compareTo(data) == 0) {
            return curr;
        }

        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
            return curr;
        }

        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addHelper(data, curr.getRight()));
            return curr;
        }
        return null;

    }

    @Override
    public T remove(T data) {

        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }

        BSTNode<T> temp = new BSTNode<>(null);
        root = removeHelper(data, root, temp);

        if (temp.getData() == null) {
            throw new java.util.NoSuchElementException("Data was not in BST");
        }

        return temp.getData();
    }

    /**
     * Helper function for removing a node
     * @param data data of a node to remove
     * @param curr current node of the BST being traversed
     * @param temp temporary node to stored the value of the node being replaced
     * @return the current node to link using pointer reinforcement
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> temp) {

        if (curr == null) {
            // node does not exist in BST\
            return curr;
        }

        if (curr.getData().compareTo(data) == 0) {
            // Element to remove is found
            --size;
            temp.setData(curr.getData());

            if (curr.getLeft() == null && curr.getRight() == null) {
                // no children to replace
                return null;
            } else if (curr.getLeft() == null) {
                // replace with right
                return curr.getRight();
            } else if (curr.getRight() == null) {
                // replace with left
                return curr.getLeft();
            } else {
                // node has 2 children
                BSTNode<T> replacement = new BSTNode<>(null);
                curr.setLeft(replacePred(curr.getLeft(), replacement));
                replacement.setRight(curr.getRight());
                replacement.setLeft(curr.getLeft());
                return replacement;
            }
        } else if (curr.getData().compareTo(data) > 0) {
            // go left
            curr.setLeft(removeHelper(data, curr.getLeft(), temp));
            return curr;

        } else {
            // go right
            curr.setRight(removeHelper(data, curr.getRight(), temp));
            return curr;
        }

    }

    /**
     * Helper method used to replace a node with two children
     * @param curr the current node of the path of the BST being traversed
     * @param replacement the node to replace the node being removed
     * @return the current node to link using pointer reinforcement
     */
    private BSTNode<T> replacePred(BSTNode<T> curr, BSTNode<T> replacement) {
        if (curr.getRight() ==  null) {
            replacement.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(replacePred(curr.getRight(), replacement));
            return curr;
        }
    }



    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }

        T temp = getHelper(data, root);

        if (temp == null) {
            throw new java.util.NoSuchElementException("Input data does not "
                    + "exist in the BST.");
        }
        return temp;
    }

    /**
     * Helper function called by get() to traverse the BST
     * @param data data to search the BST for
     * @param curr current node in the traversal of the BST
     * @return node to link to parent for pointer reinforcement
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        }

        // data already exists within BST
        if (curr.getData().compareTo(data) == 0) {
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
            throw new IllegalArgumentException("Input data is null.");
        }

        return containsHelper(data, root);
    }


    /**
     * Helper function for contains. Used to search the BST using pointer
     * reinforcement.
     * @param data Data to search the BST for
     * @param curr current node in the traversal of the BST
     * @return if data is present in the BST
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }

        // data already exists within BST
        if (curr.getData().compareTo(data) == 0) {
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
        preOrderHelper(preList, root);
        return preList;
    }

    /**
     * Helper function to get the pre order traversal of the BST
     * @param list list to add elements to in pre-order traversal arrangement
     * @param curr current node in the traversal of the BST
     */
    private void preOrderHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            list.add(curr.getData());
            preOrderHelper(list, curr.getLeft());
            preOrderHelper(list, curr.getRight());
        }
    }

    @Override
    public List<T> postorder() {
        List<T> postList = new ArrayList<>();
        postOrderHelper(postList, root);
        return postList;
    }

    /**
     * Helper function to get the post order traversal of the BST
     * @param list list to add elements to in post-order traversal arrangement
     * @param curr current node in the traversal of the BST
     */
    private void postOrderHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            postOrderHelper(list, curr.getLeft());
            postOrderHelper(list, curr.getRight());
            list.add(curr.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> inList = new ArrayList<>();
        inOrderHelper(inList, root);
        return inList;
    }

    /**
     * Helper function to get the in order traversal of the BST
     * @param list list to add elements to in in-order traversal arrangement
     * @param curr current node in the traversal of the BST
     */
    private void inOrderHelper(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            inOrderHelper(list, curr.getLeft());
            list.add(curr.getData());
            inOrderHelper(list, curr.getRight());
        }
    }

    @Override
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Input data is null.");
        }

        List<T> list = new ArrayList<>();
        pathHelper(data1, data2, root, list);

        if (list.isEmpty()) {
            throw new java.util.NoSuchElementException("Data is not in BST.");
        }

        // if data1 or data2 are not in the BST
        if ((list.get(0).compareTo(data1) != 0
                && list.get(0).compareTo(data2) != 0)
                || (list.get(list.size() - 1).compareTo(data1) != 0
                && list.get(list.size() - 1).compareTo(data2) != 0)) {
            throw new java.util.NoSuchElementException("Data is not in BST.");
        }
        return list;
    }

    /**
     * Helper function using pointer reinforcement to find the path between two
     * nodes in the BST.
     * @param data1 First data element to find a path to data2
     * @param data2 Second data element to find a path to data1
     * @param curr the current node used in the traversal of the BST
     * @param list List to add the path between data1 and data2
     */
    private void pathHelper(T data1, T data2, BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            int d1comp = data1.compareTo(curr.getData());
            int d2comp = data2.compareTo(curr.getData());

            if (d1comp < 0 && d2comp < 0) {
                // Go left
                pathHelper(data1, data2, curr.getLeft(), list);
            } else if (d1comp > 0 && d2comp > 0) {
                // go right
                pathHelper(data1, data2, curr.getRight(), list);
            } else if (d1comp == 0 && d2comp != 0) {
                // subset
                if (d2comp > 0) {
                    // right
                    addPathPre(data2, curr, list);
                } else {
                    //left
                    addPathPost(data2, curr, list);
                }
            } else if (d1comp != 0 && d2comp == 0) {
                // recurse down to other data
                if (d1comp > 0) {
                    //right
                    addPathPre(data1, curr, list);
                } else {
                    // left
                    addPathPost(data1, curr, list);
                }
            } else if (d1comp == 0) {
                //same element
                list.add(curr.getData());
            } else {
                // Conflicting values
                if (d1comp < 0) {
                    //d1 left
                    //*****************Test case hits here
                    addPathPost(data1, curr, list);
                    addPathPre(data2, curr.getRight(), list);
                    // d1 right add
                } else {
                    // d2 left
                    addPathPost(data2, curr, list);
                    addPathPre(data1, curr.getRight(), list);
                }

            }

        }
        // data is not in tree
    }

    /**
     *  Adds elements to list by adding an element before a
     *  recursive call is made.
     * @param data data element to traverse to
     * @param curr current traversal node in the BST
     * @param list list to add path node data to
     */
    private void addPathPre(T data, BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            if (data.compareTo(curr.getData()) == 0) {
                //add to list
                list.add(curr.getData());
            } else if (data.compareTo(curr.getData()) < 0) {
                //left
                list.add(curr.getData());
                addPathPre(data, curr.getLeft(), list);
            } else {
                // right
                list.add(curr.getData());
                addPathPre(data, curr.getRight(), list);
            }
        }
    }

    /**
     * Adds elements to list by adding an element after a recursive call is made
     * @param data data to traverse to
     * @param curr current node being traversed in the BST
     * @param list list to add path node data to
     */
    private void addPathPost(T data, BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            if (data.compareTo(curr.getData()) == 0) {
                //add to list
                list.add(curr.getData());
            } else if (data.compareTo(curr.getData()) < 0) {
                //left
                addPathPost(data, curr.getLeft(), list);
                list.add(curr.getData());
            } else {
                // right
                addPathPost(data, curr.getRight(), list);
                list.add(curr.getData());
            }
        }
    }


    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper function used to recursively find the height of the BST
     * @param curr current node in the traversal of the BST
     * @return the Height of the BST
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }

        int leftVal = heightHelper(curr.getLeft());
        int rightVal = heightHelper(curr.getRight());

        if (leftVal > rightVal) {
            return leftVal + 1;
        } else {
            return rightVal + 1;
        }

    }
    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
