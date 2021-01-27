package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//A group of friends went on holiday and sometimes lent each other money. For example, Alice paid for Bill's lunch for $10. Then later Chris gave Alice $5 for a taxi ride. We can model each transaction as a tuple (x, y, z) which means person x gave person y $z. Assuming Alice, Bill, and Chris are person 0, 1, and 2 respectively (0, 1, 2 are the person's ID), the transactions can be represented as [[0, 1, 10], [2, 0, 5]].
//
//Given a list of transactions between a group of people, return the minimum number of transactions required to settle the debt.
//
//Note:
//
//A transaction will be given as a tuple (x, y, z). Note that x ≠ y and z > 0.
//Person's IDs may not be linear, e.g. we could have the persons 0, 1, 2 or we could also have the persons 0, 2, 6.
//Example 1:
//
//Input:
//[[0,1,10], [2,0,5]]
//
//Output:
//2
//
//Explanation:
//Person #0 gave person #1 $10.
//Person #2 gave person #0 $5.
//
//Two transactions are needed. One way to settle the debt is person #1 pays person #0 and #2 $5 each.
//Example 2:
//
//Input:
//[[0,1,10], [1,0,1], [1,2,5], [2,0,5]]
//
//Output:
//1
//
//Explanation:
//Person #0 gave person #1 $10.
//Person #1 gave person #0 $1.
//Person #1 gave person #2 $5.
//Person #2 gave person #0 $5.
//
//Therefore, person #1 only need to give person #0 $4, and all debt is settled.
public class OptimalAccountBalancing {

	// 这道题的本质时给一组数（正/负），其和=0， 求出最小次数的组合（正负数）  ==> backtracking
	public int minTransfers(int[][] transactions) {
        if (transactions == null || transactions.length == 0)
            return 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] transaction : transactions) {
            int p1 = transaction[0];
            int p2 = transaction[1];
            int money = transaction[2];
            map.put(p1, map.getOrDefault(p1, 0) - money);
            map.put(p2, map.getOrDefault(p2, 0) + money);
        }
        
        List<Integer> pos = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();
        // 分出正/负组
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 0)
                pos.add(entry.getValue());
            else if (entry.getValue() < 0)
                neg.add(entry.getValue());
        }
        
        //Collections.sort(pos, (a, b) -> b - a);
        //Collections.sort(neg, (a, b) -> a - b);
        
        backtrack(pos, neg, 0, 0);
        return min;
        
    }
    
    int min = Integer.MAX_VALUE;
    // 开始backtracking 正负list, 看如何组合能次数最小，使最终=0
    public void backtrack(List<Integer> pos, List<Integer> neg, int i, int count) { // 选用正list为参照，递归i
        if (i == pos.size()) {
            min = Math.min(count, min);
            return;
        }
        int positive = pos.get(i);  // 把当前值写出来， 用于后面trackback方便取！！！！
        for (int j = 0; j < neg.size(); j++) { // 这里从0开始是，走过了一个负数后，其与正数的和不一定等于0，所以当前位置的数还得在重新用于计算的！！
        						/// [10, 9], [-15, -1, -3], 但走到 -5的时候， 10+（-15） = -5, 即负数列表的第0位置变为 -5，需要再用于计算
            if (neg.get(j) == 0) continue; // 为0，就可跳过（这里的0 是计算后得到的）
            int negative = neg.get(j);
            // if (pos.get(i) + neg.get(j) == 0) {
            //     backtrack(pos, neg, i + 1, j + 1, count+1);
            //} else 
            int balance = positive + negative; 
            if (balance > 0) { 
                pos.set(i, balance); // 更新 正数列表
                neg.set(j, 0);  // 大于0， 说明neg列表上当前位置可以变为0了，比正数小
                backtrack(pos, neg, i, count + 1);
                //pos.set(i, positive);
                //neg.set(j, negative);
            } else {
                neg.set(j, balance);
                pos.set(i, 0); // 小于0， 说明负数更大，正数那边变为0, 同时更新负数列表这边的值
                backtrack(pos, neg, i + 1, count + 1);
                //pos.set(i, positive);
                //neg.set(j, negative);
            }
            pos.set(i, positive); // 恢复回来的值，用于trackback
            neg.set(j, negative); // backtrack就是回到上一层的位置，用原来的值，走for i++ 后的path (原来是往 i 走)
            // count--; // 在递归return回来之后， count已经回到上一个值了， 所以，count不需要--
        }
    }
    
    public static void main(String[] args) {
    	int[][] transactions = new int[][] {
    		{0,1,10},
    		{1,0,1},
    		{1,2,5},
    		{2,0,5}
    	};
    	new OptimalAccountBalancing().minTransfers(transactions);
    }
}
