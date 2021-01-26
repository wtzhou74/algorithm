package math;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
//
//If the fractional part is repeating, enclose the repeating part in parentheses.
//
//Example 1:
//
//Input: numerator = 1, denominator = 2
//Output: "0.5"
//Example 2:
//
//Input: numerator = 2, denominator = 1
//Output: "2"
//Example 3:
//
//Input: numerator = 2, denominator = 3
//Output: "0.(6)"

public class FractionToRecurringDecimal {
	
	public String fractionToDecimal1(int numerator, int denominator) {
        if (numerator == 0) return "0";
        // replace by "^" operator
        //if (denominator == -numerator || -denominator == numerator) return "-1";
        StringBuffer res = new StringBuffer(); // Used to INSERT new item into SPECIFIED POSITION
        if(denominator < 0 ^ numerator < 0) res.append("-");
        long n = Math.abs((long)numerator); // EDGE CASE
        long d = Math.abs((long)denominator); // EDGE CASE
        // while (n >= d) {
        //     res.append(n / d);
        //     n %= d;
        // }
        res.append(n / d);
        if (n % d == 0) return res.toString();
        res.append(".");
        n = n % d;
        Map<Long, Integer> map = new HashMap<>();
        //String temp = "";
        while (!map.containsKey(n) && n != 0) {
            //temp += n * 10 / d;
            map.put(n, res.length());
            res.append(n * 10 / d);   // Each fraction * 10 then divided by denominator
            n = n * 10 % d;
        }
        //res.append(temp);
        if (n == 0) return res.toString();
        else return res.insert(map.get(n), "(").append(")").toString();
        
    }

	// TLE for TANGLESOME code
	public String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) return "0";
        if (denominator == 1) return String.valueOf(numerator);
        if (denominator == -1) return String.valueOf(-numerator);// WRONG for Integer.MIN_VALUE
        if (denominator == numerator) return "1";
        if (denominator == -numerator || -denominator == numerator) return "-1";
        boolean isNegative = false;
        if (numerator < 0 && denominator > 0 || numerator > 0 && denominator < 0) isNegative = true;
        String res = "";
        //long remainder = numerator;
        long numeratorTemp = Math.abs(numerator);
        long denominatorTemp = Math.abs(denominator);
        long remainder = numeratorTemp;
        while (remainder >= denominatorTemp ) {
            res += remainder / denominatorTemp;
            remainder %= denominatorTemp;
        }
        if (remainder == 0) return isNegative ? "-" + res : res;
        res = res.length() == 0 ? "0." : res + ".";
        Map<Long, Long> map = new LinkedHashMap<>();
        while (!map.containsKey(remainder) && remainder != 0) {
            map.put(remainder, remainder * 10 / denominatorTemp);
            remainder = remainder * 10 % denominatorTemp;
        }
        String temp = "";
        boolean isLoop = remainder == 0 ? false : true;
        for (Map.Entry<Long, Long> entry : map.entrySet()) {
            if (entry.getKey() == remainder && remainder != 0) {
                temp += "(" + entry.getValue();
            } else {
                temp += entry.getValue();
            }
            
        }
        temp = isLoop ? temp + ")" : temp;
        return isNegative ? "-" + res + temp : res + temp;
        
    }
	
	public static void main(String[] args) {
		new FractionToRecurringDecimal().fractionToDecimal(-50, 8);
	}
}
