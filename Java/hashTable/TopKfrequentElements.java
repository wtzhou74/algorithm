package hashTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given a non-empty array of integers, return the k most frequent elements.
 * 
 * USAGE OF HASHMAP TO GROUP BY FREQUENCE
 * */
public class TopKfrequentElements {

	public static List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        HashMap<Integer, Integer> map = new HashMap<>();   
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        // TreeMap sorted by keys in natural ordering by default
        // TreeMap accept Comparator, while Map or HashMap does not
        TreeMap<Integer, Integer> treeMap = sortedMapByValue(map);
        int i = 1;
        for(Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            if (i <= k) result.add(entry.getValue());
            else break;
        }
        
        return result;
        
    }
    public static TreeMap<Integer, Integer> sortedMapByValue(HashMap<Integer, Integer> map) {
        ValueComparator compartor = new ValueComparator(map);
        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(compartor);
        treeMap.putAll(map);
        
        return treeMap;
    }
    
    public static void main(String[] args) {
    	int[] test = {1,1,1,2,2,3};
    	topKFrequent(test, 2);
    }
}

// map sorted by value, reference to : https://www.programcreek.com/2013/03/java-sort-map-by-value/
class ValueComparator implements Comparator<Integer> {
    Map<Integer, Integer> map = new HashMap<>();
    public ValueComparator(HashMap<Integer, Integer> map) {
        this.map.putAll(map);
    }
//    @Override
//    public int compare(int a, int b) {
//        if (map.get(a) >= map.get(b)) return -1; // The param is Object, not primitive data type
//        else return 1;
//    }
    @Override
    public int compare(Integer a, Integer b) {
        if (map.get(a) >= map.get(b)) return -1;// in descending order
        else return 1;
    }
}

// support generic type
//class ValueComparator implements Comparator {
//	Map map;
// 
//	public ValueComparator(Map map) {
//		this.map = map;
//	}
// 
//	public int compare(Object keyA, Object keyB) {
//		Comparable valueA = (Comparable) map.get(keyA);
//		Comparable valueB = (Comparable) map.get(keyB);
//  according to the return value of compareTo() to decide whether it is greater equal to or less than (in natural ordering)
//		return valueB.compareTo(valueA);
//	}
//}
