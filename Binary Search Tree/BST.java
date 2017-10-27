import java.util.Collection;
import java.util.List;

/**
 * Your implementation of a binary search tree.
 *
 * @author YOUR NAME HERE
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

    }

    @Override
    public void add(T data) {

    }

    @Override
    public T remove(T data) {

    }

    @Override
    public T get(T data) {

    }

    @Override
    public boolean contains(T data) {

    }

    @Override
    public int size() {

    }

    @Override
    public List<T> preorder() {

    }

    @Override
    public List<T> postorder() {

    }

    @Override
    public List<T> inorder() {

    }

    @Override
    public List<T> findPathBetween(T data1, T data2) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int height() {

    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
