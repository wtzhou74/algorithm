package queueAndStack;

import java.util.Stack;

public class ReversePolishNotation {

	public static int rpnSol(String[] tokens)
	{
		if (tokens == null){
			return 0;
		}
		Stack<Integer> stack = new Stack<>();
		int result = 0;
		for(int i = 0; i < tokens.length; i++)
		{
			// Character.getNumericValue(tokens.charAt(i)
			if (stack.isEmpty()) {stack.push(Integer.parseInt(tokens[i])); continue;}
			String next = tokens[i];
			if (!next.equals("+") && !next.equals("-") && !next.equals("*") && !next.equals("/"))
			{
				stack.push(Integer.parseInt(next));
				continue;
			}
			int operand1 = stack.pop();
			int operand2 = stack.pop();
			switch (next) {
			case "+": result = operand2 + operand1; stack.push(result); break;
			case "-": result = operand2 - operand1; stack.push(result); break;
			case "*": result = operand2 * operand1; stack.push(result); break;
			case "/": if (operand1 == 0 || operand2 == 0) stack.push(0); // filter divided zero operation
				else {result = operand2 / operand1; stack.push(result);} break;// SECOND/FIRST !!!!!!
			}
			
		}
		return stack.pop();
	}
}
