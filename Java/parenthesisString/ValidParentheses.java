package parenthesisString;

import java.util.Stack;

//Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
//
//An input string is valid if:
//
//Open brackets must be closed by the same type of brackets.
//Open brackets must be closed in the correct order.
//Note that an empty string is also considered valid.
//
//Example 1:
//
//Input: "()"
//Output: true
//Example 2:
//
//Input: "()[]{}"
//Output: true
//Example 3:
//
//Input: "(]"
//Output: false
//Example 4:
//
//Input: "([)]"
//Output: false
//Example 5:
//
//Input: "{[]}"
//Output: true
public class ValidParentheses {

	public boolean isValid(String s) {
        if (s.length() == 0) return true;
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        stack.push(chars[0]);
        int i = 1;
        while (!stack.isEmpty()) {
            char top = stack.peek();
            
            char next;
            if (i < s.length()) {
                next = chars[i];
                i++;
            } else break;
            //System.out.println(next + " " + i);
            if ((top == '{' && next == '}') || (top == '[' && next == ']') || (top == '(' && next == ')')) {
                stack.pop();
                if (stack.empty() && i < s.length())//一定要注意为empty但还有没判断char ！！！
                    stack.push(chars[i++]);//同时 i++，去查看下一个字符
                continue;
            }
            stack.push(next);
            
             
        }
        
        return stack.size() == 0 ? true : false;
	}
	
	public static void main(String[] args) {
		new ValidParentheses().isValid("()[]");
	}
}
