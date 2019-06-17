package hashTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l)
 * there are such that A[i] + B[j] + C[k] + D[l] is zero.
 * To make problem a bit easier, all A, B, C, D have same length of N 
 * where 0 ≤ N ≤ 500. All integers are in the range of -228 to 228 - 1 and 
 * the result is guaranteed to be at most 231 - 1.
 * 
 * 
 * a + b + c + d = 0, then (a + b) == - (c + d) => HashMap to group (Duplication key)
 * */
public class FourSumII {

	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> abGroup = new HashMap<>();
        Map<Integer, Integer> cdGroup = new HashMap<>();
        
        creatGroup(A, B, abGroup);
        creatGroup(C, D, cdGroup);
        
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : abGroup.entrySet()) {
            if (cdGroup.containsKey(-entry.getKey()))
                result += cdGroup.get(-entry.getKey()) * entry.getValue();
        }
        
        return result;
        
    }
    
    public void creatGroup(int[] array1, int[] array2, Map<Integer, Integer> groupMap) {
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                int sum = array1[i] + array2[j];
                groupMap.put(sum, groupMap.getOrDefault(sum, 0) + 1);
            }
        }
    }
    
    // concise solution
    public int fourSumCount2(int[] A, int[] B, int[] C, int[] D) {
        // (a + b) = - (c + d)
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int a : A) {
            for (int b : B) {      
                map.put(a + b, map.getOrDefault(a+b, 0) + 1);
            } 
        }
        
        int result = 0;
        for (int c : C) {
            for (int d : D) {
                result += map.getOrDefault(-1 * (c+d), 0);
            }
        }
        
        return result;
    }
}
