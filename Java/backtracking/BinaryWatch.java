package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).
//
//Each LED represents a zero or one, with the least significant bit on the right.
//
//For example, the above binary watch reads "3:25".
//
//Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.
//
//Example:
//
//Input: n = 1
//Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
//Note:
//The order of output does not matter.
//The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
//The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
public class BinaryWatch {

	// 直接backtrack，统计0/1的所有组合再判断
	public List<String> readBinaryWatch(int num) {
        if (num == 0) return Arrays.asList("0:00");
        List<String> list = new ArrayList<>();
        backtrack(num, list, "", 0, 0);
        List<String> res = new ArrayList<>();
        for (String sol : list) {
//            int hour = getTime(sol.substring(0, 4));
//            int min = getTime(sol.substring(4, 10));
        	int hour = Integer.parseInt(sol.substring(0, 4), 2);
            int min = Integer.parseInt(sol.substring(4, 10), 2);
            String time = "";
            if (hour >= 12 || min >= 60) continue; // 看题目，[0-11], [0-59]
            time += hour + ":";
            if (min < 10)
                time += "0" + min;
            else
                time += min;
            res.add(time);
        }
        return res;
    }
    
	//同样这也是 combination问题，把固定数目的 1，放到 不同的位置形成不同数
    public void backtrack(int num, List<String> list, String sol, int s, int count) {
        if (count == num ) {
            if (sol.length() == 10) { // 虽然 1 的个数够了，但此时 sol并不一定符合，比如 sol = "11"
                list.add(sol); // 只有够了我们才能加进来， 同时 return，出栈
                //System.out.println(sol);
                return;
            }
            //return; // 不能 return, 因为 比如 sol = "11" 还得接着往下找，否则类似  "1100000000"就没了，因为被return掉了
        }
        for (int i = s; i < 10; i++) {
        	//两种选择，每次到一个位置的时候，可以有 0/1 选
            backtrack(num, list, sol + "1", i + 1, count + 1);
            backtrack(num, list, sol + "0", i + 1, count);
        }
        
    }
    
    // 直接 hour-min的所有数字组合，并借助位运算统计 hour/min中 1 的个数
    public List<String> readBinaryWatch2(int num) {
        if (num == 0) return Arrays.asList("0:00");
        List<String> res = new ArrayList<>();
        for (int hour = 0; hour < 12; hour++) {
            for (int min = 0; min < 60; min++) {
                if (countBits(hour) + countBits(min) == num) {
                    if (min < 10)
                        res.add(hour + ":0" + min);
                    else 
                        res.add(hour + ":" + min);
                }
            }
        }
        return res;
    }
    
    public int countBits(int num) {
        //int aux = num;
        int count = 0;
        while (num > 0) {
            count += num & 1;
            num = num >> 1;
        }
        return count;
    }
    
    public int getTime(String digits) {
        int time = 0;
        for (int i = 0; i < digits.length(); i++) {
            if (digits.charAt(i) == '1') {
                time += Math.pow(2, i);
            }
        }
        return time;
    }
    
    public static void main(String[] args) {
    	new BinaryWatch().readBinaryWatch(6);
    }
}
