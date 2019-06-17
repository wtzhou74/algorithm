package arrayString;

/**
 * Given two binary strings, return their sum (also a binary string).
 * The input strings are both non-empty and contains only characters 1 or 0.
 * 
 * **/
public class AddBinary {

	public static String addBinaryString(String a, String b)
	{
		int leng1 = a.length();
		int leng2 = b.length();
		
		
		if (leng1 == 0 && leng2 == 0) {
			return new String();
		}
		if (leng1 == 0 && leng2 > 0) {
			return b;
		}
		if (leng1 > 0 && leng2 == 0) {
			return a;
		}
		
		////////!!!!!!
		// replace the following with while(aLen > 0 || bLen >0)
		
		//char carry = '0';
		if (leng1 >= leng2) {
			return addItem(leng1, leng2, a, b);
		} else 
			return addItem(leng2, leng1, b, a);
		
		
		//return result.toString();
	}
	
	// PAY CLOSE ATTENTION TO THE CARRY VALUE !!!!
	public static String addItem(int longLen, int shortLen, String longStr, String shortStr) {
		
		char[] aCharArray = longStr.toCharArray();
		char[] bCharArray = shortStr.toCharArray();
		
		StringBuilder result = new StringBuilder();
		char carry = '0';
		for(int i = shortLen - 1, j = longLen - 1; i >= 0; i--, j--) {
			if(aCharArray[j] == '1' && bCharArray[i] == '1') {
				result.append(carry);
				carry = '1';
			} else if (aCharArray[j] == '1' || bCharArray[i] == '1') {
				if (carry == '1') {
					result.append('0');
					carry = '1';
				} else {
					result.append('1');
				}
			} else 
				{
					result.append(carry);
					carry = '0';
				}
		}
		
		// START from longLen - shortLen - 1
		if (shortLen != longLen) {
			for (int j = longLen - shortLen - 1; j >= 0; j--) {
				if (carry == '0') {
					result.append(aCharArray[j]);
				} else {
					if (aCharArray[j] == '1') {
						result.append('0');
						carry = '1';
					} else {
						result.append(carry);
						carry = '0';
					}
				}
				
			}	
		}
		
		// Check last element
		if (carry == '1') result.append("1");
		
		// REMEMBER TO REVERSE THE STRING
		result.reverse();
		
		return result.toString();
	}
	
	public static String optimizedSolution(String a, String b) {
		StringBuilder result = new StringBuilder();
		int aLen = a.length();
		int bLen = b.length();
		int carry = 0;
		
		while(aLen > 0 || bLen > 0) {
			int sum = carry;
			if (aLen > 0) {
				sum += charToInt(a.charAt(aLen - 1));
				aLen--;
			}
			if (bLen > 0) {
				sum += charToInt(b.charAt(bLen - 1));
				bLen--;
			}
			if ((sum % 2 == 0)) {
				result.append('0');
			} else {
				result.append('1');
			}
			
			carry = sum / 2;
		}
		if (carry != 0) result.append('1');
	
		return result.reverse().toString();
	}
	
	public static int charToInt(char a) {
		int i = 0;
		return i = (a == '1') ? 1 : 0;
	}
}
