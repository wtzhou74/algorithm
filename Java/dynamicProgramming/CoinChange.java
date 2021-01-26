package dynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
//
//Example 1:
//
//Input: coins = [1, 2, 5], amount = 11
//Output: 3 
//Explanation: 11 = 5 + 5 + 1
//Example 2:
//
//Input: coins = [2], amount = 3
//Output: -1
//Note:
//You may assume that you have an infinite number of each kind of coin.
public class CoinChange {

	// WRONG!!!
	public int wrongSol(int[] coins, int amount) {
        if (coins.length == 0) return -1;
        Arrays.sort(coins);
        int total = 0;
        int times = 0;
        for (int i = coins.length - 1; i >= 0;) {
            while (total + coins[i] <= amount) { 
            	// When you keep adding the largest one, you may cannot find the remaining in the array by adding or coin denomination
                total += coins[i];
                times++;
            }
            if (total == amount) return times;
            i--;
        }
        System.out.println(times);
        if (total != amount) return -1;
        return times;
    }
	
	public int coinChange0(int[] coins, int amount) {
        if (coins == null || coins.length == 0)
            return 0;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); //初始值用以最后判断有没有结果， ！！！这里不能用 MAX, 否则下面 +1 会溢出
        dp[0] = 0;
        
        // 对每个demonination，都有 coins.len种选择
        for (int i = 1; i < amount + 1; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) { // ！！！！如果当前面值比当前的amount小，没意义不用判断
                	// ！！ i 是当前的 amount, 所以下面用 i - coins[j]
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);// 所以上面初始化不能用MAX_VALUE
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
	
	public int dpArraySolution(int[] coins, int amount) {
		if (coins.length == 0) return -1;
		int[] dp = new int[amount + 1];// each dp is a result for a specific "amount", considering 0
		dp[0] = 0;// when amount is 0
		// Initialize DP value
		for (int i = 1; i <= amount; i++) {
			dp[i] = Integer.MAX_VALUE;// We need to do MIN operation
		}
		// This for loop is NOT required
		for (int coin : coins) {
			if (coin > amount) {// For those "amount" = coin which is bigger than real amount
				continue;
			}
			dp[coin] = 1;
		}
		for (int i = 1; i <= amount; i++) {
			// for each amount/dp, the candidates coin
			for (int j = 0; j < coins.length; j++) {
				// THERE IS NO NEED TO CHECK same COINs[j] for SERVERAL TIMES, dp[previousAmount] has been calculated
				if (coins[j] > i) continue;// denomination is bigger than current "amount", so no need to check this coin
				
				// NOTE: dp[i-coins[j]] + 1 will overflow if its value is Integer.MAX_VALUE!!!!!!
				// Alternative: Initialize dp[i] to amount + 1 instead of Integer.MAX_VALUE
				if (coins[j] <= i && dp[i - coins[j]] != Integer.MAX_VALUE) {
					// dp[i] has an initial value, dp[i] might has multiple values
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);// This is why we need to initialize
					// dp[i - coins[j]]
				}				
			}
		}
		return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
	}
	
	
	// Time: O(S * n)  ; S is the amount, n is the number of denomination count
	// 参考 CoinChange.png
    public int coinChange2(int[] coins, int amount) {
        if (coins == null || coins.length == 0)
            return 0;
        Map<Integer, Integer> memo = new HashMap<>();
        //int[] memo = new int[amount];
        return dp(coins, amount, memo);
        //return res == Integer.MAX_VALUE ? -1 : res;
    }
    
    //int res = Integer.MAX_VALUE;
    public int dp(int[] coins, int target, Map<Integer, Integer> memo) {
        if (target < 0)
            return -1;
        if (target == 0) {
            //res = Math.min(res, count);
            return 0;
        }
        if (memo.containsKey(target)) {
            return memo.get(target);
        }
        int min = Integer.MAX_VALUE; // 用以取下面 coins.len种选择的最小
        for (int i = 0; i < coins.length; i++) {
            int res = dp(coins, target - coins[i], memo); // 不在这里直接 +1， 只有在符合条件情况下才做
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        if (min == Integer.MAX_VALUE) {
            memo.put(target, -1);
        } else {
            memo.put(target, min);
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
	
	public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) return -1;
        Map<Integer, Integer> cache = new HashMap<>();
        Arrays.sort(coins);
        //cache.put(0, 0);
        int res = helper(coins, amount, 0, 0, coins.length - 1, cache);
        return res;
    }
    private int res = Integer.MAX_VALUE;
    public int helper(int[] coins, int amount, int times, int total, int i, Map<Integer, Integer> cache) {
        if (total == amount) {
            return times;            
        }
        if (total > amount) return -1;
        if (cache.containsKey(total)) return cache.get(total);
        for (; i >= 0; i--) {
        	total += coins[i];
            if (total <= amount) {
                int count = helper(coins, amount, times + 1, total, i, cache);
                if(count != -1) 
                	{
                		res = Math.min(res, count);
                		System.out.println(res);
                	}
               
            }
            total -= coins[i];
        }
        if (res == Integer.MAX_VALUE) {
            cache.put(total, -1);
        } else {
            cache.put(total, res);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
	
    public int coinChangeTest(int[] coins, int amount) {
        HashMap<Integer, Integer> map = new HashMap<>(); //map.key = remaining amount, map.value = least coins to sum up to the amount
        map.put(0,0);
        return helperTest(coins, amount, map);
    }
    private int helperTest(int[] coins, int amount, HashMap<Integer, Integer> map) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        if (map.containsKey(amount)) {
            return map.get(amount);
        }
        int num = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (amount - coin >= 0) {
                int temp = helperTest(coins, amount-coin, map);
                if (temp != -1) {
                    num = Math.min(num, temp);
                    System.out.println(num);
                }
            }
        }
        if (num == Integer.MAX_VALUE) {
            map.put(amount, -1);
            return num = -1;
        }
        map.put(amount, num+1);
        return num+1;
    } 
    
    

	
	public static void main(String[] args) {
		new CoinChange().dpArraySolution(new int[] {2}, 3);
	}
}
