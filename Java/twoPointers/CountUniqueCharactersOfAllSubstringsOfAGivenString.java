package twoPointers;

//Let's define a function countUniqueChars(s) that returns the number of unique characters on s, for example if s = "LEETCODE" then "L", "T","C","O","D" are the unique characters since they appear only once in s, therefore countUniqueChars(s) = 5.
//
//On this problem given a string s we need to return the sum of countUniqueChars(t) where t is a substring of s. Notice that some substrings can be repeated so on this case you have to count the repeated ones too.
//
//Since the answer can be very large, return the answer modulo 10 ^ 9 + 7.
//
// 
//
//Example 1:
//
//Input: s = "ABC"
//Output: 10
//Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
//Evey substring is composed with only unique letters.
//Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10
//Example 2:
//
//Input: s = "ABA"
//Output: 8
//Explanation: The same as example 1, except countUniqueChars("ABA") = 1.
//Example 3:
//
//Input: s = "LEETCODE"
//Output: 92
// 
//
//Constraints:
//
//0 <= s.length <= 10^4
//s contain upper-case English letters only.

public class CountUniqueCharactersOfAllSubstringsOfAGivenString {

	// TLE 
	// f(n) = f(n - 1) + unique // 但是这里的unique数目要根据新加入的字符判断， 不是简单的 （j - i + 1）,这里要的是 distinct
	public int uniqueLetterString(String s) {
        if (s.length() <= 1) return s.length();
        dfs(s, s.length() - 1);
        return total;
    }
    
    int total = 0;
    public int dfs(String s, int idx) {
        if (idx == 0) {
        	//total += 1;
        	return 1;
        }
        String sub = s.substring(0, idx + 1);
        int unique = 0;
        // a计算每增加1个字符，多出的 unique数值 ！！！！ tricky的地方，进行优化
        for (int i = sub.length() - 1; i >= 0; i--) {
        	unique += countUniqueChars(sub.substring(i, sub.length()));
        }
        // a这块在计算unique的时候，从[AB]到[ABC]会多出 [AB] + 3 个子字符串，不是只有一个（从而对[ABC]求unique的个数）
        // a另外 [AB], 到 [ABA]的时候，在计算多出来的字串时候，新加入的A会影响这些字串的结果 （！！！！这里就是优化的地方）
        
        int count = 0;// 这里初始化0， 是DFS时候每一层（节点）初始化值，再返回的时候不会回到这里，所以不会再初始化一遍清0； 如果下面有 for (),dfs在for里面就会
        count = dfs(s, idx - 1) + unique; // DFS返回的时候，计算各个节点的对应的 count值，累加上
        
        
        //total += count; // 这里不用再total了， count在返回的过程中就是一个累加的过程
        return count;
    }
    
    public int countUniqueChars(String s) {
        int count = 0;
        int[] unique = new int[26];
        for (int i = 0; i < s.length(); i++) {
            unique[s.charAt(i) - 'A']++;
        }
        for (int i = 0; i < 26; i++) {
            if (unique[i] == 1)
                count++;
        }
        return count;
    }
    
    // DP  ===> 详细分析看  CountUniqueCharacters.jpg
    public int uniqueLetterStringDP (String s) {
    	if (s.length() <= 1) return s.length();
        int[] first = new int[26];
        int[] second = new int[26];
        int total = 0, curr = 0, M = (int)Math.pow(10, 9) + 7; // 注意  ^ 是 XOR 运算符
        
        // preTotal + curr
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'A';
            // curr只是统计新加入 1个字符产生的额外unique数，不是在当前字符串下总的unqiue数
            curr = curr + 1 + (i - first[c]) - (first[c] - second[c]); // 这里只是计算 新加一个字符总的产生的贡献值 
            //cur + 1 + i - first[c] * 2 + second[c]
            total = (total + curr) % M;
            second[c] = first[c];
            first[c] = i + 1; // +1 方便 curr的计算
        }
        return total;
    }
    
    public static void main(String[] args) {
    	new CountUniqueCharactersOfAllSubstringsOfAGivenString().uniqueLetterStringDP("DABCA");
    }
    
}
