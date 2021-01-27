package amazon;

import java.util.Stack;

//Implement a basic calculator to evaluate a simple expression string.
//
//The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
//
//The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.
//
//You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
//
//Some examples:
//
//"1 + 1" = 2
//" 6-4 / 2 " = 4
//"2*(5+5*2)/3+(6/2+8)" = 21
//"(2+6* 3+5- (3*14/7+2)*5)+3"=-12
public class BasicCalculatorIII {

	// 注意 1） 计算顺序，先括号  ==> 碰到括号先算， 同时，括号算完后，要看括号外的是否 */， 是的话继续， 比如 5- 4*（3+6）
	//		2） 对只有 + - 的，不能像 * / 那样 pop一个计算一个  ===> 借助辅助  Stack   
	//			（由于减法必须得 a -b,所以，在入stack的时候，可以转为加法 （a + (-b)）） ,就无需额外的stack了！！！！  ==> 参考下面calculateRecursive()
	//		3） 对 * +， a+b=b+a; a*b=b*a; 但 - / 一定要注意，换顺序后答案不对  
	public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;
        s = s.replaceAll("\\s", "");
        System.out.println(s);
        Stack<String> stack = new Stack<>();
        int i = 0;
        while (i < s.length()) {
            if (stack.size() == 0) {// 往里放东西，不管是数字还是计算符号
                int j = i;
                while (j < s.length()) {
                    if (Character.isDigit(s.charAt(j)))
                        j++;
                    else
                        break;
                }
                if (i == j) {
                    stack.push(s.charAt(i++) + "");
                } else {
                    stack.push(s.substring(i, j));
                    i = j;
                } 
            } else {
                char c = s.charAt(i);
                // 如果是 计算符号， 放进去
                if (c == '+' || c == '-' || c == '*' || c == '/'
                   || c == '(') {
                    stack.push(c + "");
                    i++;
                    continue;
                }
                int j = i; // 不是的话，有可能是 数字，也有可能是 ”）“
                while (j < s.length()) {
                    if (Character.isDigit(s.charAt(j)))
                        j++;
                    else
                        break;
                }
                //String num = "";
                if (i == j) {
                    // 只能是 ')'
                    calculateAddSubt(stack); // 开始计算括号里的值;
                    i++;
                } else {
                	// 不是碰到 ）， 做 * ， / 并push
                    String num = s.substring(i, j);
                    i = j;
                    //System.out.println(num);
                    String top = stack.peek();
                    //System.out.println(top);
                    if (top.equals("*") || top.equals("/")) {
                        stack.pop();
                        long res = calculate(stack.pop(), num, top);
                        stack.push(String.valueOf(res));
                    } else {
                        stack.push(num);
                    }
                }
            }
        }
        
        calculateAddSubt(stack); // 最后栈内的是无括号的运算，都是 + -
        return Integer.parseInt(stack.pop());
    }
    
    public long calculate(String val1, String val2, String operator) {
        long v1 = Long.parseLong(val1); // String表示的值可能超过 Integer.max
        long v2 = Long.parseLong(val2);
        System.out.println(v1 + " " + operator + " " + v2);
        switch(operator) {
            case "+":
                return v1 + v2;
            case "-":
                return v1 - v2;
            case "*":
                return v1 * v2;
            case "/":
                return v1 / v2;
        }
        
        return 0;
    }
    
    public void calculateAddSubt(Stack<String> stack) {
        
        Stack<String> aux = new Stack<>();// 辅助stack, 用以计算 -， 因为 a -b != b - a
        while (!stack.isEmpty()) { 
            String val = stack.pop();
            if (val.equals("("))
                break;
            else aux.push(val);
        }
        while (!aux.isEmpty()) {
            if (aux.size() == 1) {
                String num = aux.pop();
                //String operator = stack.peek();
                // 这里就是在括号出来后，紧跟着的是 * / 运算，此时要继续运算
                while(!stack.isEmpty() &&
                      (stack.peek().equals("*") || stack.peek().equals("/"))) {
                    String operator = stack.pop();
                    String val2 = stack.pop(); // 这里碰到除非， a / b != b/a，所以val2在前
                    long res = calculate(val2, num, operator);
                    num = String.valueOf(res);
                    //operator = stack.peek();
                }
                stack.push(num);
            } else {
                String val1 = aux.pop();
                String operator = aux.pop();
                String val2 = aux.pop();
                long res = calculate(val1, val2, operator);
                aux.push(String.valueOf(res));
            }
        }
    }
    
    public int calculateRecursive(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        //s = s.replaceAll("\\s", "");//没必要； 但是用 i,j substring()就得要先去空格了
        return helper(s);
    }
    
    int idx = 0;
    //Stack<Integer> stack = new Stack<>(); 
    public int helper(String s) {
        //int sum = 0;
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char preOp = '+';
        // 下面的判断，会自动跳过空格，无需在开始的时候 replaceAll("\\s", "")
        while (idx < s.length()) {// 不能 && s.charAt(idx) != ')'， 否则 5*5）就不能算了，因为计算是在当前character的上一步
        									// 即这里是 ），还是要让它进来，好计算 5*5, 然后再看是否是 ‘）’， 是就break, 根据stack计算当前 （）中的总的值，return
        									// 这也是为什么这块stack是局部的，而不是全局的stack， 因为这个递归函数是有返回值的
            char c = s.charAt(idx);
            if (c == '(') {
                idx++;
                num = helper(s); // 就相当于其中的一块 num, 不是总的sum, 但这里的返回值是一个局部的sum,看最下面的while求和
            } else if(Character.isDigit(c)) {
                num = num * 10 + c - '0'; // 这个是另类的找数字的方法，如果是多位的， 23，2之后，num=2,接下来就是 2 * 10 + 3了，如果只有1位，0*10=0
            } //else 
            // 对上面处理完数字部分，没有idx++,紧接下来就需要做运算了（5+3），所以不能else if
            if (c == '+' || c == '-' || c == '*' || c == ')' // 这里 ‘）’还要进来是计算其前面对应的值， 比如 （5+5*5）， 当idx到‘）’，5*5这时候开始算
                       || c == '/' || idx >= s.length() - 1) {
                if (preOp == '+') {
                    stack.push(num);
                } else if (preOp == '-') { // 如果前面符号是 -， 把这个负值放进去，变成 a + (-b),之后直接pop()就可以处理，无需额外的stack
                    stack.push(-num);
                } else if (preOp == '*') {
                    stack.push(stack.pop() * num);
                } else if (preOp == '/') {
                    stack.push(stack.pop() / num);
                }
                if (c == ')') // 这里不能if else, 否则（递归）最后一个字符 （3*8） preOp, num不会被重新赋值
                    break; // 需要break出去，计算当前 （）内总的值
                num = 0; // 必须重置0，这个num是局部值，比如 a+b的结果
                preOp = c; // 记录之前的运算符号
            }
            idx++;
        }
        int sum = 0;
        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            sum += stack.pop();
        }
        return sum;
    }
    
    public static void main(String[] args) {
    	new BasicCalculatorIII().calculateRecursive("5+3-5");
    }
}
