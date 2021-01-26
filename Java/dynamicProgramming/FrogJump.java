package dynamicProgramming;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
//
//Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.
//
//If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the frog can only jump in the forward direction.
//
//Note:
//
//The number of stones is ≥ 2 and is < 1,100.
//Each stone's position will be a non-negative integer < 231.
//The first stone's position is always 0.
//Example 1:
//
//[0,1,3,5,6,8,12,17]
//
//There are a total of 8 stones.
//The first stone at the 0th unit, second stone at the 1st unit,
//third stone at the 3rd unit, and so on...
//The last stone at the 17th unit.
//
//Return true. The frog can jump to the last stone by jumping 
//1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
//2 units to the 4th stone, then 3 units to the 6th stone, 
//4 units to the 7th stone, and 5 units to the 8th stone.
//Example 2:
//
//[0,1,2,3,4,8,9,11]
//
//Return false. There is no way to jump to the last stone as 
//the gap between the 5th and 6th stone is too large.
public class FrogJump {

	// 也是一个典型的DP问题， ==> 某个点可不可达
	// ===> 对每个子问题，即看各个stone是否可达
	// ===> 若后一个可达， 则可以由前一个经过 k - 1, k, 或 k + 1 步到达， K 是前一个经过K步才到达的
	public boolean canCross(int[] stones) {
		// 这里不能用boolean， boolean 默认是false, 而对某些stones，由于前面的缘故，是可以为false的
        Boolean[][] memo = new Boolean[stones.length][stones.length]; // 初始化步数最多肯定不会超过的stone的个数的
        return dfs(stones, 0, 0, memo);
    }
    
	// TIME： O（n ^ 3）
    public boolean dfs(int[] stones, int idx, int step,
                      Boolean[][] memo) {
        if (idx == stones.length - 1) {
            //memo[idx][step] = true;
            return true;
        }
        if (memo[idx][step] != null) {
            return memo[idx][step];
        }
        // 这里的trick 是 i要初始化 idx+1, 即假定第i个是可达的，那看下一个stone, 即 idx + 1, 然后看这两个的gap
        for (int i = idx + 1; i < stones.length; i++) {
            int gap = stones[i] - stones[idx]; // 看前后两个的gap
            if (gap >= step - 1 && gap <= step + 1) { // gap 必须在这范围
                if (dfs(stones, i, gap, memo)) {
                    memo[idx][step] = true;
                    return true;
                }
            }
        }
        memo[idx][step] = false;
        return false;
    }
    
    
    // dp array 算法
    // 后面的选择依赖前面  ==> Map<Integer, Set<Integer>>, key放的是第几个stone,不是索引，set放的是到达 key的所有可能step
    // ！！！ 这里的step不是固定的0，1，2，是基于上一次选的step
    // 知道了到达当前stone可行的上一个steps, 对可达到的下一个stone就很容易知道了
    public boolean canCrossDP(int[] stones) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>()); // 初始化
        }
        map.get(0).add(0);
        for (int i = 0; i < stones.length; i++) {
            for (int k : map.get(stones[i])) { // 取出到达i可以的所有上一次的所有可能的step
                // for each K, we have 3 choice
                for (int step = k - 1; step <= k + 1; step++) {
                	// 这里如果map的key里面有 stones[i] + step说明这个step可以，前面的step < len可以不要
                	// ===> 这里key放的是stones[i]， 不是索引i, [1,2,4,6]; 这样 + step后可以直接知道下一个是哪个stone（不是索引）
                	// ===> 前后stone值的差值就是从i到i+1个stone需要的step
                    if (step > 0 && step < stones.length && map.containsKey(stones[i] + step)) {
                        map.get(stones[i] + step).add(step);
                    }
                }
            }
        }
        // 看最后一个stone, 有没有合适的step到达，有就return true, 否则false
        return map.get(stones[stones.length - 1]).size() > 0;
    }
}
