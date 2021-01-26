package arrayString;

import java.util.LinkedList;
import java.util.Queue;

public class CountAndSay {

	public String countAndSay(int n) {
		if (n == 1) return "1";
//        Queue<Character> queue = new LinkedList<>();
//        queue.offer('1');
//        int i = 1;
//        String res = "";
//        while (i < n) {
//            int size = queue.size();
//            int counter = 1;
//            while (size > 0) {
//                char digit = queue.poll();
//                if (queue.peek() != null && size > 1 && digit == queue.peek()) {
//                    counter++;
//                } else {
//                    queue.offer((char)(counter + '0'));
//                    queue.offer(digit);
//                    counter = 1;
//                }
//                size--;
//            }
//            i++;
//        }
//        while (!queue.isEmpty()) {
//            res += queue.poll() + "";
//        }
//        return res;
		
		String pre = "1";
        //String res = "";
        int j = 1;
        while (j < n) {
            String temp = "";
            int counter = 1;
            int i = 0;
            for (; i < pre.length() - 1; i++) {
                if (pre.charAt(i) == pre.charAt(i+1)) {
                    counter++;
                } else {
                    temp += counter;
                    temp += pre.charAt(i);
                    counter = 1;
                }
            }
            temp += counter;
            temp += pre.charAt(i);
            pre = temp;
            j++;
        }
        
        return pre;
	}
	
	public String recursive(int n) {
		if (n == 1) return "1";
//		String pre = recursive(n - 1);
//		String res = "";
//		int counter = 1;
//		char c = pre.charAt(0);
//		for (int i = 1; i < pre.length(); i++) {
//			if (pre.charAt(i) == pre.charAt(i - 1)) {
//				counter++;
//			} else {
//				res += counter;
//				res += c + "";
//				counter = 1;
//				c = pre.charAt(i);
//			}
//		}
//		res += counter;
//		res += c + "";
//		return res;
		String pre = countAndSay(n - 1);
        int counter = 1;
        String res = "";
        int i = 0;
        for (; i < pre.length() - 1; i++) {
            if (pre.charAt(i) == pre.charAt(i + 1)) counter++;
            else {
                res += counter; // DONOT SET TEMP, RES HERE IS A FINAL RESULT OF EACH RECURSION 
                res += pre.charAt(i);
                counter = 1;
            }
        }
        res += counter;
        res += pre.charAt(i);
        return res;
	}
	public static void main(String[] args) {
		String s = new CountAndSay().countAndSay(10);
		System.out.println(s);
	}
}
