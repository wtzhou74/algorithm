package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//
//Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.
//
//Example:
//
//Input: low = "50", high = "100"
//Output: 3 
//Explanation: 69, 88, and 96 are three strobogrammatic numbers.
//Note:
//Because the range might be a large number, the low and high numbers are represented as string.
public class StrobogrammaticNumberIII {

	// 一个个数判断  ===> TLE 比如对范围 [0, 2147483647]
	public int strobogrammaticInRange(String low, String high) {
        long highInt = Long.valueOf(high);
        long lowInt = Long.valueOf(low);
        int count = 0;
        while (lowInt <= highInt) {
            String curr = String.valueOf(lowInt);
            if (isStrobogrammatic(curr))
                count++;
            lowInt++;
        }
        // for (int i = 0; i < list.size(); i++) {
        //     System.out.println(list.get(i));
        // }
        
        return count;
    }
    
    //List<String> list = new ArrayList<>();
    private Map<String, Boolean> memo = new HashMap<>();
    public boolean isStrobogrammatic(String number) {
        if (number.length() == 0)
            return true;
        if (number.length() == 1 ) {
            if (number.charAt(0) == '0' || number.charAt(0) == '1' ||
               number.charAt(0) == '8')
                return true;
            else return false;
        }
        if(memo.containsKey(number))
            return memo.get(number);
        int left = 0, right = number.length() - 1;
        //String num = "";
        boolean res = false;
        if ((number.charAt(left) == '6' && number.charAt(right) == '9')
           || (number.charAt(left) == '9' && number.charAt(right) == '6') || (number.charAt(left) == '8' && number.charAt(right) == '8') ||
            (number.charAt(left) == '1' && number.charAt(right) == '1')
            || (number.charAt(left) == '0' && number.charAt(right) == '0')) {
               res = isStrobogrammatic(number.substring(1, number.length() - 1));
               if (res) {
		           //list.add(number); 
		           return true;        
               }
        }
        
        memo.put(number, res);
        return false;
    }
    
    
    // 对不同位数的数，找里面含有的值
    public int strobogrammaticInRange2(String low, String high) {
        int highLen = high.length();
        int lowLen = low.length();
        List<String> list = new ArrayList<>();
        for (int i = lowLen; i <= highLen; i++) {
            list.addAll(getStrobotrobogrammatic(i, i));
        }
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            String num = list.get(i);
            if (Long.valueOf(num) < Long.valueOf(low)
               || Long.valueOf(num) > Long.valueOf(high)) {
                continue;  
            } else 
                count++;
        }
        return count;
    }
    
    // 和 StrobogrammaticNumberII 一样
    public List<String> getStrobotrobogrammatic(int m, int n) {
        List<String> res = new ArrayList<>();
        if (m == 0)
            return Arrays.asList("");
        if (m == 1 ) {
            return Arrays.asList("0", "1", "8");
        }
        List<String> midRes = getStrobotrobogrammatic(m - 2, n);
        for (String num : midRes) {
            res.add("6" + num + "9");
            res.add("9" + num + "6");
            res.add("1" + num + "1");
            res.add("8" + num + "8");
            if (num.length() != n - 2) {
                res.add("0" + num + "0");
            }
        }
        return res;
    }
    
    public int strobogrammaticInRange3(String low, String high) {
        int highLen = high.length();
        int lowLen = low.length();
        List<String> list = new ArrayList<>();
        int count = 0;
        for (int i = lowLen + 1; i <= highLen - 1; i++) {
            count += calculateNum(i, i);
        }
        System.out.println(count);
        if (lowLen != highLen) {
            list.addAll(getStrobotrobogrammatic(lowLen, lowLen));
            list.addAll(getStrobotrobogrammatic(highLen, highLen));
        } else {
            list.addAll(getStrobotrobogrammatic(highLen, highLen));
        }
        
        
        for (int i = 0; i < list.size(); i++) {
            String num = list.get(i);
            if (Long.valueOf(num) < Long.valueOf(low)
               || Long.valueOf(num) > Long.valueOf(high)) {
                continue;  
            } else 
                count++;
        }
        return count;
    }
    
    
    
    //直接计算指定位数内的数字符合条件的个数
    public int calculateNum(int m, int n) {
        //List<String> res = new ArrayList<>();
        if (m == 0)
            return 0;
        if (m == 1 ) {
            return 3;
        }
        int temp = calculateNum(m - 2, n);
        int total = 0;
        if (temp == 0) {
        	if (m != n)
                total += 5;
            else
                total += 4;
        } else {
        	for (int i = 0; i < temp; i++) {
        		if (m != n)
                    total += 5;
                else
                    total += 4;
            }
        }
        return total;
    }
}
