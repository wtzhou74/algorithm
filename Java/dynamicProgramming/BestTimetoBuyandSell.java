package dynamicProgramming;

//Say you have an array for which the ith element is the price of a given stock on day i.
//
//If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.
//
//Note that you cannot sell a stock before you buy one.
//
//Example 1:
//
//Input: [7,1,5,3,6,4]
//Output: 5
//Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
//             Not 7-1 = 6, as selling price needs to be larger than buying price.
//Example 2:
//
//Input: [7,6,4,3,1]
//Output: 0
//Explanation: In this case, no transaction is done, i.e. max profit = 0.

public class BestTimetoBuyandSell {

	// 问题的本质是找 “最低点/最高点”  ， 因为 所有的天数种，"只能买1卖1"， 不存在买卖多次
	// Greedy算法，每次取（局部）最优，最后达到最终最优
	public int maxProfit(int[] prices) {
		// Brute Force
		int res = 0;
		for (int i = 0; i < prices.length; i++) {
		    for (int j = i + 1; j < prices.length; j++) {
		        int temp = prices[j] - prices[i];
		        if (prices[j] - prices[i] > 0) {
		            res = Math.max(res, temp);
		        }
		    }
		}
        return res;
	}
	
	// TIME: O(n)  ==> 只需记录最低点，同时计算其之后的maxProfit
	// DP算法
	public int sol2(int[] prices) {
		if (prices.length == 0 || prices == null) return 0;
        int max = 0;
        int min = prices[0];
        for (int i = 0; i < prices.length; i++) {
            
            // current min
            if (prices[i] < min) { //每次找出当前最低，然后往后看，买股票跟前面价格就没关系了
                min = prices[i];
            }
            // 不能直接这样，这样有可能最开始的最大值记录下了，而这个最大值是不能用的，因为只能先买
//            if (prices[i] > max) {
//            	max = prices[i];
//            }
            // current max
            if (prices[i] - min > max) {
                max = prices[i] - min;
            }
        }
        return max;
        
        // int min = Integer.MAX_VALUE;
        // int max = 0;
        // for (int i = 0; i < prices.length; i++) {
        //     if (prices[i] < min) {
        //         min = prices[i];
        //     } else if (prices[i] - min > max) {
        //         max = prices[i] - min;
        //     }
        // }
        // return max;
	}
	
	// 递归写法
	public int maxProfit1(int[] prices) {
        if (prices.length <= 1)
            return 0;
        return dp(prices, 0, Integer.MAX_VALUE);
        //return max;
    }
    
    //int max = 0;
    public int dp(int[] prices, int i, int minPrice) {
        if (i >= prices.length) {
            return 0;
        }
        
        int profit = prices[i] - minPrice;
        //max = Math.max(profit, max);
        minPrice = Math.min(minPrice, prices[i]);
        int next = dp(prices, ++i, minPrice); // 不能 i++, 因为此时 i先被用，再++，多以递归时用的是一样的值，溢出
        return Math.max(profit, next);
        
    }
    
    int total = 0;
    public void dp1 (int[] prices, int i, int min) {
        if (i >= prices.length)
            return;
        int profit = prices[i] - min;
        total = Math.max(total, profit);
        min = Math.min(min, prices[i]);
        dp(prices, i + 1, min);
        
        //total = Math.max(profit, next);
    }
}
