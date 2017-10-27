import java.util.List;
import java.util.Set;
import java.util.LinkedList;

/**
 * Your implementation of HashMap.
 * 
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private LinkedList<MapEntry<K, V>>[] backingTable;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {

    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {

    }

    @Override
    public V put(K key, V value) {

    }

    @Override
    public V remove(K key) {

    }

    @Override
    public V get(K key) {

    }

    @Override
    public boolean containsKey(K key) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {

    }

    @Override
    public Set<K> keySet() {

    }

    @Override
    public List<V> values() {

    }

    @Override
    public void resizeBackingTable(int length) {

    }
    
    @Override
    public LinkedList<MapEntry<K, V>>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return backingTable;
    }

}
