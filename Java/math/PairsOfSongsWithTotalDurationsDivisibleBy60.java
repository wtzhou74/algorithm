package math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//In a list of songs, the i-th song has a duration of time[i] seconds. 
//
//Return the number of pairs of songs for which their total duration in seconds is divisible by 60.  Formally, we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.
//
// 
//
//Example 1:
//
//Input: [30,20,150,100,40]
//Output: 3
//Explanation: Three pairs have a total duration divisible by 60:
//(time[0] = 30, time[2] = 150): total duration 180
//(time[1] = 20, time[3] = 100): total duration 120
//(time[1] = 20, time[4] = 40): total duration 60
//Example 2:
//
//Input: [60,60,60]
//Output: 3
//Explanation: All three pairs have a total duration of 120, which is divisible by 60.
// 
//
//Note:
//
//1 <= time.length <= 60000
//1 <= time[i] <= 500
public class PairsOfSongsWithTotalDurationsDivisibleBy60 {

	// 1- brute force => O(n * n)
	
	// a借助mod运算， (a + b) % c ==> (a % c + b % c) % c
	// (a + b） %  60 = 0 ==>
	//	 ==> (a % 60 == 0 && b % 60 == 0) || (a % 60 + b % 60) = 60
	public int numPairsDivisibleBy60(int[] time) {
        if (time == null || time.length == 0)
            return 0;
        int count = 0;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            map.computeIfAbsent(time[i] % 60, k -> new HashSet<>()).add(i);
        }
        Set<Integer> aux = new HashSet<>();
        for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            int key = entry.getKey();
            Set<Integer> set = entry.getValue();
            if (key == 0 || key == 30) {  // !!! 30 + 30
                int size = set.size();
                aux.add(key);
                count += (size * size - size) / 2;
            } else if (!aux.contains(key)){
                aux.add(key);
                if (map.containsKey(60 - key)) {
                    aux.add(60 - key);
                    count += set.size() * map.get(60 - key).size();
                }
            }
        }
        return count;
    }
	
	// a优化上面的代码， 类似 2sum
	public int numPairsDivisibleBy60_1(int[] time) {
        if (time == null || time.length == 0)
            return 0;
        int[] remainders = new int[60]; // 余数只能 1-59
        int count = 0;
        for (int i = 0; i < time.length; i++) {
        	// a两种情况  a = 0; 或 a = 60 - b, b这里是 time[i] % 60  ==> 2 sum的问题  (a + b = 60)
            if (time[i] % 60 == 0) {
                count += remainders[0]; // 累加和 n+...+3+2+1
            } else {
            	// a + b = 60, 看pair， 就看 60 - b 那边有几个
                count += remainders[60 - time[i] % 60];
            }
            // s其他跟 60没关的，即使remainder[i]有值，也加不到count上去，但得记下，有可能后面来的值会跟其匹配上
            remainders[time[i] % 60]++;
        }
        
        return count;
    }
	
	// a用hashMap代替上面的array，但思想是一样的
	public int numPairsDivisibleBy60_2(int[] time) {
        if (time == null || time.length == 0)
            return 0;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < time.length; i++) {
            if (time[i] % 60 == 0) {
                count += map.getOrDefault(0, new HashSet<>()).size();
                map.computeIfAbsent(0, k -> new HashSet<>()).add(i);
            } else {
                count += map.getOrDefault(60 - time[i] % 60, new HashSet<>()).size();
                map.computeIfAbsent(time[i] % 60, k -> new HashSet<>()).add(i);
            }
        }
        
        return count;
    }
}
