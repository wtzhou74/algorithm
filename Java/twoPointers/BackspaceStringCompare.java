package twoPointers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
//
//Example 1:
//
//Input: S = "ab#c", T = "ad#c"
//Output: true
//Explanation: Both S and T become "ac".
//Example 2:
//
//Input: S = "ab##", T = "c#d#"
//Output: true
//Explanation: Both S and T become "".
//Example 3:
//
//Input: S = "a##c", T = "#a#c"
//Output: true
//Explanation: Both S and T become "c".
//Example 4:
//
//Input: S = "a#c", T = "b"
//Output: false
//Explanation: S becomes "c" while T becomes "b".
//Note:
//
//1 <= S.length <= 200
//1 <= T.length <= 200
//S and T only contain lowercase letters and '#' characters.
//Follow up:
//
//Can you solve it in O(N) time and O(1) space?

public class BackspaceStringCompare {

	public boolean twoPointers (String S, String T) {
		
		// based on Backspace的特性，从后往前判断，从#开始，到非#暂停
		if (S == null || T == null) return false;
        if (S.equals(T)) return true;
        int lenS = S.length(), lenT = T.length();
        int i = lenS - 1, j = lenT - 1;
        int skipS = 0, skipT = 0;
        while (i >= 0 || j >= 0) { // ||是因为 S 或 T 有可能需要再执行一次， 比如：  S="n", T="a#n", s先到-1， 但T是#, 需要继续查看
            while (i >= 0) {
                if (S.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else {
                    if (skipS > 0) {
                        skipS--;
                        i--;
                    } else break;
                }
            }
            while (j >= 0) {
                if (T.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else {
                    if (skipT > 0) {
                        skipT--;
                        j--;
                    } else break;
                }
            }
            // 此时 i, j 是会出现在 editor里的，除非都删除完了
            if (i >= 0 && j >= 0 ) {
                if (S.charAt(i) != T.charAt(j)) {
                    return false;
                }
            }
            if (!(i >= 0 && j >= 0)) break;
            i--;
            j--;
        }
        return i < 0 && j < 0 ? true : false;
	}
	
	
	
	public boolean backspaceCompare(String S, String T) {
		if (S == null || T == null) return false;
        if (S.equals(T)) return true;
        Stack<Character> auxS = new Stack<>();
        Stack<Character> auxT = new Stack<>();
        for (char c : S.toCharArray()) {
            if (c != '#')
                auxS.push(c);
            else {
                if (!auxS.isEmpty())
                    auxS.pop();
            }
        }
        for (char c : T.toCharArray()) {
            if (c != '#')
                auxT.push(c);
            else {
                if (!auxT.isEmpty())
                    auxT.pop();
            }
        }
        while (!auxS.isEmpty() && !auxT.isEmpty()) {
            if (auxS.pop() != auxT.pop()) {
                return false;
            }
        }
        return auxS.isEmpty() && auxT.isEmpty() ? true : false;
        
    }
	
	public static void main(String[] args) {
		new BackspaceStringCompare().twoPointers("nzp#o#g", "b#nzp#o#g");
	}
}
