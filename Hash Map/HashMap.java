import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
//import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

/**
 * Your implementation of HashMap.
 * 
 * @author John Blasco jblasco6
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
        backingTable = new LinkedList[INITIAL_CAPACITY];
        size = 0;
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
        backingTable = new LinkedList[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Input data cannot be null.");
        }

        // if greater then load factor resize
        if ((double) (size + 1) / backingTable.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(backingTable.length * 2 + 1);
        }

        int index = Math.abs(key.hashCode()) % backingTable.length;

        if (backingTable[index] == null) {
            backingTable[index] = new LinkedList<>();
        } else {
            for (MapEntry<K, V> temp : backingTable[index]) {
                if (temp.getKey() == key) {
                    V returnValue = temp.getValue();
                    temp.setValue(value);
                    return returnValue;
                }
            }
        }

        backingTable[index].addFirst(new MapEntry<>(key, value));
        ++size;
        return null;

    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Input key is null");
        }

        int index = Math.abs(key.hashCode()) % backingTable.length;

        MapEntry<K, V> curr;

        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> it = backingTable[index].iterator();
            while (it.hasNext()) {
                curr = it.next();
                if (curr.getKey() == key) {
                    V returnValue = curr.getValue();
                    it.remove();
                    --size;
                    return returnValue;
                }
            }
        }

        throw new java.util.NoSuchElementException("Key does not exist");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Input key is null");
        }

        int index = Math.abs(key.hashCode()) % backingTable.length;

        MapEntry<K, V> curr;

        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> it = backingTable[index].iterator();
            while (it.hasNext()) {
                curr = it.next();
                if (curr.getKey() == key) {
                    return curr.getValue();
                }
            }
        }

        throw new java.util.NoSuchElementException("Key does not exist");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Input key is null");
        }

        int index = Math.abs(key.hashCode()) % backingTable.length;

        MapEntry<K, V> curr;

        if (backingTable[index] != null) {
            Iterator<MapEntry<K, V>> it = backingTable[index].iterator();
            while (it.hasNext()) {
                curr = it.next();
                if (curr.getKey() == key) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clear() {
        backingTable = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();

        for (int i = 0; i < backingTable.length; ++i) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> temp : backingTable[i]) {
                    returnSet.add(temp.getKey());
                }
            }
        }

        return returnSet;
    }

    @Override
    public List<V> values() {
        List<V> returnList = new ArrayList<>();

        for (int i = 0; i < backingTable.length; ++i) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> temp : backingTable[i]) {
                    returnList.add(temp.getValue());
                }
            }
        }

        return returnList;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Input length cannot be "
                    + "negative");
        }

        LinkedList<MapEntry<K, V>>[] tempTable = new LinkedList[length];
        // go through backing array and for each value add to new array

        for (int i = 0; i < backingTable.length; ++i) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> temp : backingTable[i]) {
                    int index = Math.abs(temp.getKey().hashCode()) % length;
                    if (tempTable[index] == null) {
                        tempTable[index] = new LinkedList<>();
                    }
                    tempTable[index].addFirst(temp);
                }
            }
        }

        backingTable = tempTable;
    }
    
    @Override
    public LinkedList<MapEntry<K, V>>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return backingTable;
    }

}
