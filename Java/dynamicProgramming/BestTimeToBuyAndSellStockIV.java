package dynamicProgramming;

//Say you have an array for which the i-th element is the price of a given stock on day i.
//
//Design an algorithm to find the maximum profit. You may complete at most k transactions.
//
//Note:
//You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
//
//Example 1:
//
//Input: [2,4,1], k = 2
//Output: 2
//Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
//Example 2:
//
//Input: [3,2,6,5,0,3], k = 2
//Output: 7
//Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
//             Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
public class BestTimeToBuyAndSellStockIV {

	// dp[t][i]  ==> the maximum profit using at most t transactions till the i'th day.
	// 					- 注意：！！这里不是表示第t次交易一定发生在第 i 天， 而是表示 到第 i 天的时候，做最多 t次交易的最大收益
	//		==> 对每个允许的交易次数 0，1，2，...k，每个都对应 n 天，即在这 n 天内，最多可以做的 transaction，
	//     ===> 所以这里 用 dp[t][i], 而不是 dp[i][t]
	
	// 对 k 比天数的一半还多，那就直接累加前后两天之差 >0 的值即可
	
	// 状态转换公式: profit[t][i] = Math.max(profit[t][i-1], prices[i] - prices[j] + profit[t-1][j]);
	//					==> 交易的两天 i, j 不一定是连续的
	//        ===> 两种情况：  1） 第i天不交易，即 第 i-1就已经做了 t 次了， 即 profit[t][i-1]
	//						第 i 天，此时 第 i - 1肯定交易次数到 t 了，不管第 t 次交易是否发生在 第 j - 1天, 
	//						profit[t][i]表示的是到 第 i 天完成 t 次交易的最大利润值而已，不是表示第t次交易一定发生第 i 天。
	//					2） 上一次的交易是在 第 j 天做的，此时的profit就是 当时的profit + 现在的价格差
	
	public int maxProfit(int k, int[] prices) {
        if (prices.length <= 1)
            return 0;
        int n = prices.length;
        int maxProfit = 0;
        // 对 k 比天数的一半还多，那就直接累加前后两天之差 >0 的值即可，无需动态规划了
        if (k > n/2) {
            for (int i = 1; i < n; i++) {
                if (prices[i] - prices[i-1] > 0)
                    maxProfit += prices[i] - prices[i-1];
            }
            return maxProfit;
        }
        int[][] profit = new int[k+1][n];
        for (int i = 0; i <= k; i++) { // 交易次数
        	//int prev_diff = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++) { // 第几天
                if (i == 0 || j == 0) { // 没交易或者第0天做任何交易，收益都是0
                    profit[i][j] = 0;
                } else {
                    int max = 0;
                    // 对第 j 天的时候，根据 relation， 需要求出之前的最大 profit
                    //  因为两次交易不一定是连续的，所以我们要看 第 i - 1次交易发生在 第 j 天之前的任何情况，
                    // 取最大 profit
                    for (int x = 0; x < j; x++) {
                        max = Math.max(max, prices[j] - prices[x] + profit[i - 1][x]);
                    }
                    // 当下我们知道了 第 i - 1 次交易的最大profit了，那么根据关系，跟 profit[i][j-1],
                    //  即当天(第 j 天，此时 第 j - 1肯定交易次数到 i 了，不管第 i 次交易是否发生在 第 j - 1天)
                    //  啥也不做的情况， 看两个哪个有更大的profit
                    profit[i][j] = Math.max(max, profit[i][j - 1]);
                    
                    // 可用下面取到， 形成 Time(O（nk）) 
                    // refer to: https://www.techiedelight.com/find-maximum-profit-earned-at-most-k-stock-transactions/
//                  prev_diff = Math.max(prev_diff, profit[i - 1][j - 1] - price[j - 1]); // 那么第 i-1个操作就是“买”操作，
//					profit[i][j] = Math.max(profit[i][j - 1], price[j] + prev_diff); // 在第 j 天做的 “卖”的操作
                }
            }
        }
        return profit[k][n-1];
    }
}
