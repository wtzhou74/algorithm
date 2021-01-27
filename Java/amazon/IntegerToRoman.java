package amazon;

//Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
//
//Symbol       Value
//I             1
//V             5
//X             10
//L             50
//C             100
//D             500
//M             1000
//For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
//
//Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
//
//I can be placed before V (5) and X (10) to make 4 and 9. 
//X can be placed before L (50) and C (100) to make 40 and 90. 
//C can be placed before D (500) and M (1000) to make 400 and 900.
//Given an integer, convert it to a roman numeral. Input is guaranteed to be within the range from 1 to 3999.
//
//Example 1:
//
//Input: 3
//Output: "III"
//Example 2:
//
//Input: 4
//Output: "IV"
//Example 3:
//
//Input: 9
//Output: "IX"
//Example 4:
//
//Input: 58
//Output: "LVIII"
//Explanation: L = 50, V = 5, III = 3.
//Example 5:
//
//Input: 1994
//Output: "MCMXCIV"
//Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
public class IntegerToRoman {

	public String intToRoman(int num) {
		
		//因为Roman数从左往右，从大到小组，所以，需要排序，map不合适
        // Map<String, Integer> map = new HashMap<>();
        // map.put(1, "I");
        // map.put(5, "V");
        // map.put(10, "X");
        // map.put(50, "L");
        // map.put(100, "C");
        // map.put(500, "D");
        // map.put(1000, "M");
        // map.put(4, "IV");
        // map.put(9, "IX");
        // map.put(40, "XL");
        // map.put(90, "XC");
        // map.put(400, "CD");
        // map.put(900, "CM");
		
		// 这样两组数字对应上
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuffer sb = new StringBuffer();
        
        // Roman值转换只能用 + （除了指定的6个用减法的）， 不能用乘， 比如 300只能 100 + 100 + 100， 不能 3 * 100 ！！！！
        for (int i = 0; i < values.length && num >= 0; i++) {
        	
        	// 这里从大的写写， 比如 140 = 100 + 40 或者 100 + 10+10+10+10，用前者
            while (values[i] <= num) { // 我们找到第一个比他小的值后，记录下来
                num -= values[i]; // 看剩下的值是否还满足，比如  300 -> C 余 200，继续 C，余 100，再C
                sb.append(symbols[i]);
            }
        }

        return sb.toString();
        
    }
	
	// 另一种方法就是把所有千位/百位/十位/个位 可用的Roman值都写出来
	// 然后 / % 运算得出各个位置上的值
	public String intToRoman2(int num) {
	    
	    String[] thousands = {"", "M", "MM", "MMM"}; // 这边最大只能3000
	    String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; 
	    String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
	    String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
	    
	    return thousands[num / 1000] 
	    		+ hundreds[num % 1000 / 100] 
	    				+ tens[num % 100 / 10] 
	    						+ ones[num % 10];
	}
}
