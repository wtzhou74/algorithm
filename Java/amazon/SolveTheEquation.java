package amazon;

//Solve a given equation and return the value of x in the form of string "x=#value". The equation contains only '+', '-' operation, the variable x and its coefficient.
//
//If there is no solution for the equation, return "No solution".
//
//If there are infinite solutions for the equation, return "Infinite solutions".
//
//If there is exactly one solution for the equation, we ensure that the value of x is an integer.
//
//Example 1:
//Input: "x+5-3+x=6+x-2"
//Output: "x=2"
//Example 2:
//Input: "x=x"
//Output: "Infinite solutions"
//Example 3:
//Input: "2x=x"
//Output: "x=0"
//Example 4:
//Input: "2x+3x-6x=x+2"
//Output: "x=-1"
//Example 5:
//Input: "x=x+2"
//Output: "No solution"
public class SolveTheEquation {

	// 不能简单直接看单个char, 比如 56 + 190x
	// x / -x, 这个在算 coefficient的时候要注意， +1/-1
	public String solveEquation0(String equation) {
        int numOfX = 0;
        int sum = 0;
        equation.replaceAll(" ", "");
        String[] strs = equation.split("=");
        for (String s : strs[0].split("(?=\\+)|(?=\\-)")) { // 这个正则表示匹配+/-前面的，但这个+/-不被用于运算(匹配的内容本身不包括+/-)
        					// 6x+3-4x  ==》 [6x, +3, -4x]
            if (s.indexOf('x') >= 0) {
                numOfX += parsing(s);
            } else {
                sum -= parsing(s);
            }
        }
        
        for (String s : strs[1].split("(?=\\+)|(?=\\-)")) {
            if (s.indexOf('x') >= 0) {
                numOfX -= parsing(s);
            } else {
                sum += parsing(s);
            }
        }
        
        
        System.out.println(numOfX);
        System.out.println(sum);
        if (numOfX == 0)
            if (sum == 0)
                return "Infinite solutions";
            else
                return "No solution";
        else
            return "x=" + sum / numOfX;
    }
    
    public int parsing(String s) {
        if (s.indexOf('x') >= 0) {
            if (s.indexOf('x') == 0) {
                return 1;
            } else {
                String co = s.substring(0, s.length() - 1);
                if (co.equals("+"))
                    return 1;
                else if (co.equals("-"))
                    return -1;
                else 
                    return Integer.parseInt(co);
            }
        } else {
            return Integer.parseInt(s);
        }
    }
    
