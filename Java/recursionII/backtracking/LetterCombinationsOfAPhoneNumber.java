package recursionII.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfAPhoneNumber {

	public List<String> letterCombinations(String digits) {
        Map<String, String> map = new HashMap<>();
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
        
        List<String> res = new ArrayList<>();
        backtrack("", map, digits, res);
        return res;
        
    }
    
	// Time: O(3N-square * 4M-square) : N is the number of digit which map to 3 letters, 
	//		and M is the number of digit that map to 4 letter.
	// Space: same
    public void backtrack(String s, Map<String, String> map, 
    		String digits, List<String> res) {
        if (digits.length() == 0) {
            // find a solution
            res.add(s);
            return;
        } 
        // Pick candidate from DIFFERENT target (pool) !!!!!!
        String letters = map.get(digits.substring(0, 1));
        // EACH STEP OF OCCURRENCE OF RECURSION IS ONE STRP TO THE SOLUTION
        for (int i = 0; i < letters.length(); i++) {
        	String letter = letters.substring(i, i + 1);
        // append the current letter to the combination
        // and proceed to the next digits
        	s += letter;
        	backtrack(s, map, digits.substring(1), res); // backtrack(s + letter, map, digits.substring(1), res); NO NEEDED TO REMOVE, SINCE s + letter IS THE SOLUTION
        	s = s.substring(0, s.length() - 1); // HAVE TO REMOVE IF USING s += letter which applys all solutions.
        }
    }
    
    // 第二种写法
    List<String> res = new ArrayList<>();
    public void getCombination(Map<Integer, String> map, String digits, int s, String sol) {
        if (s == digits.length()) {
            res.add(sol);
            return;
        }
        String letters = map.get(Integer.valueOf(digits.substring(s, s + 1)));
        // 相当于图中， 每个点下的多个adjacent
        for (int i = 0; i < letters.length(); i++) {
            sol += letters.charAt(i);
            getCombination(map, digits, s + 1, sol);
            sol = sol.substring(0, sol.length() - 1);
        }
    }
    
    public static void main(String[] args) {
    	List<String> res = new LetterCombinationsOfAPhoneNumber().letterCombinations("23");
    	System.out.println();
    }
}
