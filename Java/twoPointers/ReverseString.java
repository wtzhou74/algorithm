package twoPointers;

//Write a function that reverses a string. The input string is given as an array of characters char[].
//
//Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
//
//You may assume all the characters consist of printable ascii characters.
//
// 
//
//Example 1:
//
//Input: ["h","e","l","l","o"]
//Output: ["o","l","l","e","h"]
//Example 2:
//
//Input: ["H","a","n","n","a","h"]
//Output: ["h","a","n","n","a","H"]
public class ReverseString {

	// Two Pointers
	public void reverseString(char[] s) {
        if (s.length == 0 || s.length == 1) return;
        int i = 0, j = s.length - 1;
        while (i < j) {
            char aux = s[i];
            s[i] = s[j];
            s[j] = aux;
            i++;
            j--;
        }
        
    }
	
	// Space: O(N) to keep the recursion stack
	public void recursiveSol(char[] s) {
        if (s.length == 0 || s.length == 1) return;
        helper(s, 0, s.length - 1);
        
    }
    
    public void helper(char[] s, int left, int right) {
        if (left > right)
            return;
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        helper(s, left + 1, right - 1);
    }
}
