import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Your implementation of an AVL Tree.
 *
 * @author YOUR NAME HERE
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
    public Set<T> threshold(T lower, T upper) {

    }

    @Override
    public List<T> levelorder() {

    }

    @Override
    public void clear() {

    }

    @Override
    public int height() {

    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
