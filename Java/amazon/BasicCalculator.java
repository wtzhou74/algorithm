package amazon;

import java.util.Stack;

//Implement a basic calculator to evaluate a simple expression string.
//
//The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
//
//Example 1:
//
//Input: "1 + 1"
//Output: 2
//Example 2:
//
//Input: " 2-1 + 2 "
//Output: 3
//Example 3:
//
//Input: "(1+(4+5+2)-3)+(6+8)"
//Output: 23
//Note:
//You may assume that the given expression is always valid.
//Do not use the eval built-in library function.
public class BasicCalculator {

	public int calculate(String s) {
        if (s == null || s.length() == 0)
            return 0;
        return helper(s);
    }
    
    int idx = 0;
    public int helper(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char preOp = '+';
        while (idx < s.length()) {
            char c = s.charAt(idx);
            if (c == '(') {
                idx++;
                num = helper(s);
            } else if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (c == '+' || c == '-' || c == ')' || idx >= s.length() - 1) {
                if (preOp == '+')
                    stack.push(num);
                else if (preOp == '-')
                    stack.push(-num);
                if (c == ')')
                    break;
                num = 0;
                preOp = c;
            }
            idx++;
        }
        int sum = 0;
        while(!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }
    
    
    public int calculateIterative(String s) {

    	if (s == null || s.length() == 0)
            return 0;
        int num = 0; // 当前的一个操作数，每次需要重置0
        int operator = 1; //符号数，用乘法来取代 if (+) if (-), 这样stack就可以直接 int了
        int i = 0;
        int result = 0; //总的结果，(加减)一边遍历一边计算结果，碰到 （ 入栈，等括号内出结果
        Stack<Integer> stack = new Stack<>();
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0'; // 这样就不用 replaceAll去空格了
            } else if (c == '+') {
                result += num * operator; // 乘以符号数，就正/负数
                operator = 1; // 当前的符号数
                num = 0; // 重置操作数
            } else if (c == '-') {
                result += num * operator;
                operator = -1;
                num = 0;
            } else if (c == '(') { // 这就像碰到乘除一样，要先算再放到stack进去
                stack.push(result); // 碰到括号，要暂存当前的值及符号数
                stack.push(operator);
                
                num = 0;
                operator = 1;
                result = 0; // 一定要记得重置 result !!!!, 在括号内，开始常规的运算， 之前的result已经放到stack里了
            } else if (c == ')') {
                result += num * operator; // 算出括号内的最后一次运算
                
                result *= stack.pop(); // *操作符，得出是要 + 还是 - 当前值  a + (-b)
                result += stack.pop(); // 加上之前的值
                
                num = 0;
            }
            i++;
                
        }
        return result + (operator * num); //最后一次计算， 比如 2+3， operator = +， num = 3, result = 2
    }
    
    public static void main(String[] args) {
    	new BasicCalculator().calculateIterative("2+3");
    }
}
