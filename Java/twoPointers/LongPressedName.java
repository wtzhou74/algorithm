package twoPointers;
//Your friend is typing his name into a keyboard.  Sometimes, when typing a character c, the key might get long pressed, and the character will be typed 1 or more times.
//
//You examine the typed characters of the keyboard.  Return True if it is possible that it was your friends name, with some characters (possibly none) being long pressed.
//
// 
//
//Example 1:
//
//Input: name = "alex", typed = "aaleex"
//Output: true
//Explanation: 'a' and 'e' in 'alex' were long pressed.
//Example 2:
//
//Input: name = "saeed", typed = "ssaaedd"
//Output: false
//Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.
//Example 3:
//
//Input: name = "leelee", typed = "lleeelee"
//Output: true
//Example 4:
//
//Input: name = "laiden", typed = "laiden"
//Output: true
//Explanation: It's not necessary to long press any character.
// 
//
//Note:
//
//name.length <= 1000
//typed.length <= 1000
//The characters of name and typed are lowercase letters.
public class LongPressedName {

	public boolean isLongPressedName(String name, String typed) {
        if (name.equals(typed)) return true;
        int i = 0, j = 0;
        while (i <= name.length() - 1 && j <= typed.length() - 1) {
            if (name.charAt(i) != typed.charAt(j)) return false;
            int duplicates = 0;
            while (i + 1 < name.length()
                  && name.charAt(i + 1) == name.charAt(i)) {
                i++;
                duplicates++;
            }
            while (j + 1 < typed.length()
                  && typed.charAt(j + 1) == typed.charAt(j)) {
                j++;
                duplicates--;
            }            
            if (duplicates > 0) return false;
            i++;
            j++;
        }
        return i >= name.length() && j >= typed.length() ? true : false; // 不能简单return true， 因为 typed有可能丢失最后字母
    }
	
	public static void main(String[] args) {
		new LongPressedName().isLongPressedName("pyplrz", "ppyypllr");
	}
}
