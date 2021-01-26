package twoPointers;

import java.util.HashMap;
import java.util.Map;

//Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good if the number of different integers in that subarray is exactly K.
//
//(For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
//
//Return the number of good subarrays of A.
//
// 
//
//Example 1:
//
//Input: A = [1,2,1,2,3], K = 2
//Output: 7
//Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
//Example 2:
//
//Input: A = [1,2,1,3,4], K = 3
//Output: 3
//Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
// 
//
//Note:
//
//1 <= A.length <= 20000
//1 <= A[i] <= A.length
//1 <= K <= A.length
public class SubarraysWithKDifferentIntegers {

	// ！！！！本题的本质； 求K个diff, 那就是等于 “最大K个” - “最大（K - 1）个”； 这里是“AtMOST”
	// ==> 两个 slidingWindows  ==> subarraysWithKDistinctOptimized()
	
	// 一个sliding window， 不优化
	public int subarraysWithKDistinct(int[] A, int K) {
        if (A.length < K) return 0;
        if (A.length <= 1) return K;
        int i = 0, j = 0;
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        while (j < A.length) {
        	map.put(A[j], map.getOrDefault(A[j], 0) + 1);
            if (map.size() == K) {
                if (j - i == K - 1) { // 之间的长度等于 K，j 直接往后移
                    count++;
                    j++;
                    // 多余的，因为 if (size == K) 进来的，而且之后map没做修改
//                    if (map.size() > K) {
//                    	map.remove(A[i]);
//                    	i++;
//                    } 	
                } else {// 后一个数会让其 size > K, 或者已到最后一个了，统计  [i, j] 中等于 K 的个数； [i, k] k<j； 的个数已在 aux下面算过了
                    if ((j + 1 < A.length && !map.containsKey(A[j + 1]))
                    		|| j == A.length - 1) {
                        while (map.size() == K) {
                        	count++;
                            map.put(A[i], map.get(A[i]) - 1);
                            if (map.get(A[i]) == 0)
                            	map.remove(A[i]);
                            i++;
                        }
                        
                    } else { // j + 1后的数还是不能改变map.size()的值，此时保留 i,所以借助 aux
                        int temp = i;
                        Map<Integer, Integer> aux = new HashMap<>();
                        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        	aux.put(entry.getKey(), entry.getValue());
                        }
                        while (aux.size() == K) {
                        	count++;
                            aux.put(A[temp], aux.get(A[temp]) - 1);
                            if (aux.get(A[temp]) == 0)
                            	aux.remove(A[temp]);
                            temp++;
                        }
                    }
                    //map.put(A[j], map.getOrDefault(A[j], 0) + 1); //从一进来就放
                    j++;
                }
            } else if (map.size() > K) { // 需要加上，比如 [1,2], K = 1
            	count++;
            	map.remove(A[i]);
            	i++;
            	j++;
            } else {
                //map.put(A[j], map.getOrDefault(A[j], 0) + 1); //一进来就放
                j++;
            }
            
        }
        return count;
    }
	
	public int subarraysWithKDistinctOptimized(int[] A, int K) {
        if (A.length < K) return 0;
        if (A.length <= 1) return K;
        // “最大K”个的组合数   -  “最大 k-1”个的组合数  == “只有K”个的组合数
        return distinctAtMostK (A, K) - distinctAtMostK(A, K - 1);
    }
    
    public int distinctAtMostK (int[] A, int K) {
        int i = 0, j = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        while (j < A.length) {
            map.put(A[j], map.getOrDefault(A[j], 0) + 1);
            while (map.size() > K) {
                map.put(A[i], map.get(A[i]) - 1);
                if (map.get(A[i]) == 0)
                    map.remove(A[i]);
                i++;
            }
            count += j - i + 1; // 比如 [1, 3, 5]; 组合数 [1] = 1, [1 , 3] = [1] + (1, 3) + (3) = 3; [1, 3, 5] = [1, 3] + (1,3,5) + (3 + 5) + (5) = 3 + 1 + 1 + 1 = 5; 
            					// 后面（）部分正好是 j - i + 1的数目，那么从[i, j]， j 从==i开始，一个个 += 上
            j++;
        }
        return count;
    }
	
	public static void main(String[] args) {
		//int[] A = new int[] {18,49,24,24,46,47,18};
		int[] A = new int[] {1,2};
		new SubarraysWithKDifferentIntegers().subarraysWithKDistinct(A, 1);
	}
}
