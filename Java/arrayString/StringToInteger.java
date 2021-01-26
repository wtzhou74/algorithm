package arrayString;

public class StringToInteger {

	public int myAtoi(String str) {
        boolean isNegative = false;
        int i = 0;
        int res = 0; // Long res = 0l;  NOT A GOOD OPTION
        for (; i < str.length() && str.charAt(i) == ' '; i++); // removing white space at the beginning of string
        if (i > str.length()) return 0; // No need to check (0, 9), because for loop; and here is check one digit by one digit
        if (str.charAt(i) == '-') {
        	isNegative = true;
        	i++;
        } else if (str.charAt(i) == '+') {
        	i++;
        }
        // starting to check the subsequent characters
        for (; i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9'; i++) {
        	int digit = (int)str.charAt(i) - (int)'0';
        	if (isNegative) {
//        		if (res * 10 + digit > Integer.MAX_VALUE) { // res * 10 + digit will OVERFLOW
//            		return Integer.MIN_VALUE;
//            	}
        		if (res > (Integer.MAX_VALUE - digit) / 10) {
        			return Integer.MIN_VALUE;
        		}
        	} else {
        		if (res > (Integer.MAX_VALUE - digit) / 10) {
        			return Integer.MAX_VALUE;
        		}
        	}
        	
        	res = res * 10 + digit;
        	
        }
        return isNegative ? -1 * res : res;
        
            
    }
    
    
    public static void main(String[] args) {
    	int a = new StringToInteger().myAtoi("-91283472332");
    	System.out.println();
    }
}
