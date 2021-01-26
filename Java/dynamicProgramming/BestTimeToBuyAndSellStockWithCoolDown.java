package dynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Say you have an array for which the ith element is the price of a given stock on day i.
//
//Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:
//
//You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
//After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
//Example:
//
//Input: [1,2,3,0,2]
//Output: 3 
//Explanation: transactions = [buy, sell, cooldown, buy, sell]

// REFER TO:
// Great explanation: https://leetcode.com/explore/interview/card/top-interview-questions-hard/121/dynamic-programming/862/discuss/456719/Sharing-my-thought-process.-Thanks-to-@npvinhphat's-state-machine-thinking-solution
// FSM（Finite-state Machine） https://leetcode.com/explore/interview/card/top-interview-questions-hard/121/dynamic-programming/862/discuss/442941/Java-State-Machine-Solution-+-Diagram-+-Explanation-or-O(n)-TC-and-O(1)-SC
public class BestTimeToBuyAndSellStockWithCoolDown {

	// !!!! 画出状态图是最好的，方便理清各个状态下的关系 
	// !!!!(这里的buy/sell/cooldown 不是状态，是到达某个状态的行为 ！！！参考 FSM-***.jpg) 
	// 子问题， 每种状态下不同动作所得的结果， dp[day][state]
	public int maxProfit(int[] prices) {
        if (prices.length == 1 || prices.length == 0) return 0;
        Map<String, Integer> memo = new HashMap<>();
        return helper(prices, 0, 0, memo);
    }
	
	// Finite-state diagram 
	// s2: This is just a (temp) state. Sole purpose of this state is to go to state s0 with one rest.
	public int fsm(int[] prices) {
		// value of initial states at very beginning
		int s0 = 0; // cooldown, no stock initially
		int s1 = -prices[0]; // buy, Initial buy is negative net gain
		int s2 = 0; //sell,  No stock initially 
		
		for (int i = 1; i < prices.length; i++) {
			// 只能开始先买
			s1 = Math.max(s0 - prices[i], s1); // 根据FSM， s1 可以从 s1 或者 s0 + buy 转换到
			// update s0
			// NOTE:  If we put s0 first, then s0 will be changed before using it for calculating s1, so we should execute s1 first
			//		or set a temp var (int temp = s0) to store s0, just like doing swap(x, y);
			s0 = Math.max(s0, s2); 
			//s1 = Math.max(s0 - prices[i], s1); // 根据FSM， s1 可以从 s1 或者 s0 + buy 转换到
			s2 = s1 + prices[i];
		}
		
		return Math.max(s2, s0);
	}
	
	// 多状态转换/多情况  ===》 多个DP数组
	// DP数组的时候   ===》 设置单个变量  ==》 重复 pre = cur 以达到dp数组的目的  ====》 节省空间
	// dpBuy[i] 表示在 i 时间点buy时的最大收益
	public int dpsol(int[] prices) {
		int[] dp = new int[prices.length + 1];
		if (prices.length == 1 || prices.length == 0) return 0;
        // Map<String, Integer> memo = new HashMap<>();
        // return helper(prices, 0, 0, memo);
        
		// 可以用 int[][] dp = new int[n][3];// 即每天都有三种状态种的任何一种
        int[] dpBuy = new int[prices.length];
        int[] dpSell = new int[prices.length];
        int[] dpCool = new int[prices.length];
        dpBuy[0] = -prices[0];
        dpSell[0] = 0;
        dpCool[0] = 0;
        
//         int preb = -prices[0];
//         int pres = 0;
//         int prec = 0;
        
//         int currb;
//         int currc;
//         int currs;
        
        for (int i = 1; i < prices.length; i++) {
        	
        	// 以下转换正好会 recursion 解法中的相反
            dpCool[i] = Math.max(dpCool[i - 1], dpSell[i - 1]);
            dpSell[i] = Math.max(dpCool[i - 1], dpBuy[i-1] + prices[i]); // Per FSM, it can be replaced with dpSell[i] = dpBuy[i-1] + prices[i];
            dpBuy[i] = Math.max(dpCool[i - 1] - prices[i], dpBuy[i - 1]);
//             currb = Math.max(preb, prec - prices[i]); // CANNO go from s to b without c
//             currc = Math.max(prec, pres);// preb must be less prec or pres
//             currs = Math.max(prec, preb + prices[i]);
            
//             preb = currb;
//             prec = currc;
//             pres = currs;
            
            // 另外一种写法
//            dp[0][1] = -prices[0];
//            //int state = 0;
//            // sell: 0; buy: 1; cooldown: 2
//            for (int i = 1; i < prices.length; i++) {
            	// 根据FSM图，现在可以sell-0了，之前可以是cooldown,或者buy, 而对当前dp[i][0]的值，
            	//由于是sell,当然是加上当前price的值，所以 +price[i],而不是因为之前是buy,而用 -price[i], 
            			//要减也是减price[i-1],因为是在上一天买的
//                dp[i][0] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);
            	//同样，现在可以buy-1, 那么之前状态可以是cooldown过来，所以-prices[i], 或者继续处于buy-1状态啥也没做
//                dp[i][1] = Math.max(dp[i - 1][2] - prices[i], dp[i - 1][1]);
            	// 现在可以cooldown了，之前可以是cooldown,或者刚sell掉，这里i时候，此时跟prices[i]没关系
//                dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][0]);
//            }
//            int n = prices.length;
//            return Math.max(dp[n - 1][0],Math.max(dp[n - 1][1], dp[n - 1][2]));
        }
        
        //return Math.max(pres, prec);
        return Math.max(dpCool[prices.length - 1], dpSell[prices.length - 1]);
	}
	
