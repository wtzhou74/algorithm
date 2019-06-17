package hashTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * usage of HASHMAP for (value, index)
 * 
 * **/
public class MinimumIndexSumOfTwoLists {

	public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        
        for (int i = 0; i < list1.length; i++) map1.put(list1[i], i);
        for (int i = 0; i < list2.length; i++) map2.put(list2[i], i);
        
        int sum = Integer.MAX_VALUE;
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                int temp = entry.getValue() + map2.get(entry.getKey());
                if (sum > temp) {
                    sum = temp;
                    result.clear();
                    result.add(entry.getKey());
                } else if (sum == temp){
                    result.add(entry.getKey());
                }
            }
        }
        
        return result.toArray(new String[result.size()]);
    }
}
