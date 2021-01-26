package backtracking;

import java.util.ArrayList;
import java.util.List;

//Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.  Return a list of all possible strings we could create.
//
//Examples:
//Input: S = "a1b2"
//Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
//
//Input: S = "3z4"
//Output: ["3z4", "3Z4"]
//
//Input: S = "12345"
//Output: ["12345"]
//Note:
//
//S will be a string with length between 1 and 12.
//S will consist only of letters or digits.
public class LetterCasePermutation {

	// permutation ==> 每个字符有2种情况（除数字） ==> Time: O(2^n)
	public List<String> letterCasePermutation(String S) {
        List<String> res = new ArrayList<>();
        backtrack(S, 0, res, "");
        return res;
    }
    
    public void backtrack(String S, int s, List<String> list, String sol) {
        if (sol.length() == S.length()) { //!!!! 这里不能用 s == S.length(); 递归回到最外面的时候，因为最开始
        	// s是从0开始，i也从0，当递归到最外面时，i++会变成1， 把 i+1传给s, 使得 s == S.length() (假如 S="Ab"), 而此时 sol = “b”;
        	// 只有一个元素，但由于if （s == S.length()）;所以 “b”也会被加入list
        	// 所以，这里我们应该判读目标 sol 是否是我们要的，而不是索引，索引在递归过程中会变化，尤其是局部变量，在回来后拿到原来的值，接着做处理，比如++等
        	// 每一层的起始 i 是不一样的，跟着 s 走的
            list.add(sol);
            return; // return之后出栈，回到上一层，sol从 “Ab” 变回 “A”，接着通过当前的 i++ 取下一个元素 （每一层起始的 i 是不一样的）
        }
        //String sol = "";
        // 这里从 Start开始，即每次递归过来的时候，从上一个元素的下一个开始，不是从0开始
        // 这里取permutation，我们取完上一个的情况，接下来就是相对于当前元素的下一个，层层递归下去
        for (int i = s; i < S.length(); i++) {
            char c = S.charAt(i);
            if (Character.isLowerCase(c)) {
                char upper = Character.toUpperCase(c);
                backtrack(S, i + 1, list, sol + upper);
                //backtrack(S, i + 1, list, sol + c); // 自己也得算上一种，所以这里还得按不变的情况往下递归
            } else if (Character.isUpperCase(c)) {
                char lower = Character.toLowerCase(c);
                backtrack(S, i + 1, list, sol + lower);
                //backtrack(S, i + 1, list, sol + c);
            }
            //else {
                //sol += c;
            backtrack(S, i + 1, list, sol + c);
            //}
            //backtrack(S, i + 1, list, sol);
            //这里我们不需要额外通过减小一个目的元素 backtrack回去，
            // 因为sol 是string, 在return后，由于索引的变化，自动回到上一个元素，并从此处开始另外一种情况的判断
            // 再回去的情况是回到上一层，选择另外的路径看是否有符合条件的 ！！！
            //sol = sol.substring(0, sol.length() - 1);
        }
    }
    
    public static void main(String[] args) {
    	new LetterCasePermutation().letterCasePermutation("Ab");
    }
}
