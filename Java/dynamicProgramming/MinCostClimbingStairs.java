package dynamicProgramming;

//On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).
//
//Once you pay the cost, you can either climb one or two steps. You need to find minimum cost to reach the top of the floor, and you can either start from the step with index 0, or the step with index 1.
//
//Example 1:
//Input: cost = [10, 15, 20]
//Output: 15
//Explanation: Cheapest is start on cost[1], pay that cost and go to the top.
//Example 2:
//Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
//Output: 6
//Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].
//Note:
//cost will have a length in the range [2, 1000].
//Every cost[i] will be an integer in the range [0, 999].

public class MinCostClimbingStairs {

	public int minCostClimbingStairs(int[] cost) {
        if (cost.length < 2) return 0;
        int[] dp = new int[cost.length + 1];
        dp[0] = cost[0];
        dp[1] = cost[1]; 
        // 整体关系 i =  min（i-1, i-2） + 当前值
        
        // 第一种写法  ==> 多记录一个，把走到 i - 1的cost放到 dp[i]中， 所以，对 idx=1的cost, 其实际是 dp[2] = min(1, 2) + 0
        				// !!!!当 i = len+1的时候，其cost是0
        for (int i = 2; i <= cost.length; i++) {
            int val = 0;
            if (i < cost.length) /// ！！！！ i == len , cost = 0, 因为当前dp值只是用于存放结果的，而且 cost[len]会溢出
                val = cost[i];
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + val;
        }
        return dp[cost.length];
        
        // 第二种写法，只记录到 dp[len-1]的为止，此时它的值是  min(len-1, len-2)
        // for (int i = 2; i < cost.length; i++) {
        //     dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        // }
        // return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }
}
