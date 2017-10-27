/**
 * Your implementation of a linked stack.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T pop() {
        return null;
    }

    @Override
    public void push(T data) {
        return;
    }

    @Override
    public int size() {
        return 0;
    }

    /**
     * Returns the head of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}