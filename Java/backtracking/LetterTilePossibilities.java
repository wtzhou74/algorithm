package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//You have a set of tiles, where each tile has one letter tiles[i] printed on it.  Return the number of possible non-empty sequences of letters you can make.
//
//
//
//Example 1:
//
//Input: "AAB"
//Output: 8
//Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
//Example 2:
//
//Input: "AAABBC"
//Output: 188
// 
//
//Note:
//
//1 <= tiles.length <= 7
//tiles consists of uppercase English letters.

public class LetterTilePossibilities {

	// Permutation的问题
	public int numTilePossibilities(String tiles) {
        if (tiles.length() == 1) return 1;
        int[] aux = new int[26];
        for (char c : tiles.toCharArray()) {
            aux[c - 'A']++;//统计所有字符的数字，因为结果只需要给出最终的所有组合，所以这里我们统计 删掉/加上一个字符来backtrack
        }
        backtrack(aux);
        return count;
    }
    int count = 0;
    public void backtrack(int[] aux) {
        for (int i = 0; i < 26; i++) {//总共最多只能有26个不同的字符
            if (aux[i] > 0) {
                count++;
                aux[i]--;//删掉当前字符，看能有多少组合
                backtrack(aux);
                aux[i]++;//恢复，再看别的情况
            }
        }
    }
    
	//int count = 0;
    // 直接获取所有不同的组合并统计个数
    // 包含所有子集的排列问题   ===> 对每个元素都有 选/不选  两种情况   ==> visited[] , backtrack
    public int numTilePossibilities2(String tiles) {
        if (tiles.length() == 1) return 1;
        char[] chars = tiles.toCharArray();
        boolean[] visited = new boolean[tiles.length()];
        backtrack(chars, visited, "");
        return set.size();
    }
    Set<String> set = new HashSet<>();
    public void backtrack(char[] chars, boolean[] visited, String s) {
        for (int i = 0; i < chars.length; i++) {//这里的结束条件基于 i 以及 visited
            if (visited[i])
                continue;
            visited[i] = true; 
            s += chars[i];
            set.add(s);
            backtrack(chars, visited, s);
            visited[i] = false; // backtrack, 就是恢复当前元素的visited情况
            s = s.substring(0, s.length() - 1);  // 同时在这里要恢复当前元素
            // 对 "AB"，当B开头，得到比如 ”BA“的结果是由于 backtrack的缘故，将以visited过的元素设置位false,
            // 再递归往回到最外层时候， 局部变量 i 初始值是 0，但此时再被访问时由于 i++, 就从1开始了，Sol也就从B开始了
            // 这也就是在写如 BinaryWatch.java这样的题时不能用 索引 s==len来作为结束条件，而应该是 sol.len = S.len
            
        }
    }
    public static void main(String[] args) {
    	new LetterTilePossibilities().numTilePossibilities2("AB");
    }
}