	public String solveEquation(String equation) {
        int numOfX = 0;
        int sum = 0;
        equation.replaceAll(" ", "");
        int idxOfE = equation.indexOf("=");
        //String[] lr = equation.split("=");
        String left = equation.substring(0, idxOfE);
        String right = equation.substring(idxOfE + 1, equation.length());
        System.out.println(right);
        // right.replaceAll("\\+", "-");
        // right.replaceAll("\\-", "+");
        
         int i = 0;
         while (i < left.length()) {
             if (i == 0 && left.charAt(i) == 'x') {
                 numOfX++;
                 i++;
                 continue;
             }
            
             char operator = i == 0 ? (left.charAt(i) == '+' || left.charAt(i) == '-' ? left.charAt(i++) : '+') : left.charAt(i++);
             String temp = "";
             while(i < left.length() && (left.charAt(i) != '+'
                                    && left.charAt(i) != '-')) {
                 if (left.charAt(i) != 'x') {
                     temp += left.charAt(i);
                     i++;
                 } else 
                     break;
             }
//             //System.out.println(temp);
//             //System.out.println(left.charAt(i));
             if (i < left.length() && left.charAt(i) == 'x') {
                 if (operator == '+')
                     numOfX = temp.length() == 0 ? 
                         numOfX + 1 : numOfX + Integer.parseInt(temp);
                 else
                     numOfX = temp.length() == 0 ? 
                         numOfX - 1 : numOfX - Integer.parseInt(temp);
                 i++;
             } else {
                 if (operator == '+')
                     sum -= Integer.parseInt(temp);
                 else
                     sum += Integer.parseInt(temp);
             }
         }
        
         i = 0;
         while (i < right.length()) {
             if (i == 0 && right.charAt(i) == 'x') {
                 numOfX--;
                 i++;
                 continue;
             }
            
             char operator = i == 0 ? (right.charAt(i) == '+' || right.charAt(i) == '-' ? right.charAt(i++) : '+') : right.charAt(i++);
             String temp = "";
             while(i < right.length() && (right.charAt(i) != '+'
                                    && right.charAt(i) != '-')) {
                 if (right.charAt(i) != 'x') {
                     temp += right.charAt(i);
                     i++;
                 } else
                     break;
             }
//             //System.out.println(temp);
             if (i < right.length() && right.charAt(i) == 'x') {
                 if (operator == '+')
                     numOfX = temp.length() == 0 ? 
                         numOfX - 1 : numOfX - Integer.parseInt(temp);
                     //numOfX -= Integer.parseInt(temp);
                 else
                     numOfX = temp.length() == 0 ? 
                         numOfX + 1 : numOfX + Integer.parseInt(temp);
                 i++;
             } else {
                 if (operator == '+')
                     sum += Integer.parseInt(temp);
                 else
                     sum -= Integer.parseInt(temp);
             }
         }
        
        System.out.println(numOfX);
        System.out.println(sum);
        if (numOfX == 0 && sum == 0)
            return "Infinite solutions";
        if (numOfX > 0 || numOfX < 0)
            return "x=" + sum / numOfX;
        if (numOfX == 0 && sum != 0)
            return "No solution";
        return "";
    }
	
	int numOfX = 0;
    int sum = 0;
    public String solveEquation1(String equation) {
        // int numOfX = 0; // 用primitive type做参数传递，返回回来的值不对，这里要用全局primitive变量，或者对象
        // int sum = 0;
        equation.replaceAll(" ", "");
        int idxOfE = equation.indexOf("=");
        String left = equation.substring(0, idxOfE);
        String right = equation.substring(idxOfE + 1, equation.length());
        System.out.println(right);
        
        calculate(left, true);
        calculate(right, false);
        
        System.out.println(numOfX);
        System.out.println(sum);
        if (numOfX == 0)
            if (sum == 0)
                return "Infinite solutions";
            else
                return "No solution";
        else
            return "x=" + sum / numOfX;
    }
    
    public void calculate(String str, boolean isLeft) {
        int i = 0;
        while (i < str.length()) {
            if (i == 0 && str.charAt(i) == 'x') {
                numOfX = isLeft ? numOfX + 1 : numOfX - 1;
                i++;
                continue;
            }
            
            char operator = i == 0 ? (str.charAt(i) == '+' || str.charAt(i) == '-' ? str.charAt(i++) : '+') : str.charAt(i++);
            String temp = "";
            while(i < str.length() && (str.charAt(i) != '+'
                                   && str.charAt(i) != '-')) {
                if (str.charAt(i) != 'x') {
                    temp += str.charAt(i);
                    i++;
                } else 
                    break;
            }
            if (i < str.length() && str.charAt(i) == 'x') {
                if (operator == '+')
                    if (isLeft)
                        numOfX = temp.length() == 0 ? 
                            numOfX + 1 : numOfX + Integer.parseInt(temp);
                    else 
                        numOfX = temp.length() == 0 ? 
                            numOfX - 1 : numOfX - Integer.parseInt(temp);
                else
                    if (isLeft)
                        numOfX = temp.length() == 0 ? 
                            numOfX - 1 : numOfX - Integer.parseInt(temp);
                    else
                        numOfX = temp.length() == 0 ? 
                            numOfX + 1 : numOfX + Integer.parseInt(temp);
                i++;
            } else {
                if (operator == '+')
                    if (isLeft)
                        sum -= Integer.parseInt(temp);
                    else
                        sum += Integer.parseInt(temp);
                else
                    if (isLeft)
                        sum += Integer.parseInt(temp);
                    else
                        sum -= Integer.parseInt(temp);
            }
        }
        
    }
}
