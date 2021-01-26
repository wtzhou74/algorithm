package backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//A string S represents a list of words.
//
//Each letter in the word has 1 or more options.  If there is one option, the letter is represented as is.  If there is more than one option, then curly braces delimit the options.  For example, "{a,b,c}" represents options ["a", "b", "c"].
//
//For example, "{a,b,c}d{e,f}" represents the list ["ade", "adf", "bde", "bdf", "cde", "cdf"].
//
//Return all words that can be formed in this manner, in lexicographical order.
//
// 
//
//Example 1:
//
//Input: "{a,b}c{d,e}f"
//Output: ["acdf","acef","bcdf","bcef"]
//Example 2:
//
//Input: "abcd"
//Output: ["abcd"]
// 
//
//Note:
//
//1 <= S.length <= 50
//There are no nested curly brackets.
//All characters inside a pair of consecutive opening and ending curly brackets are different.
public class BraceExpansion {

	public String[] expand(String S) {
        if (S.length() == 0) return new String[0];
        if (S.indexOf('{') < 0) return new String[]{S};
        List<String> res = new ArrayList<>();
        recursive(res, S, "", 0);
        Collections.sort(res);
        return res.toArray(new String[0]);
    }
    
	// 这个与普通的combination类似，这里注意 s 索引不是连续的，看{}； 故是 } 的下一个元素
	// sol.len ！= S.len
    public void recursive(List<String> list, String S, String sol, int s) {
    	for (int i = s; i < S.length(); i++) {
            if (S.charAt(i) == '{') {
                int j = i + 1;
                List<Character> aux = new ArrayList<>();
                while (S.charAt(j) != '}') {
                	if (S.charAt(j) != ',') 
                		aux.add(S.charAt(j));
                    j++;
                }
                // 对括号里面的数递归  ==> 去括号中的一个，开始取下一个 ==> 再有括号，递归
                // ！！！这里的下一个是 } 的下一个元素
                for (int k = 0; k < aux.size(); k++) {
                    recursive(list, S, sol + aux.get(k), j + 1);// 这里就决定 s 的索引
                }
                return; // 出栈，递归往回； 比如 {a, b}； 第一次 k = 0 ==> sol + 'a'; 递归出去得到一个结果，return 或者 for i==S.len；
                		// ===> 出栈， k = 1, ==> sol + 'b'; 如果 k = aux.len； return ,回到上一对 {}， 接着处理上一对的下一个元素，如果有
            }
            if (i < S.length())
                sol += S.charAt(i);
        }
    	list.add(sol);
    }
    
    // 另一种写法，与上面类似  ==> {a,b,c}e{d,f} ==> {"abc", "e", "df"}
    public String[] expand2(String S) {
        if (S.length() == 0) return new String[0];
        if (S.indexOf('{') < 0) return new String[]{S};
        List<String> items = getSections(S);
        List<String> res = new ArrayList<>();
        dfs(items, res, "", 0);
        Collections.sort(res);
        return res.toArray(new String[0]);
    }
    
    public List<String> getSections(String S) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '{') {
                int j = i + 1;
                StringBuffer sb = new StringBuffer();
                while (S.charAt(j) != '}') {
                	if (S.charAt(j) != ',')
                        sb.append(S.charAt(j));
                	j++;
                }
                list.add(sb.toString());
                i = j;
            } else 
               list.add(c + ""); 
            
        }
        return list;
    }
    
    int K = 0;
    public void dfs(List<String> items, List<String> res, String sol, int s) {
        if (sol.length() == items.size()) {
            res.add(sol);
            return;
        }
        String item = items.get(s);
        // 每次下来只取 item中的一个元素
        for (int i = 0; i < item.length(); i++) {
            sol += item.charAt(i);
            dfs(items, res, sol, s + 1);// 递归下去，使一轮递归只取每个括号中的一个字符
            sol = sol.substring(0, sol.length() - 1);//对 "{a,b}f{d,c}", return； 出栈，递归往回，
            			// 此时 sol = "afd"，是一个solution
            			// 这里只所以要 len - 1, 是递归往回的时候，for中的局部变量 i 从 0，++变到 1，
            			// 对 {d, c};
            			// 接下来要取 c， 如果 sol.len 不减 1，就会使 sol = ‘afdc’； 不对，所以 要减 1，sol = af, 接着得到 afc的另一个solution
            			// 当整个一轮 for跳出后，接着出栈，回到上一个 item,即 {a，b}下
        }
    }
    
    public static void main(String[] args) {
    	new BraceExpansion().expand2("{a,b}f{d,c}");
    }
}
