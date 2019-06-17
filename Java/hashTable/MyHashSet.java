package hashTable;

/**
 * All values will be in the range of [0, 1000000].
 * The number of operations will be in the range of [1, 10000].
 * Please do not use the built-in HashSet library.
 * */
public class MyHashSet {

	private int buckets = 1000;
    private int itemsPerBucket = 1001;
    private boolean[][] table;
    /** Initialize your data structure here. */
    public MyHashSet() {
        table = new boolean[buckets][];// initialize 2D array
    }
    
    // HASH FUNCTION
    public int hash(int key) {
        return key % buckets;
    }
    
    // position of the bucket of the element
    // NEEDED because of the value range [1, 1000000], otherwise, table[hashKey][key] might cause IndexOutofBound
    public int pos (int key) {
        return key / buckets;
    }
    public void add(int key) {
        int hashKey = hash(key);//bucket number
        if (table[hashKey] == null) {
            table[hashKey] = new boolean[itemsPerBucket]; // put key into the bucket with 1001 items
        }
        table[hashKey][pos(key)] = true;
    }
    
    public void remove(int key) {
        int hashKey = hash(key);
        if (table[hashKey] != null) 
            table[hashKey][pos(key)] = false;
    }
    
    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int hashKey = hash(key);
        if (table[hashKey] != null && table[hashKey][pos(key)]) return true;
        else return false;
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
