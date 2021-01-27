package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//Design a data structure that supports all following operations in average O(1) time.
//
//
//
//insert(val): Inserts an item val to the set if not already present.
//remove(val): Removes an item val from the set if present.
//getRandom: Returns a random element from current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
// 
//
//Example:
//
//// Init an empty set.
//RandomizedSet randomSet = new RandomizedSet();
//
//// Inserts 1 to the set. Returns true as 1 was inserted successfully.
//randomSet.insert(1);
//
//// Returns false as 2 does not exist in the set.
//randomSet.remove(2);
//
//// Inserts 2 to the set, returns true. Set now contains [1,2].
//randomSet.insert(2);
//
//// getRandom should return either 1 or 2 randomly.
//randomSet.getRandom();
//
//// Removes 1 from the set, returns true. Set now contains [2].
//randomSet.remove(1);
//
//// 2 was already in the set, so return false.
//randomSet.insert(2);
//
//// Since 2 is the only number in the set, getRandom always return 2.
//randomSet.getRandom();
public class InsertDeleteGetRandomO1 {

	/** Initialize your data structure here. */
    Map<Integer, Integer> map; // value-index // 根据给定的val,找到要remove 的index, 
    List<Integer> list; // 保存当前可用的元素，remove的时候，把要remove的“交换”到最后，这样就不会因为remove而需要额外移动元素了
    Random random; // 生成 index的随机值
    public InsertDeleteGetRandomO1() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
        this.random = new Random();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val))
            return false;
        list.add(val);
        map.put(val, list.size() - 1);
        
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    // remove的时候，把要remove的元素换到最后（这样remove就是O（1）， 因为只需要交换元素，不需要额外移动元素了）
    public boolean remove(int val) {
        if (!map.containsKey(val))
            return false;
        int idx = map.get(val);
        if (idx != list.size() - 1) {
            list.set(idx, list.get(list.size() - 1));
            map.put(list.get(list.size() - 1), idx); // !!!一定要同时更新map中 value-index的值， 因为上面value对应的index改了
        }
        list.remove(list.size() - 1);
        map.remove(val);
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        int randomNum = random.nextInt(list.size());
        return list.get(randomNum);
    }
    
    public static void main(String[] args) {
    	InsertDeleteGetRandomO1 t = new InsertDeleteGetRandomO1();
    	t.insert(0);
    	t.insert(1);
    	t.remove(0);
    	t.insert(2);
    	t.remove(1);
    	t.getRandom();
    }
}
