import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Sample JUnit test cases for BST.
 *
 * @version 1.0
 * @author CS 1332 TAs
 */
public class HashmapStudentTests {
    private HashMap<Integer, String> map;
    private LinkedList<MapEntry<Integer, String>>[] expected =
    (LinkedList<MapEntry<Integer, String>>[]) new
    LinkedList[HashMapInterface.INITIAL_CAPACITY];
    public static final int TIMEOUT = 200;
    
    @Before
    public void setup() {
        map = new HashMap<>();
    }
    
    @Test(timeout = TIMEOUT)
    public void testAdd() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "B"));
        expected[11] = new LinkedList<>();
        expected[11].addLast(new MapEntry(24, "C"));
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();
        expected[4].addLast(new MapEntry(69, "F"));
        
        map.put(24, "C");
        map.put(69, "F");
        map.put(1, "B");
        map.put(2, "A");
        map.put(35, "D");
        map.put(7, "E");
        
        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[2], map.getTable()[2]);
        assertEquals(expected[4], map.getTable()[4]);
        assertEquals(expected[7], map.getTable()[7]);
        assertEquals(expected[9], map.getTable()[9]);
        assertEquals(expected[11], map.getTable()[11]);
        
    }
    
    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "B"));
        expected[11] = new LinkedList<>();
        expected[11].addLast(new MapEntry(24, "C"));
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();
        expected[4].addLast(new MapEntry(69, "F"));
        
        map.put(24, "C");
        map.put(69, "F");
        map.put(1, "B");
        map.put(2, "A");
        map.put(35, "D");
        map.put(7, "E");
        
        Set<Integer> actual = map.keySet();
        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(7);
        expected.add(24);
        expected.add(35);
        expected.add(69);
        
        assertEquals(expected, actual);
    }
    
    @Test(timeout = TIMEOUT)
    public void testRemove() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[1] = new LinkedList<>();
        expected[11] = new LinkedList<>();
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();
        
        map.put(24, "C");
        map.put(69, "F");
        map.put(1, "B");
        map.put(2, "A");
        map.put(35, "D");
        map.put(7, "E");
        
        map.remove(24);
        map.remove(69);
        map.remove(1);
        
        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[2], map.getTable()[2]);
        assertEquals(expected[4], map.getTable()[4]);
        assertEquals(expected[7], map.getTable()[7]);
        assertEquals(expected[9], map.getTable()[9]);
        assertEquals(expected[11], map.getTable()[11]);
        
    }
    
    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[1] = new LinkedList<>();
        expected[11] = new LinkedList<>();
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();
        
        map.put(24, "C");
        map.put(69, "F");
        map.put(1, "B");
        map.put(2, "A");
        map.put(35, "D");
        map.put(7, "E");
        
        assertTrue(map.containsKey(69));
        assertTrue(map.containsKey(7));
        assertFalse(map.containsKey(8));
        assertFalse(map.containsKey(98));
        
    }
}
