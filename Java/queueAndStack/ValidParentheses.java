package queueAndStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ValidParentheses {

	public static boolean validParentheseSol(String s)
	{
		if (s == null)
        {
            return false;
        }
        if (s.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++)
        {
        	if (stack.isEmpty()) {stack.push(s.charAt(i)); continue;};
            char temp = (char)stack.peek();
            char target = 0;
            switch(temp){
            case '{' : target = '}'; break;
            case '[' : target = ']'; break;
            case '(' : target = ')'; break;
            }
            if (target == s.charAt(i)) stack.pop();
            else stack.push(s.charAt(i));
            
        }
        if (stack.isEmpty()) return true;
        return false;
	}
}
