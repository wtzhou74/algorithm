package dynamicProgramming;

import java.util.Arrays;

//Say you have an array prices for which the ith element is the price of a given stock on day i.
//
//Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).
//
//Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
//
//Example 1:
//
//Input: [7,1,5,3,6,4]
//Output: 7
//Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
//             Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
//Example 2:
//
//Input: [1,2,3,4,5]
//Output: 4
//Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
//             Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
//             engaging multiple transactions at the same time. You must sell before buying again.
//Example 3:
//
//Input: [7,6,4,3,1]
//Output: 0
//Explanation: In this case, no transaction is done, i.e. max profit = 0.
// 
//
//Constraints:
//
//1 <= prices.length <= 3 * 10 ^ 4
//0 <= prices[i] <= 10 ^ 4
public class BestTimeToBuyAndSellStockII {

	// BestTimeToBuyAndSellStockWithCoolDown.java 类似， 画出状态转化图，完成下面算法
	//	但本题实际没必要DP解法， 参考 BestTimeToBuyAndSellStockII.png, 并参考下面非DP写法
	
	// 两种状态间的转换
	public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int hold = -prices[0]; // 注意初始值的设置
        int cash = 0;
        for (int i = 1; i < prices.length; i++) {
            hold = Math.max(cash - prices[i], hold);
            cash = Math.max(cash, hold + prices[i]);
        }
        return cash;
    }
	
	// DP, 跟 BestTimeToBuyAndSellStockWithCoolDown.java 类似
	public int maxProfitDP(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] - prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
        }
        return dp[prices.length - 1][1];
    }
	
	
	// DP 递归写法
	public int maxProfit1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int[][] memo = new int[prices.length][2]; // 不能用int[] memo, 因为跟状态有关
        			// 除非 定义 int[] buy; int[] sell 两个
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        return dp(prices, 0, 0, memo);
    }
    
    // state: 0-hold; 1-cash
    public int dp(int[] prices, int i, int state, int[][] memo) {
        if (i >= prices.length)
            return 0;
        if (memo[i][state] != -1)
            return memo[i][state];
        int max = 0;
        if (state == 1)
            max = Math.max(dp(prices, i + 1, 1, memo), dp(prices, i + 1, 0, memo) + prices[i]);
        else {
            max = Math.max(dp(prices, i + 1, 0, memo), dp(prices, i + 1, 1, memo) - prices[i]);
        }
        memo[i][state] = max;
        return max;
    }
    
    // 非DP 写法  ==> 参考 BestTimeToBuyAndSellStockII.png
    // 本题其实就是 累加所有相邻的 两个正数diff, 
    // 而对two-pass， 就是不停找 （lowest valley, highest peak）, 并累加， 等价下面的one-pass, 参考： https://leetcode.com/articles/best-time-to-buy-and-sell-stock-ii/
    public int maxProfit2(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int total = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - prices[i - 1] > 0)
                total += prices[i] - prices[i -1];
        }
        return total;
    }
}
