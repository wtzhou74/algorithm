package microsoft;

import java.util.List;

//Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
//
//Return the maximum possible length of s.
//
// 
//
//Example 1:
//
//Input: arr = ["un","iq","ue"]
//Output: 4
//Explanation: All possible concatenations are "","un","iq","ue","uniq" and "ique".
//Maximum length is 4.
//Example 2:
//
//Input: arr = ["cha","r","act","ers"]
//Output: 6
//Explanation: Possible solutions are "chaers" and "acters".
//Example 3:
//
//Input: arr = ["abcdefghijklmnopqrstuvwxyz"]
//Output: 26
// 
//
//Constraints:
//
//1 <= arr.length <= 16
//1 <= arr[i].length <= 26
//arr[i] contains only lower case English letters.
public class MaximumLengthOfAConcatenatedStringWithUniqueCharacters {

	public int maxLength(List<String> arr) {
        if (arr == null || arr.size() == 0)
            return 0;
        dfs(arr, 0, "");
        return max.length();
    }
    
    String max = "";
    //DFS的过程
    public void dfs(List<String> arr, int s, String sol) {
        if (s >= arr.size()) {
            if (max.length() < sol.length())
                max = sol;
            return ;
        }
        // a任何一个合理的组合都可以是最终结果！！！
        if (max.length() < sol.length()) {
            max = sol;
        }
        for (int i = s; i < arr.size(); i++) {
            if (!isUnique(sol + arr.get(i)))
                continue;
            dfs(arr, i + 1, sol + arr.get(i));
        }
    }
    
    public boolean isUnique(String s) {
        // int i = 0;
        // while (i < s.length()) {
        //     if (s.substring(i + 1, s.length()).indexOf(s.charAt(i)) >= 0) {
        //         return false;
        //     }
        //     i++;
        // }
        // return true;
        
        int[] aux = new int[26];
        for (int i = 0; i < s.length(); i++) {
            if (aux[s.charAt(i) - 'a'] >= 1)
                return false;
            aux[s.charAt(i) - 'a']++;
        }
        return true;
    }
}
