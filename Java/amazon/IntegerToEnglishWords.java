package amazon;

import java.util.List;

//Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
//
//Example 1:
//
//Input: 123
//Output: "One Hundred Twenty Three"
//Example 2:
//
//Input: 12345
//Output: "Twelve Thousand Three Hundred Forty Five"
//Example 3:
//
//Input: 1234567
//Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
//Example 4:
//
//Input: 1234567891
//Output: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
public class IntegerToEnglishWords {

	// Divide & Conquer思想， 把整个num三位三位一分，因为对英文的数字三位一分的
	//	对每一份，再进行 hundren, tens的校验
	//			==> 这里需要把比如 one, two, twenty, eleven这些词写下来，因为这些没规律
	
	// iterative solution
	public String numberToWords(int num) {
        if (num == 0)
            return "Zero";
        int billion = num / 1000000000;
        int million = (num % 1000000000) / 1000000;
        int thousand = ((num % 1000000000) % 1000000) / 1000;
        int rest = ((num % 1000000000) % 1000000) % 1000;
        
        String res = "";
        if (billion != 0)
            res += three(billion) + " Billion ";
        if (million != 0)
            res += three(million) + " Million ";
        if (thousand != 0)
            res += three(thousand) + " Thousand ";
        if (rest != 0)
            res += three(rest);
        
        if (res.lastIndexOf(" ") == res.length() - 1)
            return res.substring(0, res.length() - 1);
        else return res;
        
        // 调用递归写法
//        convertNum(num, list);
//        return String.join(" ", list);
            
    }
    
	// 递归写法
	public void convertNum(int num, List<String> res) {
        if (num >= 1000000000) {
            convertNum(num / 1000000000, res);
            res.add("Billion");
            convertNum(num % 1000000000, res);
        } else if (num >= 1000000) {
            convertNum(num / 1000000, res);
            res.add("Million");
            convertNum(num % 1000000, res);
        } else if (num >= 1000) {
            convertNum(num / 1000, res);
            res.add("Thousand");
            convertNum(num % 1000, res);
//        } else {
//        	// 这里可以继续递归往下分
//            String three = three(num);
//            if (three.length() > 0) // 比如 1000
//                res.add(three); // 如果不判断，会把 “”加进去，在输出的时候没做去末尾空串的处理会导致结果不完全正确
//        }
        } else if (num >= 100) {
            convertNum(num / 100, res);
            res.add("Hundred");
            convertNum(num % 100, res);
        } else {
            if (num != 0) {
                String two = two(num);
                res.add(two);
            }
        }
    }
	
    public String three(int num) {
    	if (num == 0) return ""; //用于在递归的时候，过来有可能是0
        int hundred = num / 100;
        int rest = num % 100;
        String res = "";
        String two = two(rest);
        if (hundred != 0) {
            if (rest == 0)
                res += one(hundred) + " Hundred";
            else 
                res += one(hundred) + " Hundred " + two;
        } else {
            if (rest != 0)
                res += two;
        }
        if (res.lastIndexOf(" ") == res.length() - 1)
            return res.substring(0, res.length() - 1);
        else return res;
    }
    
    public String two(int num) {
        if (num < 10)
            return one(num);
        if (num >= 10 && num < 20)
            return lessThan20(num);
        int tens = num / 10;
        System.out.println(tens);
        if (num % 10 == 0)
            return tens(tens);
        else
            return tens(num / 10) + " " + one(num % 10);
    }
    
    public String one(int num) {
        switch(num) {
            case 1: return "One";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
            default: return "";
        }
    }
    
    public String lessThan20(int num) {
        switch(num) {
            case 10: return "Ten";
            case 11: return "Eleven"; 
            case 12: return "Twelve";
            case 13: return "Thirteen";
            case 14: return "Fourteen";
            case 15: return "Fifteen";
            case 16: return "Sixteen";
            case 17: return "Seventeen";
            case 18: return "Eighteen";
            case 19: return "Nineteen";
            default: return "";
        }
    }
    
    public String tens(int num) {
        switch(num) {
            case 2: return "Twenty";
            case 3: return "Thirty";
            case 4: return "Forty";
            case 5: return "Fifty";
            case 6: return "Sixty";
            case 7: return "Seventy";
            case 8: return "Eighty";
            case 9: return "Ninety";
            default: return "";
        }
    }
}
