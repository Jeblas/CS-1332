import java.util.NoSuchElementException;

/**
 * Your implementation of a linked queue.
 *
 * @author John Blasco jblasco6
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty.");
        }

        T temp = head.getData();
        head = head.getNext();
        if (size == 1) {
            tail = null;
        }
        --size;
        return temp;

    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Input data is null.");
        }

        if (size == 0) {
            tail = new LinkedNode<T>(data);
            head = tail;
        } else {
            tail.setNext(new LinkedNode<T>(data));
            tail = tail.getNext();
        }
        ++size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this queue.
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

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}