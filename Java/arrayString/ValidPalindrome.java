package arrayString;

public class ValidPalindrome {

	public boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;
//        String temp = s.replace("[^0-9A-Za-z]", ""); // INEFFICIENT way since REPLACEALL need O(N) extra space
//        return helper(temp.toLowerCase());
        return helper(s);
    }
//    public boolean helper(String s) {
//        int  i = 0, j = s.length() - 1;
//        while (i < j) {
//            if (s.charAt(i++) != s.charAt(j--)) return false;
//        }
//        return true;
//    }
	public boolean helper(String s) {
        int i = 0, j = s.length() - 1;
        char[] cs = s.toCharArray();
        while (i < j) {
            if (!isValidCharacter(cs[i])) {i++; continue;}
            if (!isValidCharacter(cs[j])) {j--; continue;}
            // if ((cs[i++] ^ cs[j--]) != 0 && (cs[i++] ^ cs[j--]) != 32) { WRONG!!! The PRE i is DIFFERENT from LATTER i
            if ((cs[i] ^ cs[j]) != 0 && (cs[i] ^ cs[j]) != 32) {
                return false;
            } else {
            	i++;
            	j--;
            }
        }
        return true;
    }
    public boolean isValidCharacter(char c) {
        if (c >= 'a' && c <= 'z') return true;
        if (c >= '0' && c <= '9') return true;
        if (c >= 'A' && c <= 'Z') return true;
        return false;
    }
    
    public static void main(String[] args) {
    	new ValidPalindrome().isPalindrome("A man, a plan, a canal: Panama");
    }
}
