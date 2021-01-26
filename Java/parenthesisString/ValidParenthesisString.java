package parenthesisString;

import java.util.HashMap;
import java.util.Map;

//Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether this string is valid. We define the validity of a string by these rules:
//
//Any left parenthesis '(' must have a corresponding right parenthesis ')'.
//Any right parenthesis ')' must have a corresponding left parenthesis '('.
//Left parenthesis '(' must go before the corresponding right parenthesis ')'.
//'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
//An empty string is also valid.
//Example 1:
//Input: "()"
//Output: True
//Example 2:
//Input: "(*)"
//Output: True
//Example 3:
//Input: "(*))"
//Output: True
//Note:
//The string size will be in the range [1, 100].
public class ValidParenthesisString {

	// ‘*’可以替换三种： ’）‘， ’（‘， ''
	//		==> 同时考虑两种情况： 1） 从左往右， 把所有的 ‘*’换成‘（’，计算balance
	//			 2） 从右往左， 把所有的 ‘*’换成‘）’，计算balance
	//		===> 只有两者同时满足，整体才 balance
	
	// 重定义问题
	// 1- There are more open parenthesis but we have enough '*' so we can balance the parenthesis with ')'
	// 2- There are more close parenthesis but we have enough '*' so we can balance the parenthesis with '('
	// 3- There are as many '(' than ')' so all parenthesis are balanced, we can ignore the extra '*'
	// 结合1）2）实际左跟右是一样多的了
	// Time: O(n)
	// Space: O(1)
	public boolean checkValidString(String s) {
        if (s.length() == 0)
            return true;
        char[] chars = s.toCharArray();
        int leftBalance = 0;
        int rightBalance = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(' || chars[i] == '*'){
                leftBalance++;
            } else
                leftBalance--;
            if (leftBalance < 0) return false;
            
        }
        // We can already check if parenthesis are valid
        //如果 == 0; 直接就可断定 parenthesis 有效
        if (leftBalance == 0) return true;
        //对于大于0的情况，还要校验从右往左的情况
        
        for (int j = chars.length - 1; j >= 0; j--) {
                
            if (chars[j] == ')' || chars[j] == '*')
                rightBalance++;
            else
                rightBalance--;
            if (rightBalance < 0) return false;
        }
        
        // the remaning cases are: 1) leftBalance > 0; 2) rightBalance == 0 or rightBalance > 0
        return true;
        
    }
	
	// Another O(N) solution
	// '*' 可以是 （*） ==> 被用作empty ==> 设置 * 也是 +1； ==> > 0
	// '*' 被替代 ’)‘,'('  ===> Balance == 0
	
	public boolean solution2 (String s) {
		if (s.length() == 0) return true;
		int min = 0, max = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				max++;
				min++;// 用以抵消 * 号
			} else if (s.charAt(i) == ')') {
				min--;
				max--;
			} else {
				max++;
				min--;// 用以判断 * 是否有作用
			}
			if (max < 0) return false; // ")" 
			
			// max 跟min 不一样是有 * 导致的
			min = Math.max(max, 0); // (*); 因为 * 的时候 min--，故如果min < 0 了 （max > 0）,'*' 没作用
		}
		
		return min == 0; // balance, ()*  //最后 max大于0没关系，因为是 *号
	}
	
	
	// DP， 典型的 Decision-making 问题， ==> 即在当前状态下，下一个选哪个 （*替换 ')' 或 替换 '（' 或 不替换）
	public boolean dynamicSolution(String s) {
        if (s.length() == 0)
            return true;
        Map<String, Boolean> memo = new HashMap<>();
        return dp(s, 0, 0, memo);
    }
    
    public boolean dp(String s, int idx, int count, Map<String, Boolean> memo) {
        if (count < 0) return false;
        if (idx == s.length() && count == 0)
            return true;
        if (idx >= s.length())
            return false;
        String key = s + "/" + idx + "/" + count;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        boolean res = true;
        if (s.charAt(idx) == '(') {
            res = dp(s, idx + 1, count+1, memo);
        } else if (s.charAt(idx) == ')') {
            res = dp(s, idx + 1, count - 1, memo);
        } else {
        	// decision-making, 三种情况
            res = dp(s, idx + 1, count, memo) ||
                dp(s, idx + 1, count + 1, memo) ||
                dp(s, idx + 1, count - 1, memo);
        }
        memo.put(key, res);
        return res;
    }
    
    // TODO: DP Array
}
