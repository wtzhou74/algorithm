package arrayString;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete as many transactions as 
 * you like (i.e., buy one and sell one share of the stock multiple times).
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
 * 
 * */
public class BestTime2BuyAndSellStockII {

	public static int maxProfit(int[] prices) {
		if (prices.length == 0) return 0;
        int profit = 0, i = 0;
        while (i < prices.length - 1) {
            if (prices[i + 1] > prices[i]) {
                profit += (prices[i + 1] - prices[i]);
            }
            i++;
        }
        return profit;
    }
	
	public static void main(String[] args) {
		int[] prices = {7,1,5,3,6,4};
		maxProfit(prices);
	}
}
