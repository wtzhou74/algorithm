package twoPointers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.
//
//Example 1:
//Input: S = "ababcbacadefegdehijhklij"
//Output: [9,7,8]
//Explanation:
//The partition is "ababcbaca", "defegde", "hijhklij".
//This is a partition so that each letter appears in at most one part.
//A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
//Note:
//
//S will have length in range [1, 500].
//S will consist of lowercase letters ('a' to 'z') only.
public class PartitionLabels {

	public List<Integer> greedy(String S) {
        if (S == null || S.length() == 0) return new ArrayList<>();
        int[] index = new int[26];
        for (int i = 0; i < S.length(); i++) { // 记录每个字符最后一次出现在字符串中的位置，接下来就是看最后一个位置
            index[S.charAt(i) - 'a'] = i;
        }
        List<Integer> res = new ArrayList<>();
        int max = 0;
        int count = 1;
        for (int i = 0; i < S.length(); i++) {
            if (index[S.charAt(i) - 'a'] > max) {
                max = index[S.charAt(i) - 'a'];
            } else if (i == max) { // 当前位置与之前字符出现的最后位置相等，即找到一个partition
                res.add(count);
                max = i + 1;// max需要从下一个i 开始，否则 [ab]的结果会是 [1]，应该是 [1,1]
                count = 0;
            }
            count++;
        }
        return res;
    }
	
	public List<Integer> partitionLabels(String S) {
        if (S == null || S.length() == 0) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Set<String> set = new HashSet<>();
        int i = 0;
        int count = 0;
        while (i < S.length()) {
            String c = S.charAt(i) + "";
            if (i == S.length() - 1) {
                if (set.contains(c)) {
                    res.add(count+1);
                    break;
                } else {
                    res.add(1);
                    break;
                }
            }
            String aux = S.substring(i + 1, S.length());
            if (aux.contains(c)) {
                set.add(c);
                i++;
                count++;
                continue;
            }
            if (set.contains(c) && !aux.contains(c)) {
                set.remove(c);
            }
            if (set.size() == 0) {
                res.add(count + 1);
                count = 0;
            } else {
                count++;
            }
            i++;
        }
        return res;
    }
}
