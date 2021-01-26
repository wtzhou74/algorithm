package twoPointers;

//Write a function that takes a string as input and reverse only the vowels of a string.
//
//Example 1:
//
//Input: "hello"
//Output: "holle"
//Example 2:
//
//Input: "leetcode"
//Output: "leotcede"
//Note:
//The vowels does not include the letter "y".
public class ReverseVowelsOfAString {

	public String reverseVowels(String s) {
        if (s == null || s.length() == 0) return s;
        // List<Character> vowels = new ArrayList<>();
        // vowels.add('a');
        // vowels.add('e');
        // vowels.add('i');
        // vowels.add('o');
        // vowels.add('u');
        // vowels.add('A');
        // vowels.add('E');
        // vowels.add('I');
        // vowels.add('O');
        // vowels.add('U');
        
        StringBuffer sb = new StringBuffer(s);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !isVowel(s.charAt(i))) {
                i++;
            }
            while (j > i && !isVowel(s.charAt(j))) {
                j--;
            }
            if (i < j) {
                char temp = s.charAt(i);
                sb.setCharAt(i, s.charAt(j));
                sb.setCharAt(j, temp);
                
                i++;
                j--;
            }
        }
        return sb.toString();
    }
    
    
    public boolean isVowel(char c) {
        switch (c) {
            case 'a' : return true;
            case 'e': return true;
			case 'i': return true;
			case 'o': return true;
			case 'u': return true;
			case 'A': return true;
			case 'E': return true;
			case 'I': return true;
			case 'O': return true;
			case 'U': return true;
			default : return false;
        }
    }
}
