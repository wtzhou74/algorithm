package microsoft;

import java.util.HashSet;
import java.util.Set;

//Given a string, determine if a permutation of the string could form a palindrome.
//
//Example 1:
//
//Input: "code"
//Output: false
//Example 2:
//
//Input: "aab"
//Output: true
//Example 3:
//
//Input: "carerac"
//Output: true
public class PalindromePermutation {

	public boolean canPermutePalindrome(String s) {
        int[] aux = new int[128];
        int i = 0;
        while (i < s.length()) {
            aux[s.charAt(i)]++;
            i++;
        }
        int odd = 0;
        for (int j = 0; j < aux.length; j++) {
            if (aux[j] % 2 != 0) {
                odd++;
            }
            if (odd > 1)
                return false;
        }
        return true;
    }
	
	public boolean canPermutePalindrome1(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                set.remove(s.charAt(i));
            } else 
                set.add(s.charAt(i));
//            if (!set.add(s.charAt(i))) {//add对set里面已经有该值的 return false
//            	set.remove(s.charAt(i));
//            }
        }
        return set.size() <= 1;
    }
}
