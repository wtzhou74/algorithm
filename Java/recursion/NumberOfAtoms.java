package recursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

//Given a chemical formula (given as a string), return the count of each atom.
//
//An atomic element always starts with an uppercase character, then zero or more lowercase letters, representing the name.
//
//1 or more digits representing the count of that element may follow if the count is greater than 1. If the count is 1, no digits will follow. For example, H2O and H2O2 are possible, but H1O2 is impossible.
//
//Two formulas concatenated together produce another formula. For example, H2O2He3Mg4 is also a formula.
//
//A formula placed in parentheses, and a count (optionally added) is also a formula. For example, (H2O2) and (H2O2)3 are formulas.
//
//Given a formula, output the count of all elements as a string in the following form: the first name (in sorted order), followed by its count (if that count is more than 1), followed by the second name (in sorted order), followed by its count (if that count is more than 1), and so on.
//
//Example 1:
//Input: 
//formula = "H2O"
//Output: "H2O"
//Explanation: 
//The count of elements are {'H': 2, 'O': 1}.
//Example 2:
//Input: 
//formula = "Mg(OH)2"
//Output: "H2MgO2"
//Explanation: 
//The count of elements are {'H': 2, 'Mg': 1, 'O': 2}.
//Example 3:
//Input: 
//formula = "K4(ON(SO3)2)2"
//Output: "K4N2O14S4"
//Explanation: 
//The count of elements are {'K': 4, 'N': 2, 'O': 14, 'S': 4}.
//Note:
//
//All atom names consist of lowercase letters, except for the first character which is uppercase.
//The length of formula will be in the range [1, 1000].
//formula will only consist of letters, digits, and round parentheses, and is a valid formula as defined in the problem.

public class NumberOfAtoms {

	// 本质也是个 parentheses 的递归问题
	// Time : O(N^2), in which, O(N) to parse through the formula, but each of O(N) multiplicities after a bracket
	// 								may increment the count of each name in the formula, leading to an O(N^2) complexity
	public String countOfAtoms(String formula) {
        if(formula.length() == 1)
            return formula;
        Stack<String[]> stack = new Stack<>();
        int i = 0;
        while (i < formula.length()) {
        	// 对开头是大写字母，说明一个formula的开始，把与其对应的数字也一起放入stack
            if (isUpperCase(formula.charAt(i))) {
                i = generateUnit(formula, i, stack);
                continue;
            }
            // ( 放进去，这是到时候出栈到一个位置的结束，其里面的元素需要 * 括号外紧跟的数字
            if (formula.charAt(i) == '(') {
                stack.push(new String[]{"(", "0"});
                i++;
                continue;
            } else if (formula.charAt(i) == ')') {//开始pop,并相乘更新括号里面元素的个数了
                // get the number
                int j = i + 1;
                String num = "";
                while (j < formula.length() && isDigit(formula.charAt(j))) {
                    num += formula.charAt(j);
                    j++;
                }
                i = j; // ！！！！注意更新下一个 i 的值
                if (num.length() == 0) num = "1";
                List<String[]> aux = new ArrayList<>();
                // 更新括号里面元素的个数
                while (!stack.isEmpty()) {
                    String[] s = stack.pop();
                    if (s[0].equals("(")) {
                        break;
                    }
                    int newVal = Integer.valueOf(s[1]) * Integer.valueOf(num);
                    s[1] = String.valueOf(newVal);
                    aux.add(s);
                }
                // 更新数字后并重新放入栈，用以更外层的处理
                for (String[] s : aux) {
                    stack.push(s);
                }
            }
            
        }
        TreeMap<String, String> map = new TreeMap<>();//元素及其个数都取完后，进行排序
        while (!stack.isEmpty()) {
            String[] s = stack.pop();
            if (map.containsKey(s[0])) {//在之前取元素的时候，有重复元素，这些元素的个数要合并相加 ！！！
                int newVal = Integer.valueOf(map.get(s[0])) + Integer.valueOf(s[1]);
                map.put(s[0], String.valueOf(newVal));
            } else {
                map.put(s[0], s[1]);
            }
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            if (val.equals("1")) // 输出时， 个数 1 不输出
                sb.append(key);
            else sb.append(key + val);
        }
        return sb.toString();
    }
    
	// 确定当前元素以及对应的个数
    public int generateUnit(String formula, int i, Stack<String[]> stack) {
        
        String unit = formula.charAt(i) + "";
        int j = i + 1;
        String num = "";
        while (j < formula.length() && (isLowerCase(formula.charAt(j)) || isDigit(formula.charAt(j)))) {
            if (isLowerCase(formula.charAt(j))) {
                unit += formula.charAt(j);
                j++;
            } else if (isDigit(formula.charAt(j))) {
                num += formula.charAt(j);
                j++;
            }
        }
        if (num.length() == 0) num = "1";
        stack.push(new String[]{unit, num});
        i = j;
            
        return i;
    }
    
    public boolean isUpperCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return true;
        } else return false;
    }
    
    public boolean isLowerCase(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        } else return false;
    }
    
    public boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        } else return false;
    }
    
    
    // recursive
    int idx; // 这里需要一个全局变量, 这个idx是指向原字符串的，
    		// 只能一个个往后面取，不能中断，也不能返回，所以不能设置一个针对递归的一次全局变量
    public String countOfAtomsRecursive(String formula) {
        StringBuilder ans = new StringBuilder();
        idx = 0;
        Map<String, Integer> count = parse(formula);
        for (String name: count.keySet()) {
            ans.append(name);
            int multiplicity = count.get(name);
            if (multiplicity > 1) ans.append("" + multiplicity);
        }
        return new String(ans);
    }

    public Map<String, Integer> parse(String formula) {
        int N = formula.length();
        Map<String, Integer> count = new TreeMap<>();//按字符排序，局部变量
        while (idx < N && formula.charAt(idx) != ')') {
            if (formula.charAt(idx) == '(') { // 需要递归
            	idx++;
                for (Map.Entry<String, Integer> entry: parse(formula).entrySet()) { // 递归括号内部的formulas； 往里走的过程
                    count.put(entry.getKey(), count.getOrDefault(entry.getKey(), 0) + entry.getValue()); // 同时解析返回的结果放入 map，这时候同时要看是否已有
                }
            } else { // 对于不是 （ 开头的， 正常解析formula
                int iStart = idx++;
                while (idx < N && Character.isLowerCase(formula.charAt(idx))) idx++; // 对一个新formula,大写字母只能有一个！！！
                String name = formula.substring(iStart, idx); // 得到formula名字
                iStart = idx;//找数字
                while (idx < N && Character.isDigit(formula.charAt(idx))) idx++;
                int multiplicity = iStart < idx ? Integer.parseInt(formula.substring(iStart, idx)) : 1;// 如果是 1，原字符串是不写的
                count.put(name, count.getOrDefault(name, 0) + multiplicity); //把这个放入map  （ + ， 因为这时候的 names 都在一个括号内，同一层）
            }
        }
        // 这块是出 ） 括号了，这时候 如果有数字，就需要 * map里面有的formulas，
        // 	如果没有括号，或者连续的括号，比如 （）（），都会在上面的while中执行完
        int iStart = ++idx; // 先++是因为前面有 ‘）’
        while (idx < N && Character.isDigit(formula.charAt(idx))) idx++;
        if (iStart < idx) { // 有数字，相乘，否则不用管，继续往外层走
            int multiplicity = Integer.parseInt(formula.substring(iStart, idx));
            for (String key: count.keySet()) {
                count.put(key, count.get(key) * multiplicity); //相乘，
            }
        }
        return count;
    }

}
