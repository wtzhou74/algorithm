package hashTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 * Design a data structure that supports all following operations in average O(1) time.
 * insert(val): Inserts an item val to the set if not already present.
 * remove(val): Removes an item val from the set if present.
 * getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
 * 
 * Note: Keep the position/index when remove an element of Map => leverage List (size) to keep the index
 * */
public class InsertDeleteGetRandom {

	ArrayList<Integer> nums;
    HashMap<Integer, Integer> map; // key is the value and the value is the index in nums(list)
    //java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */
    public InsertDeleteGetRandom() {
        nums = new ArrayList<Integer>();
        map = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        } else {
            map.put(val, nums.size());// in line with remove()
            nums.add(val);
            return true;
        }
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;
        else {
            int index = map.get(val);// the index where the removed value in
            if (index < nums.size() - 1) {
                int last = nums.get(nums.size() - 1);//get last one element
                map.put(last, index);// put the last one element into the removed index
                
                nums.set(index, last);
            }
            // remove the last one no matter where the removed value is
            nums.remove(nums.size() - 1);
            map.remove(val);
            
            return true;
        }
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
    	Random rand = new Random();
        return nums.get(rand.nextInt(nums.size())); // get random number [0,size)
        // int rand = (int)(Math.random() * range) + min; // get random number [1, size]
    }
}
