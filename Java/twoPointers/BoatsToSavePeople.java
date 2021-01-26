package twoPointers;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
//
//Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most limit.
//
//Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried by a boat.)
//
// 
//
//Example 1:
//
//Input: people = [1,2], limit = 3
//Output: 1
//Explanation: 1 boat (1, 2)
//Example 2:
//
//Input: people = [3,2,2,1], limit = 3
//Output: 3
//Explanation: 3 boats (1, 2), (2) and (3)
//Example 3:
//
//Input: people = [3,5,3,4], limit = 5
//Output: 4
//Explanation: 4 boats (3), (3), (4), (5)
//Note:
//
//1 <= people.length <= 50000
//1 <= people[i] <= limit <= 30000
public class BoatsToSavePeople {

	//Time: O(NlgN)
	// 最轻的跟最重的配对
	// 但这里要注意,同时两边移动的时候,sum 可能变大,不变,变小 !!!!!! 看numRescueBoatsTest()的错误逻辑
	public int numRescueBoats(int[] people, int limit) {
        if (people.length == 1) return 1;
        // List<Integer> aux = new LinkedList<>();
        // for (int i = 0; i < people.length; i++) {
        //     aux.add(people[i]);
        // }
        Arrays.sort(people);
        //Collections.sort(aux);
        if (people[0] + people[1] > limit) return people.length;
        int i = 0, j = people.length - 1;
        int total = 0;
        while (i < j) {
            if (people[i] + people[j] <= limit) {
                i++;
                j--;
                total++;
            } else {
                j--;
                total++;
            }
        }
        if (i == j) total++;
        return total;
    }
	
	// 由于其值可以重复,同时往里移动时和可能变大 ==> 1,3,5,5 (1 + 5 < 3 + 5)
	// 往里移动时,左边界往里移动会使sum变大, 右边界往里移动会使sum变小 (也可能不变)
	public int numRescueBoatsTest(int[] people, int limit) {
        if (people.length == 1) return 1;
        List<Integer> aux = new LinkedList<>();
        for (int i = 0; i < people.length; i++) {
            aux.add(people[i]);
        }
        Collections.sort(aux);
        if (aux.get(0) + aux.get(1) > limit) return aux.size();
        int i = 0, j = aux.size() - 1;
        int firstJ = -1;
        int total = 0;
        while (i < j) {
            if (aux.get(i) + aux.get(j) <= limit) {
                if (firstJ == -1) firstJ = j;
                i++;
                j--;
                total++;
            } else {
                j--;
            }
        }
        // 以下逻辑仅使用于左右两边往里移动,其和是小于等于外层的
        // 而实际情况是由于其值可以重复,同时往里移动时和可能变大 ==> 1,3,5,5 (1 + 5 < 3 + 5)
        // 所以如下逻辑对某些用例不合适
        if (firstJ == aux.size() - 1) return total;
        else {
            for (int k = firstJ + 1; k < aux.size(); k++) {
                total++;
            }
            if (j == i) total += 1;
        }
        return total;
    }
}
