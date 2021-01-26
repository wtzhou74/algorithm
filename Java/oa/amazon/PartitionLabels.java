package oa.amazon;

import java.util.ArrayList;
import java.util.List;

//A string S of lowercase English letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
//
//		 
//
//Example 1:
//
//Input: S = "ababcbacadefegdehijhklij"
//Output: [9,7,8]
//Explanation:
//The partition is "ababcbaca", "defegde", "hijhklij".
//This is a partition so that each letter appears in at most one part.
//A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
// 
//
//Note:
//
//S will have length in range [1, 500].
//S will consist of lowercase English letters ('a' to 'z') only.
public class PartitionLabels {

	// Greedy算法  ==> 每次看当前字符的index,以及其lastIndexOf的位置
	// Time: O(N)  判断N个字符的index
	// Space: O(1) 如果不考虑return的list
	public List<Integer> partitionLabels(String S) {
        if (S.length() == 0) return new ArrayList<>();
        int i = 0;
        List<Integer> res = new ArrayList<>();
        while (i < S.length()) {
            char c = S.charAt(i);
            int last = S.lastIndexOf(c);
            if (last == i) {
                res.add(1);
                i++;
                continue;
            }
            // 看 [i, last] 之间的字符其最后一次出现的位置是在这之间还是在这范围之外，
            		// 之外的话就往外扩，在里面的话就不管
            for (int j = i + 1; j < last; j++) {
            	if (S.charAt(j) == c) continue;//一样的话，last肯定一样
                int idx = S.lastIndexOf(S.charAt(j));
                if (idx > last)
                    last = idx;	
            }
            res.add(last - i + 1);
            i = last + 1;
        }
        return res;
    }
}
