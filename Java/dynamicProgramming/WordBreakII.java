package dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.
//
//Note:
//
//The same word in the dictionary may be reused multiple times in the segmentation.
//You may assume the dictionary does not contain duplicate words.
//Example 1:
//
//Input:
//s = "catsanddog"
//wordDict = ["cat", "cats", "and", "sand", "dog"]
//Output:
//[
//  "cats and dog",
//  "cat sand dog"
//]
//Example 2:
//
//Input:
//s = "pineapplepenapple"
//wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
//Output:
//[
//  "pine apple pen apple",
//  "pineapple pen apple",
//  "pine applepen apple"
//]
//Explanation: Note that you are allowed to reuse a dictionary word.
//Example 3:
//
//Input:
//s = "catsandog"
//wordDict = ["cats", "dog", "sand", "and", "cat"]
//Output:
//[]

public class WordBreakII {

	public List<String> dp(String s, List<String> wordDict) {
		List<String> res = new ArrayList<>();
        if (wordDict.size() == 1) {
        	if (wordDict.contains(s)) {
        		res.add(s);
        	}
        	return res;
        }
        Map<Integer, List<String>> memo = new HashMap<>();
        return helper(0, s, wordDict, memo);
	}
	
	public List<String> helper(int start, String s, List<String> wordDict,
            Map<Integer, List<String>> memo) {
		if (memo.containsKey(start)) {
			return memo.get(start);
		}
		
		List<String> sol = new ArrayList<>(); // for each path(root to leaf), we will get a solution
		if (start == s.length()) {
			sol.add("");// reach the end of the string s
		}
		for (int i = start; i <= s.length(); i++) { // for (int end = start, end <= s.length(); end++)
            if (wordDict.contains(s.substring(start, i))) {
                List<String> remain = helper(i, s, wordDict, memo); // the sentences of remaining string
                // 这边递归返回的时候， return回来 （start, i）对，接着又 i++看新的 （start, i）是否符合
                // will be executed 2^n times ==> "aaaa","aaa", "aa", "a"
                for (String str : remain) { // 这边判断 “”, 是因为在 ==s.len的时候，我们设置了一个 “”
                	// for each sentences, we need add substring(start, i) to it
                    sol.add(s.substring(start, i) + (str.equals("") ? "" : " ") + str); // combining all strings together to get one solution
                }
            }
        }
		memo.put(start, sol);
		return sol;
	}
	
	//真正的backtracking， 这个对 “aaaaaaaaaa” 会出现 TLE的
	List<String> res = new ArrayList<>();
	public void backtracking(String s, List<String> wordDict, int start, int idx, List<String> sol, Map<Integer, List<String>> memo) {
        if (idx == s.length()) {
            String temp = "";
            for (String t : sol) {
                temp += t + " ";
            }
            res.add(temp.substring(0, temp.length() - 1));
            return;
        }
        if (memo.containsKey(start)) {
            sol.addAll(memo.get(start));
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (wordDict.contains(s.substring(start, i + 1))) {
                sol.add(s.substring(start, i + 1));
                backtracking(s, wordDict, i + 1, i + 1, sol, memo);
                sol.remove(sol.size() - 1); // 把sol的元素减掉1，存放新的
            }
        }
        memo.put(start, sol);
    }
	
	
	// tabular DP ==> TLE对worst case, 比如 “aaaaaaa”, 会达到 n^n， 因为 [i,j]之间的 a 会被重复计算
	//		而上面 top-down的solution可以 memorization
	public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if (wordDict.size() == 1) {
        	if (wordDict.contains(s)) {
        		res.add(s);
        	}
        	return res;
        }
        ArrayList<String>[] dp = new ArrayList[s.length() + 1]; //dp[i] means substring(0,i), how many sentences included
        //boolean[] dp = new boolean[s.length() + 1];
        ArrayList<String> initial = new ArrayList<>();
        initial.add(""); // MUST !!!!
        dp[0] = initial;
        for (int i = 1; i <= s.length(); i++) {
        	// sub(0, i)
        	ArrayList<String> sentences = new ArrayList<>();
        	for (int j = 0; j < i; j++) {
        		if (wordDict.contains(s.substring(j, i))) {
        			if (dp[j].size() > 0) {
        				for (String str : dp[j]) {
        					if (str.length() > 0) {
        						// for each sentences of sub(0, j), we need add substring(j, i) to it.
        						sentences.add(str + " " + s.substring(j, i));
        					} else {
        						sentences.add(s.substring(j, i));
        					}
        				}
        			}
        		}
        	}
        	dp[i] = sentences;
        }
        return dp[s.length()];
    }
	
	public List<String> wordBreak1(String s, List<String> wordDict) {
        if (s.length() == 0)
            return new ArrayList<>();
        Map<Integer, List<String>> dp = new HashMap<>();
        for (int i = 0; i <= s.length(); i++) {
            dp.put(i, new ArrayList<>());
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (wordDict.contains(s.substring(j, i))) {
                	List<String> aux = new ArrayList<>();
                    if (dp.get(j) != null) {
                        List<String> temp = dp.get(j);
                        if (temp.size() == 0)
                        	aux.add(s.substring(j, i));
                        else 
                        	for (int k = 0; k < dp.get(j).size(); k++) {
	                            //System.out.println(aux.size());
	                            aux.add(temp.get(k) + " "
	                                    + s.substring(j, i));
                        	}
                        if (dp.get(i) != null)
                            dp.get(i).addAll(aux);
                        else 
                            dp.put(i, aux);
                    } 
                    // 不需要了，dp.get(j) == null 说明前面的不符合，那后面的就不用考虑了
//                    else {
//                        aux.add(s.substring(j, i));
//                        dp.put(i, aux);
//                    }
                    
                    //System.out.println(s.substring(j, i));
//                    List<String> aux = dp.get(i); //这里这么取，由于substring, i是可以取到s.len，
//                    				// 所以前面初始化的时候一定要 i <= s.len, 否则这里会 nullpointer
//                    if (dp.get(j).size() == 0) {
//                        aux.add(s.substring(j, i));
//                    } else {
//                        List<String> temp = dp.get(j);
//                        for (int k = 0; k < dp.get(j).size(); k++) {
//                            System.out.println(aux.size());
//                            aux.add(temp.get(k) + " "
//                                    + s.substring(j, i));
//                        }
//                    }
//                    dp.put(i, aux);
                }
            }
        }
        return dp.get(s.length());
    }
	
	
	
	public static void main(String[] args) {
		List<String> wordDict = Arrays.asList("a", "aa", "aaa", "sand", "dog");
		new WordBreakII().wordBreak("aaa", wordDict);
	}
}
