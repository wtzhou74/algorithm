import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MapTest {

	public static void main(String[] args) {
		Map<String, Integer> testMap = new HashMap<>();
		//testMap = sortByValueTest(testMap);
		testMap.put("e", 1);
		testMap.remove("e");
		System.out.println(testMap.size() == 0);//true
		testMap.put("a", 10);
		testMap.put("b", 9);
		testMap.put("c", 5);
		testMap.put("d", 20);
		testMap = sortByValueTest(testMap);
		
		Map<Character, Integer> init = new HashMap<>(5);
		System.out.println(init.size());
		
		Map<String, Integer> map = new HashMap<>();
//		for (Map.Entry<String, Integer> s : map.entrySet()) {
//			String key = s.getKey();
//			Integer value = s.getValue();
//		}
//		map.putIfAbsent("a", 1);// return null if key does not exist, and put("a", 1); else return current VALUE of the key "a"
		map.put("a", 1);
		map.put("b", 10);
		map.put("f", 5);
		map.put("h", 3);
		map = sortByValue(map);
		System.out.println();
		
		map.size();
		map.putIfAbsent("b", 0);
		map.getOrDefault("b", 0);
		
		map.put("b", map.getOrDefault("b", 0) + 1);
		//map.computeIfAbsent("source", k -> new ArrayList<>()).add("destination");
		
		// map.getOrDefault(key, defaultValue)
		
		map.remove("b");
		map.values();// collection
		
		map.containsKey("e");
		map.containsValue(5);
		
		map.get("e");
		
		
		//map.computeIfAbsent(key, mappingFunction);// function是用key计算的  // ConnectingCitiesWithMinimumCost.java
		
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			entry.getKey();
			entry.getValue();
		}
		
		for (String key : map.keySet()) {
			
		}
		
		for (int val : map.values()) {
			
		}
		
		Map.Entry<String, Integer> entry = map.entrySet().iterator().next();
		System.out.println();
		
		// get first element
		map.keySet().stream().findFirst().get(); // But items in Map is unordered
		// for TreeMap ===> treeMap.firstKey(); treeMap.lastKey()
		map.keySet().iterator().next();
		
		TreeMap<Integer, Integer> tm = new TreeMap<>();
		tm.pollFirstEntry().getValue();
	}
	
	// Applying Collections.sort();
	public static HashMap<String, Integer> sortByValue(Map<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                //return (o1.getValue()).compareTo(o2.getValue());
            	return o1.getValue() - o2.getValue();
            } 
        }); 
        
//        // Alternative to sort the list
//        list.sort(Map.Entry.comparingByValue());
          
        // put data from sorted list to LinkedHashmap !!!!
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); // LinkedHashMap will keep the inserting order!!!,
        // a不能用比如HashMap<>，否则插入顺序不定
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
	
	public static LinkedHashMap<String, Integer> sortByValueTest(Map<String, Integer> map) {
		// 分两步; 1) 把entrySet放入list, 排序；  2) 重新按排序后顺序放入 LinkedHashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, (a, b) -> a.getValue() - b.getValue());
		
		LinkedHashMap<String, Integer> newMap = new LinkedHashMap<>(); // 一定要用LinkedHashMap,否则对普通Map,插入顺序不定
		for (Map.Entry<String, Integer> item : list) {
			newMap.put(item.getKey(), item.getValue());
		}
		return newMap;
	}
//	
//	public static void main() {
//		
//	}
	
}
