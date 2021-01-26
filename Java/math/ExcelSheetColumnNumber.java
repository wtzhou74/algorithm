package math;

import java.util.HashMap;
import java.util.Map;

public class ExcelSheetColumnNumber {

	public int titleToNumberOpti(String s) {
		if (s.length() == 0) return 0;
		int total = 0;
		int unit = 1;
		for (int i = s.length() - 1; i >= 0; i--) {
			total += (s.charAt(i) - 'A' + 1);
			unit *= 26;// TAKING ADVANTAGE OF EXISTING LOOP
		}
		return total;
	}
	public int titleToNumber(String s) {
        if (s.length() == 0) return 0;
        /**START*/ //Be replaced with c - 'A' + 1 to get the digit value
        Map<Character, Integer> map = new HashMap<>();
        char c = 'A';
        for (int i = 1; i <= 26; i++) {
            map.put(c, i);
            c = (char)(c + 1);
        }
        /**END*/
        int total = 0;
        int j = s.length();
        /**Starting from lowest digit, 1* UNIT*UNIT ...*/
        for (int i = 0; i < s.length(); i++) {
            int temp = power26(j);
            total += temp * map.get(s.charAt(i));
            j--;
        }
        return total;
    }
    
    public int power26(int n) {
        int total = 1;
        while(n > 1) {
            total *= 26;
            n--;
        }
        return total;
    }
    
    public static void main(String[] args) {
    	new ExcelSheetColumnNumber().titleToNumber("AB");
    }
}