	// 递归 写法   ==> 画出状态图是最好的，方便理清各个状态下的关系
	public int maxProfit3(int[] prices) {
        if (prices.length <= 1) return 0;
        int[][] memo = new int[prices.length][3];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dp(prices, 0, 0, memo);
    }
    int[] states = new int[] {1, -1, 0};// 1: sell; -1: buy; 0: cooldown
    public int dp(int[] prices, int i, int state, int[][] memo) {
        if (i >= prices.length) {
            return 0;
        }
        int stateIdx = 0;
        if (state == 1)
            stateIdx = 0;
        else if (state == -1)
            stateIdx = 1;
        else stateIdx = 2;
        if (memo[i][stateIdx] != - 1)
            return memo[i][stateIdx];
        int maxfit = 0;
        // initial state
        // if (i == 0) { // 更state=0是一样的，所以这里我们不重复写
        //     // buy/cool and next to cool
        //     int s0 = dp(prices, i+1, -1, memo) - prices[i];// buy and next to cool
        //     int s1 = dp(prices, i+1, 0, memo); // cool and next to buy
        //     maxfit = Math.max(s0, s1);
        // } else 
        if (state == 1) {
            // from sell
            int s0 = dp(prices, i+1, 0, memo);// 从“卖”过来,只能 cool，即参数state=0，告诉下一步我是 0 过来的
            							// 之前sell时候 +price, 所以不需要再在这里 +price
            maxfit = s0;
        } else if (state == -1) {
            // 从“买”过来的，当前这一步可以 1) cooldown, 2） 也可以 sell
            int s0 = dp(prices, i+1, -1, memo); // 当前cooldown, 但下一步的状态还是之前的，状态不变，即还是 -1 ！！！
            										// 继续告诉下一步你可以卖可，但可以选择cooldown
            int s1 = dp(prices, i+1, 1, memo) + prices[i]; // 卖掉，然后告诉下一步我卖了， state = 1
            					// 同时当前(天)我卖掉了，所以价格上要 +price[i]
            maxfit = Math.max(s0, s1);
        } else if (state == 0) {
            // 初始状态/ cooldown
            int s0 = dp(prices, i+1, -1, memo) - prices[i];// 买， 告诉下一步我是 -1 过来的，
            					//由于我在第i填买了，所以此时要 - prices[i]
            int s1 = dp(prices, i+1, 0, memo); // 继续cooldown, 并告诉下一步我是 0 过来的
            maxfit = Math.max(s0, s1);
        }
        memo[i][stateIdx] = maxfit;
        return maxfit;
    }
    
    
	// RECURSION + MEMO
    public int helper(int[] prices, int state, int i, Map<String, Integer> memo) {
        if (i >= prices.length) {
            return 0;
        }
        
        String key = String.valueOf(i) + "/" + String.valueOf(state);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int count = 0;
        
        // What are the options from state1 to state2 !!!!!!!!
    	if (state == 1) {// buy before, now cooldown or sell
            //int buy = helper(prices, 1, i + 1) - prices[i];
            int sell = helper(prices, -1, i + 1, memo) + prices[i];
            int cooldown = helper(prices, 1, i + 1, memo);// cannot be 0, !!!!! 1 here means I am still HOLDING the stock and am waiting for sell !!!!!!!!!!
            count = Math.max(sell, cooldown);
        } else if (state == -1) {// sell before, now cooldown
            count = helper(prices, 0, i + 1, memo);            
        } else if (state == 0) { // cooldown before, now cooldown or buy
            int buy = helper(prices, 1, i + 1, memo) - prices[i];
            //int sell = helper(prices, -1, i + 1) + prices[i];
            int cooldown = helper(prices, 0, i + 1, memo);
            count = Math.max(buy, cooldown);
        }
        
    	memo.put(key, count);
        return count;
    }
    
    
    public static void main(String[] args) {
    	int res = new BestTimeToBuyAndSellStockWithCoolDown().maxProfit(new int[] {1 , 2, 4});
    	System.out.println(res);
    }
}
