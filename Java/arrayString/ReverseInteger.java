package arrayString;

//Given a 32-bit signed integer, reverse digits of an integer.
//
//Example 1:
//
//Input: 123
//Output: 321
//Example 2:
//
//Input: -123
//Output: -321
//Example 3:
//
//Input: 120
//Output: 21
//Note:
//Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. 
//For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

public class ReverseInteger {

	public int reverse(int x) {
		if (x > Integer.MAX_VALUE || x <= Integer.MIN_VALUE) return 0;
        int temp = Math.abs(x);
        String res = "";
        while (temp > 10) {
            res += String.valueOf(temp % 10);
            temp = temp / 10;
        }
        res += temp;
        double d = Double.parseDouble(res);
        if (d > Integer.MAX_VALUE || d < Integer.MIN_VALUE) return 0;
        return Integer.parseInt(res);
    }
	
	public static void main(String[] args) {
		// new ReverseInteger().reverse(1534236469);
		new ReverseInteger().reverse(-2147483648);
	}
}
