package arrayString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

public class BasicCalculatorII {

	public int calculate(String s) {
		if (s.length() == 0) return 0;        
        s = s.replaceAll(" ", ""); // 方法本身并不会改变原字符串
//        if (!s.contains("*") && !s.contains("/") &&
//           !s.contains("+") && !s.contains("-")) return Integer.valueOf(s);
        //Stack<String> stack = new Stack();
        List<Integer> digits = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '+' &&
                    s.charAt(i) != '-' &&
                    s.charAt(i) != '*' &&
                    s.charAt(i) != '/') {
                temp += s.charAt(i);
                if (i == s.length() - 1) {
                	digits.add(Integer.valueOf(temp));
                	break;
                }
                continue;
            }
            if (temp.length() == 0) operators.add(s.charAt(i) + "");
            else {
                digits.add(Integer.valueOf(temp));
                operators.add(s.charAt(i) + "");
                temp = "";
            }
        }
        if (operators.contains("*") || operators.contains("/")) {
            for (int i = 0; i < operators.size();) {
                if (operators.get(i).equals("*")) {
                    int val = digits.get(i) * digits.get(i + 1);
                    digits.set(i, val);
                    digits.remove(i + 1);
                    operators.remove(i);
                    i = 0;// list is changing, so i should go back to 0
                } else if (operators.get(i).equals("/")) {
                    int val = digits.get(i) / digits.get(i + 1);
                    digits.set(i, val);
                    digits.remove(i + 1);
                    operators.remove(i);
                    i = 0;
                } else {
                	i++;// check next operator if current operator is neither / or *
                }
            }
        }
        if (operators.contains("+") || operators.contains("-")) {
            for (int i = 0; i < operators.size();) {
                if (operators.get(i).equals("+")) {
                    int val = digits.get(i) + digits.get(i + 1);
                    digits.set(i, val);
                    digits.remove(i + 1);
                    operators.remove(i);
                    i = 0;
                } else if (operators.get(i).equals("-")) {
                   int val = digits.get(i) - digits.get(i + 1);
                    digits.set(i, val);
                    digits.remove(i + 1);
                    operators.remove(i);
                    i = 0;
                } else {
                	i++;
                }
            }
        }
        return digits.get(0);
    }
	

	// 用 stack的时候一定要注意在最后stack中只有 + - 运算的时候，要先从栈底先做，因为 + - 是从左往右的，否则 2-5+3 结果会错
	public int calculate1(String s) {
		if (s == null || s.length() == 0)
            return 0;
        s = s.replaceAll(" ", "");
        //System.out.println(s);
        Stack<String> stack = new Stack<>();
        int j = 0;
        while (j < s.length()) {
            if (stack.isEmpty()) {
                int k = j;
                //String temp = "";
                // !!!!下面要做 substring, 所以 k可以到达最后一个元素的位置（不是索引），所以这里要 ==， 
                                   // 同时 == 的时候要特殊处理
                while (k <= s.length()) {
                    if (k == s.length() || isOperator(s.charAt(k))) {
                        stack.push(s.substring(j, k));
                        j = k;
                        break;
                    }
                    k++;
                }
                continue;
            }
            String val = stack.peek();
            if (val.equals("*") || val.equals("/")) {
                stack.pop();
                int num = Integer.valueOf(stack.pop()); 
                int k = j;
                String temp = "";
                while (k <= s.length()) {
                    if (k == s.length() || isOperator(s.charAt(k))) {
                        System.out.println(j + " " + k);
                        temp = s.substring(j, k);
                        j = k;
                        break;
                    }
                    k++;
                }
                if (val.equals("*")) 
                    stack.push(String.valueOf(Integer.valueOf(temp) * num));
                else {
                    stack.push(String.valueOf(num / Integer.valueOf(temp)));
                }
                continue;
            }
            if (!Character.isDigit(s.charAt(j))) {
                stack.push(s.charAt(j) + "");
                j++;
            } else {
                int k = j;
                //String temp = "";
                while (k <= s.length()) {
                    if (k == s.length() || isOperator(s.charAt(k))) {
                        stack.push(s.substring(j, k));
                        j = k;
                        break;
                    }
                    k++;
                }
            }
            
        }
        int res = 0;
        // 剩下的是 +/-了，但 +/-是从左往右的，而上面stack，会让左边的在栈底，即后出来，所以对 3-5+2 会先算 5 + 2
        // ===> 所以这里我们要把里面的元素反过来，所以这里我们借助额外的一个 stack.
        Stack<String> aux = new Stack<>();
        while(!stack.isEmpty()) {
            aux.push(stack.pop());
        }
        while (!aux.isEmpty()) {
            int num1 = Integer.valueOf(aux.pop());
            if (aux.isEmpty()) {
                res = num1;
                break;
            }
            String operator = aux.pop();
            int num2 = Integer.valueOf(aux.pop());
            if (operator.equals("+"))
                aux.push(String.valueOf(num1 + num2));
            else {
                //System.out.println(num1 + " " + num2);
                aux.push(String.valueOf(num1 - num2));
            }
        }
        return res;
    }
    
    public boolean isOperator(char c) {
        if (c == '+' || c == '-'
           || c == '*' || c == '/')
            return true;
        else return false;
    }
	public static void main(String[] args) {
		new BasicCalculatorII().calculate1("5");
	}
}
