package amazon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
//
//Note:
//
//The same word in the dictionary may be reused multiple times in the segmentation.
//You may assume the dictionary does not contain duplicate words.
//Example 1:
//
//Input: s = "leetcode", wordDict = ["leet", "code"]
//Output: true
//Explanation: Return true because "leetcode" can be segmented as "leet code".
//Example 2:
//
//Input: s = "applepenapple", wordDict = ["apple", "pen"]
//Output: true
//Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
//             Note that you are allowed to reuse a dictionary word.
//Example 3:
//
//Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
//Output: false

public class WordBreak {

	
	// Dynamic programming
	public boolean wordBreak(String s, List<String> wordDict) {
		if (wordDict.size() == 1)
			return s.equals(wordDict.get(0));
		boolean[] dp = new boolean[s.length() + 1]; // dp[i] means that the substring(0, i+1) meets the requirement
		dp[0] = true;
		// s1 (0,j) and s2(j+1,i) 
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				// subproblems relation
				if (wordDict.contains(s.substring(j, i)) && dp[j]) {
					dp[i] = true;
					break; // 不用再判读了， 有一个是 true就可以了, 说明 [0, i]之间的串是可以的
				}
			}
		}
		
		return dp[s.length()];
	}
	
	//这个不是backtracking算法
	public boolean backtracking(String s, List<String> wordDict) {
		Map<Integer, Boolean> memo = new HashMap<>();
		
		// set.contains() 是O（1）， 而 list.contains()是O（n）
		Set<String> dic = new HashSet<>(wordDict);
		//Collections.addAll(dic, wordDict);
		
		// Better choice 
		// Boolean[] memo = new Boolean[s.length]; // since start/key is the value within (0, length)
		return helper(0, s, wordDict, memo);
	}
	
	// 要记录一个 start的索引，用于取substring
	public boolean helper(int start, String s, List<String> wordDict, Map<Integer, Boolean> memo) {
		if (start == s.length()) {
			return true;
		}
		if (memo.containsKey(start)) return memo.get(start);
		for (int i = start; i <= s.length(); i++) { // for (int end = start, end <= s.length(); end++)
			if (wordDict.contains(s.substring(start, i))) {
				boolean res = helper(i, s, wordDict, memo); 
				if (res) {
					memo.put(start, true); // 这里可以换成 string, 但int-start 更省空间
					return true;
				}
			}
		}
		memo.put(start, false);
		return false;
	}
	
	public boolean wordBreakBFS(String s, List<String> wordDict) {

		Set<String> wordDictSet=new HashSet<>(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[s.length()];
        queue.add(0);
        while (!queue.isEmpty()) {
            int start = queue.remove();
            // ！！！！！！一定要先判断，因为每一层会对应多个word, 每个word都会有自己的一个last idx,各自不一样，cat,cats
            // 如果 visited[start] == 1; 然后对任何queue中任何元素都进行 for,在for 里面判断是否需要offer
            // ==> 这样对  "aaaaaaaa" 会TLE， 因为第2，3，4，... 的a都要进queue，因为第一次访问时，后面的 a 都是unvisited
            // 如果 先if, 这样即便 第2，3，4，... 都入 queue, 但由于状态都设置为 visited,出栈时就被过滤掉了 ！！！！
            if (visited[start] == 0) { // 需要visited，因为每一层的words不一定都一样
                for (int end = start + 1; end <= s.length(); end++) {
                    if (wordDictSet.contains(s.substring(start, end))) {
                        queue.add(end); // 这个end 就是第i层中，所有符合条件的substring的最后一个位置
                        				// 接下来，进入到第 i + 1 层，从上一层所有符合条件的string的最后一个位置
                        			// 每个开始判断，确定接下来所有符合条件的word的最后一个字符的位置，循环进入下一层
                         ///   对 catsanddog, cat, cats 是第一层，入q的是 t， s的位置，进入下一层后，就从这两处开始往下进行
                         //  存不是word的字符，比如这里的c,a没有意义
                        if (end == s.length()) {
                            return true;
                        }
                    }
                }
                visited[start] = 1;
            }
        }
        return false;
    }
	
	public static void main(String[] args) {
		String s = "catsanddog";
		List<String> wordDict = Arrays.asList("cat", "cats", "and", "dog", "sand");
		new WordBreak().wordBreakBFS(s, wordDict);
	}
}
