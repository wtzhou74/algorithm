package amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

//Given an integer n, find the closest integer (not including itself), which is a palindrome.
//
//The 'closest' is defined as absolute difference minimized between two integers.
//
//Example 1:
//Input: "123"
//Output: "121"
//Note:
//The input n is a positive integer represented by string, whose length will not exceed 18.
//If there is a tie, return the smaller one as answer.
public class FindTheClosestPalindrome {

	// intuitive 解法就是不停的  +1 / -1， 然后看是不是Palindrome  ==> TLE
	
	// Palindrom ==> 两半部分一样，==> 把原数字分两半，==> 又得最近，所以取值越小越好， ====> 所以把左半部分复制到右半部分， 形成一个新的 Palindrom数字
	//  ==> a分5种情况， 
	//			对odd, 比如 123， ==> 121, 111, 131;  若本身是 121， ==> 111, 131； 对 even, 比如 1234 ==> 1221, 1111, 1331, 或者本身是palindrom, 比如 1221
	//          a同时，这里还有两个边界， 比如 121， 其 [99, 1001]
	//====> a很明显这里要分 odd/even，同时要取左半部分，并同时要 +1/-1 操作， ====> 优化代码 ==> 取 （0，mid + 1）的子串， 比如 121 ==> 12, 1234 => 12
	//                           ====> a这样的话，对odd, leftHalf-1,  e..g 11, 13 , 同时在复制的时候， 需要少取一位，只复制 1，而不是 12, 11,
	//							 =====> a对偶数， 我们直接 12， 并 12+1， 12-1， 然后复制就好了
	// ===> a这里为了简化string到num的转换， 用 / % 运算进行复制
	public String nearestPalindromic(String n) {
		if (n == null || n.length() == 0)
            return "";
        long num = Long.parseLong(n); // parseLong返回的是 primitive long, 所以这里赋值给long,并不创建对象，否则下面的num!=val，即使值一样
        boolean isEven = n.length() % 2 == 0 ? true : false;
        int mid = isEven ? n.length() / 2 - 1 : n.length() / 2;
        String leftHalf = n.substring(0, mid + 1); // 看上面解释，这样在处理 odd/even可以合并
        Set<Long> set = new HashSet<>();
        set.add(getPalindrom(Long.valueOf(leftHalf), isEven));
        set.add(getPalindrom(Long.valueOf(leftHalf) - 1, isEven));
        set.add(getPalindrom(Long.valueOf(leftHalf) + 1, isEven));
        set.add((long)Math.pow(10, n.length() - 1) - 1);
        set.add((long)Math.pow(10, n.length()) + 1);
        
        long diff = Long.MAX_VALUE;
        long res = 0;
        for (Long val : set) {
        	// a不能取自己
            if (val == num) continue; //a注意上面num， 用 parseLong
            if (Math.abs(val - num) < diff) {
                res = val;
                diff = Math.abs(val - num);
            } else if (Math.abs(val - num) == diff) {
                res = Math.min(res, val);
            }
        }
        return String.valueOf(res);
    }
    
	// a用 / % 运算进行复制
	public long getPalindrom(long leftHalf, boolean isEven) {
		long res = leftHalf;
        if (!isEven)
            leftHalf = leftHalf / 10;
        while (leftHalf > 0) {
            res = res * 10 + leftHalf % 10;
            leftHalf /= 10;
        }
        return res;
    }
    
    // Brute force - TLE, 即分别向两边 +1/-1
    public String nearestPalindromic1(String n) {
        if (n == null || n.length() == 0)
            return "";
        int offset = 1;
        PriorityQueue<Long> pq = new PriorityQueue<>();
        getPalindrom(n, n, pq);
        return pq.poll().toString();
    }
    
    public void getPalindrom(String s, String n, 
                             PriorityQueue<Long> pq) {
        Long num = Long.valueOf(n);
        //boolean found = false;
        
        if (!n.equals(s) && isPalindrome(n)) {
            pq.add(Long.valueOf(n));
            return;
        } else {
            getPalindrom(s, String.valueOf(num - 1), pq);
            getPalindrom(s, String.valueOf(num + 1), pq);
        }
        
        
    }
    
	public String nearestPalindromicWRONG(String n) {
        if (n == null || n.length() == 0)
            return "";
        //int num = Integer.valueOf(n);
        Set<String> set = new HashSet<>();
        int half = n.length() / 2;
        String prefix = n.substring(0, half);
        StringBuffer sb = new StringBuffer(prefix);
        String rPrefix = sb.reverse().toString();
        if (n.length() % 2 == 0) {
            set.add(prefix + rPrefix);
         // a 这边也要看是否是 palindrome的情况， 比如 1221 - 选1111， 2-1，中间位置的值 - 1 ！！！！！
            // set.add(n.substring(0, half - 1) + (mid - 1)
            //       + (mid - 1) + prefix);
            // set.add(prefix + (mid + 1)
            //       + prefix);
        } else {
            set.add(prefix + n.charAt(half) + rPrefix);
            if (isPalindrome(n)) { // 基数情况 -  121, 要看 111, 131 !!!!! 总之本身是 palindrome，需要中间位置的值 +1/-1, 再看大小
                int mid = (int)(n.charAt(half) - '0');
                set.add(prefix + (mid - 1)
                      + prefix);
                set.add(prefix + (mid + 1)
                      + prefix);
            }
                
        }
        set.add(String.valueOf((long)Math.pow(10, n.length() - 1) - 1));
        set.add(String.valueOf((long)Math.pow(10, n.length()) + 1));
        
        Long target = Long.valueOf(n);
        Long diff = Long.MAX_VALUE;
        String res = "";
        for (String s : set) {
            if (s.equals(n)) continue;
            System.out.println(s);
            if (Math.abs(target - Long.valueOf(s)) < diff) {
            	diff = Math.abs(target - Long.valueOf(s));
            	res = s;
            } else if (Math.abs(target - Long.valueOf(s)) == diff) { // 如果有多个，选最小
                res = String.valueOf(Math.min(Long.valueOf(res), Long.valueOf(s)));
            }
                //res = s;
        }
        return res;
    }
    
    
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
    
    public static void main(String[] args) {
    	new FindTheClosestPalindrome().nearestPalindromic("999");
    }
}
