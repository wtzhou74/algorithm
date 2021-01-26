package math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FizzBizz {

	public List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        map.put(3, "Fizz");
        map.put(5, "Buzz");
        
        for (int i = 1; i <= n; i++) {
            String temp = "";
            // Solution 1
            // if (isMultipleOfThree(i) && !isMultipleOfFive(i)) {
            //     res.add("Fizz");
            //     continue;
            // } else if (!isMultipleOfThree(i) && isMultipleOfFive(i)) {
            //     res.add("Buzz");
            // }
            // else if (isMultipleOfThree(i) && isMultipleOfFive(i)) {
            //     res.add("FizzBuzz");
            // } else
            // {
            //     res.add(String.valueOf(i));
            // }
            
            // Solution 2
//             if (isMultipleOfThree(i)) temp += "Fizz";
//             if (isMultipleOfFive(i)) temp += "Buzz";
//             if (temp.length() == 0) temp += i;
            
//             res.add(temp);
            
            // Solution 3  ==> in case if there are 3, 5, 7,....
            for (Map.Entry<Integer, String> s : map.entrySet()) {
                if (i % s.getKey() == 0) temp += s.getValue();
            }
            if (temp.length() == 0) {
                temp += i;
            }
            res.add(temp);
            
        }
        return res;
    }
    
    public boolean isMultipleOfThree(int num) {
        return num % 3 == 0 ? true : false;
    }
    
    public boolean isMultipleOfFive(int num) {
        return num % 5 == 0 ? true : false;
    }
}
