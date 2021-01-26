package design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//You have a queue of integers, you need to retrieve the first unique integer in the queue.
//
//Implement the FirstUnique class:
//
//FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
//int showFirstUnique() returns the value of the first unique integer of the queue, and returns -1 if there is no such integer.
//void add(int value) insert value to the queue.
// 
//
//Example 1:
//
//Input: 
//["FirstUnique","showFirstUnique","add","showFirstUnique","add","showFirstUnique","add","showFirstUnique"]
//[[[2,3,5]],[],[5],[],[2],[],[3],[]]
//Output: 
//[null,2,null,2,null,3,null,-1]
//Explanation: 
//FirstUnique firstUnique = new FirstUnique([2,3,5]);
//firstUnique.showFirstUnique(); // return 2
//firstUnique.add(5);            // the queue is now [2,3,5,5]
//firstUnique.showFirstUnique(); // return 2
//firstUnique.add(2);            // the queue is now [2,3,5,5,2]
//firstUnique.showFirstUnique(); // return 3
//firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
//firstUnique.showFirstUnique(); // return -1
//Example 2:
//
//Input: 
//["FirstUnique","showFirstUnique","add","add","add","add","add","showFirstUnique"]
//[[[7,7,7,7,7,7]],[],[7],[3],[3],[7],[17],[]]
//Output: 
//[null,-1,null,null,null,null,null,17]
//Explanation: 
//FirstUnique firstUnique = new FirstUnique([7,7,7,7,7,7]);
//firstUnique.showFirstUnique(); // return -1
//firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7]
//firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3]
//firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3,3]
//firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7,3,3,7]
//firstUnique.add(17);           // the queue is now [7,7,7,7,7,7,7,3,3,7,17]
//firstUnique.showFirstUnique(); // return 17
//Example 3:
//
//Input: 
//["FirstUnique","showFirstUnique","add","showFirstUnique"]
//[[[809]],[],[809],[]]
//Output: 
//[null,809,null,-1]
//Explanation: 
//FirstUnique firstUnique = new FirstUnique([809]);
//firstUnique.showFirstUnique(); // return 809
//firstUnique.add(809);          // the queue is now [809,809]
//firstUnique.showFirstUnique(); // return -1
// 
//
//Constraints:
//
//1 <= nums.length <= 10^5
//1 <= nums[i] <= 10^8
//1 <= value <= 10^8
//At most 50000 calls will be made to showFirstUnique and add.

public class FirstUniqueNumber {

	// a根据题意， 这里需要保证插入时候的顺序 ===> queue, list, LinkedHashMap
	// a但同时这里还有记录 occurrence ====> Map, 或者 Node（val, occ）
	// 1) Node就不合适，因为在判断有无的时候，需要new Node，不是num
	// 2） 放入到 LinkedHashMap, 然后 sortByValue  ==> 但数据越来越多的时候，sort的消耗很多time
	// 3) 用两个container,一个放Unique的，这里需要保证顺序，==> Map，或 List，但list的O（N）不如Map的O（1） ==>Map
	//      a另外一部分放不是Unique的，这个无关顺序，直接放入 set的就好，同时set有O（1）的contains()时间复杂度
	Map<Integer, Integer> map;
    Set<Integer> set;
    public FirstUniqueNumber(int[] nums) {
        map = new LinkedHashMap<>();
        set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) // 一定要先判断!!!!，否则比如 [7, 7, 7, 7], map remove之后，之后进来的7会被当成unique的而加入map
                continue;
            else if (map.containsKey(num)) {
                map.remove(num);
                set.add(num);
            } else map.put(num, 1);
        }
        //map = sortByValue(map);
    }
    
    public int showFirstUnique() {
        if (map == null || map.size() == 0) return -1;
        int first = map.keySet().iterator().next(); // 直接返回第一个key就好，如果有的话
        return first;
    }
    
    public void add(int value) {
        if(set.contains(value)) {
            return;
        } else if (map.containsKey(value)) {
            map.remove(value);
            set.add(value);
        } else
            map.put(value, 1);
        // map.put(value, map.getOrDefault(value, 0) + 1);
        // map = sortByValue(map);
    }
    
    // a这里不需要
    public Map<Integer, Integer> sortByValue(Map<Integer, Integer> map) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            list.add(entry);
        }
        Collections.sort(list, (a, b) -> a.getValue() - b.getValue());
        Map<Integer, Integer> newMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            newMap.put(entry.getKey(), entry.getValue());
        }
        return newMap;
    }
}
