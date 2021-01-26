package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
//
//Find all strobogrammatic numbers that are of length = n.
//
//Example:
//
//Input:  n = 2
//Output: ["11","69","88","96"]
public class StrobogrammaticNumberII {

	public List<String> findStrobogrammatic(int n) {
        if (n == 0) return new ArrayList<>();
        
        return topdown(n, n);
    }
    
	// 典型的BOTTON-UP 递归， ==> 一直递归往下，到能处理的最深的地方，这里是当n=0或n=1; 然后一层层把正确的值往回包回去 
    public List<String> topdown(int m, int n) {
        List<String> res = new ArrayList<>();
//        if (m == 0) return List.of(""); // List.of() 创建List只适用于 Java 9或以上
//        if (m == 1) return List.of("0", "1", "8");
        if (m == 0) return Arrays.asList("");
        if (m == 1) return Arrays.asList("0", "1", "8");
        
        List<String> midRes = topdown(m - 2, n); //递归一直往下，两个两个(从最外层到最里)往下递归， 到跳出递归从里往外返回
        //然后递归往回，并处理每一层的结果，放入临时变量 res,同时 return到上一层，直到得到最外层的最终结果
        for (String s : midRes) { // midRes 是上一层 return res返回来的，那么我们只需要把前后两个数-比如 6，9补上去就可，得到一个更大的符合条件的数，再return，继续往外扩充直到长度n为止
            if (m != n) //还没到最外层的时候，0可以放； 最外层就不行了，0不能作为一个数的第一个，除非是1位数
                res.add("0" + s + "0");
            res.add("1" + s + "1");// 这些都是中间结果，一层层往里加 1， 6，9等值
            res.add("6" + s + "9");
            res.add("9" + s + "6");
            res.add("8" + s + "8");
            // 以上是在拼接需要的结果
        }
        return res;
        
        // TOP-DOWN  ---递归的过程一层层判断，不合适就直接跳出
        // 判断某个数是否是 strobogrammatic  ===> from StrobogrammaticNumberIII.java
//        List<String> list = new ArrayList<>();
//        public boolean isStrobogrammatic(String number) {
//            if (number.length() == 0)
//                return true;
//            if (number.length() == 1 ) {
//                if (number.charAt(0) == '0' || number.charAt(0) == '1' ||
//                   number.charAt(0) == '8')
//                    return true;
//                else return false;
//            }
//            int left = 0, right = number.length() - 1;
//            //String num = "";
        //	每递归到一层就判断，true才继续往下
//            if ((number.charAt(left) == '6' && number.charAt(right) == '9')
//               || (number.charAt(left) == '9' && number.charAt(right) == '6') || (number.charAt(left) == '8' && number.charAt(right) == '8') ||
//                (number.charAt(left) == '1' && number.charAt(right) == '1')
//                || (number.charAt(left) == '0' && number.charAt(right) == '0'))
//                   if (isStrobogrammatic(number.substring(1, number.length() - 1))) {
//               list.add(number);// 能够到这的说明这个number是合适的，从最里面合适的一个个添加，直到最大且合适的值   
//               return true;        
//                   }
//            return false;
//            }
    }
    
    // 直接计算指定位数的数里面有多少个符合条件的数，  上面 TOP-DOWN 也可以，一个个判断
    // 这里我们借助这种数的特性，来直接统计个数，不输出符合条件的具体数是什么
    public int calculateNum(int m, int n) {
        //List<String> res = new ArrayList<>();
        if (m == 0)
            return 0;
        if (m == 1 ) {
            return 3;
        }
        int temp = calculateNum(m - 2, n);
        int total = 0; // 中间结果
        // 下面统计个数参考上面的方法
        if (temp == 0) { // 因为当 n 是偶数，当递归下去后 m=0，此时return的个数是 0， 如果不对0特殊处理，下面的for
        				//直接就退出了，导致最终结果是 0
        	if (m != n) 
                total += 5; // 对于中间的string, 其长度小于 n, "0**0"的中间结果是可以的
            else
                total += 4;// 但最后与 n一样长度的时候， total只能 +4， 0不能放在开头
        } else {
        	// 对temp for处理，temp是一层符合条件的数的个数，对每个数（所以 for），通过拼接，比如 6-9 等形成新的符合条件的数
        	// 参考上面方法
        	for (int i = 0; i < temp; i++) { // 对n是奇数的，递归到最里面，m=1,不会m=0,return 0的
        		if (m != n)
                    total += 5;
                else
                    total += 4;
            }
        }
        
        
        return total;
    }
    
    public static void main(String[] args) {
    	int total = new StrobogrammaticNumberII().calculateNum(4, 4);
    	List<String> res = new StrobogrammaticNumberII().findStrobogrammatic(3);
    	System.out.println(res);
    }
}
