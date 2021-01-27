package amazon;

import java.util.Stack;

//Implement a basic calculator to evaluate a simple expression string.
//
//The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.
//
//Example 1:
//
//Input: "3+2*2"
//Output: 7
//Example 2:
//
//Input: " 3/2 "
//Output: 1
//Example 3:
//
//Input: " 3+5 / 2 "
//Output: 5
//Note:
//
//You may assume that the given expression is always valid.
//Do not use the eval built-in library function.
public class BasicCalculatorII {

	public int calculate(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int num = 0;
        char preSign = '+';
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0'; // - '0', 否则得出的结果是 c 的ASCII值
            }
            if (c == '+' || c == '-' || c == '*' 
                || c == '/' || i >= s.length() - 1) {
                if (preSign == '+') {
                    stack.push(num);
                } else if (preSign == '-') {
                    stack.push(-num);
                } else if (preSign == '*') {
                    stack.push(stack.pop() * num);
                } else if (preSign == '/') {
                    stack.push(stack.pop() / num);
                }
                num = 0;
                preSign = c;
            }
            i++;
        }
        
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }
	
	
//	StringBuffer sb = new StringBuffer();
//    while (i < s.length()) {
//        char c = s.charAt(i);
//        if (Character.isDigit(c)) {
//            sb.append(c);
//            i++;
//        } 
//        if (i == s.length() || c == '+' || c == '-'
//                  || c == '*' || c == '/') {
//            if (preSign == '+') {
//                stack.push(Integer.parseInt(sb.toString())); // 这个对 -3+2就不行，因为开始进来 -， pre=+, 而此时sb==null
//            } else if (preSign == '-') {
//                stack.push(-Integer.parseInt(sb.toString()));
//            } else if (preSign == '*') {
//                stack.push(stack.pop() * Integer.parseInt(sb.toString()));
//            } else if (preSign == '/') {
//                stack.push(stack.pop() / Integer.parseInt(sb.toString()));
//            }
//            sb = new StringBuffer();
//            i++;
//        }
//    }
}
