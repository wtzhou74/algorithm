package queueAndStack;

import java.util.Stack;

public class DecodeString {

	// "3[a2[c]]"
	public static String decodeStr(String s)
	{
		if (s == null)
        {
            return null;
        }
        Stack<Character> stack = new Stack<>();
        char[] characters = s.toCharArray();
        
        StringBuilder result = new StringBuilder();
        for(char c : characters)
        {
            if (c == ']')
            {
                StringBuilder tempStr = new StringBuilder();
                char temp = stack.pop();
                while(temp != '[')
                {
                    tempStr.append(temp);
                    if (!stack.isEmpty())
                    {
                        temp = stack.pop();
                    }
                }
                
                
                StringBuilder digits = new StringBuilder();
                
                while(!stack.isEmpty() && Character.isDigit(stack.peek()))
                {
                    char digit = stack.pop();
                    digits.append(digit);
                }
                
                String subString = tempStr.reverse().toString();
                StringBuilder strBuilder = new StringBuilder();
                int repeatedNum = Integer.parseInt(digits.reverse().toString());
                for (int i = 0; i < repeatedNum; i++)
                {
                    strBuilder.append(subString);
                }
                
                for(char character : strBuilder.toString().toCharArray())
                {
                    stack.push(character);
                }
                
            } else {
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty())
        {
            result.append(stack.pop());
        }
        
        return result.reverse().toString();
    }
	
	public static boolean containDigit(String subString)
	{
		if (subString.isEmpty()) return true;
		return subString.matches("[A-Za-z]*[0-9]+[A-Za-z]*");
	}
	
	 public boolean isDigit(char c) {
	     return (c >= '0' && c <= '9');
	 }
}
