package dynamicProgramming;

//Say you have an array for which the ith element is the price of a given stock on day i.
//
//Design an algorithm to find the maximum profit. You may complete at most two transactions.
//
//Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
//
//Example 1:
//
//Input: [3,3,5,0,0,3,1,4]
//Output: 6
//Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
//             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
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

public class BestTimeToBuyAndSellStockIII {

	public int maxProfitWRONG(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        // one transaction
        int minPrice = Integer.MAX_VALUE;
        int maxProfit1 = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (maxProfit1 < prices[i] - minPrice) {
                maxProfit1 = Math.max(maxProfit1, prices[i] - minPrice);
            }
        }
        System.out.println(maxProfit1);
        // two transactions
        int hold = -prices[0];
        int cash = 0;
        // 下面出来的结果是基于可以 multiple transactions，不仅仅只有两次
        for (int i = 0; i < prices.length; i++) {
            hold = Math.max(cash - prices[i], hold);
            cash = Math.max(hold + prices[i], cash);
        }
        System.out.println(cash);
        return Math.max(maxProfit1, cash);
    }
	
	// 思路是：  ==> dp[] ==> 记录在某天的时候的最大收益
	//		1） 一次transaction from i to n-1 这些天的各自对应的最大收益，更新 profit[i]
	//		 2） 由于允许2次， 那么再次更新profit[i], 这时候更新取最大的 profit[i-1], 同时从day i开始第二次
	//			==> 交易，以此更新 profit[i]  ==> 不能重叠，所以看下面的逻辑顺序
	// 如果先左后右， refer to: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/solution/
	public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int n = prices.length;
        int[] profit = new int[n];
        int maxPrice = prices[n - 1];
        // 从右往左开始第一次transaction， 得到dp[i]的值
        // ！！！不能从左往右，因为下面也是从左往右，那样的话会取到重复值 [1,4,2],会得到 6 的结果
        for (int i = n - 2; i >= 0; i--) {
        	//从右往左的时候， “先卖后买”， 所以要在高位卖
            profit[i] = Math.max(profit[i + 1], maxPrice - prices[i]);// 从i买 i+1卖
            maxPrice = Math.max(maxPrice, prices[i]);
        }
        // 然后从左往右，再次更新（second transaction） profit[i]
        int minPrice = prices[0];
        for (int i = 1; i < n; i++) {
            profit[i] = Math.max(profit[i - 1], prices[i] - minPrice + profit[i]);
            minPrice = Math.min(prices[i], minPrice);
        }
        return profit[n - 1];
    }
	
	// 一次循环实现
	public int maxProfit1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int price1 = Integer.MAX_VALUE;
        int profit1 = 0;
        int price2 = Integer.MAX_VALUE;
        int profit2 = 0;
        // 
        for (int i = 0; i < prices.length; i++) {
            price1 = Math.min(price1, prices[i]);
            profit1 = Math.max(profit1, prices[i] - price1);
            // 第二次买的时候，计算profit要注意， 
            	// - 1） 价格比较 prices[i] - profit1, 当前的价格-上次盈利的钱 ==> 实际手上的收益  ==> 形成顺序
            	// -2 ) 算总收益的时候，即profit2的时候，就是当前价格 - price2了
            //  - ！！！这里不能直接 price2 = Math.min(price2, prices[i]);在算总收益，
            	//  ==> 这样会导致第一次买和第二次买重合（不允许的！）， 比如 [1,4,2], [1,4]会被算两遍
            price2 = Math.min(price2, prices[i] - profit1);
            profit2 = Math.max(profit2, prices[i] - price2);
        }
        //return Math.max(profit1, profit2);
        return profit2;
    }
}
