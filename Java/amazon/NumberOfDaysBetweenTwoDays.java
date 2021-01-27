package amazon;

//Write a program to count the number of days between two dates.
//
//The two dates are given as strings, their format is YYYY-MM-DD as shown in the examples.
//
// 
//
//Example 1:
//
//Input: date1 = "2019-06-29", date2 = "2019-06-30"
//Output: 1
//Example 2:
//
//Input: date1 = "2020-01-15", date2 = "2019-12-31"
//Output: 15
// 
//
//Constraints:
//
//The given dates are valid dates between the years 1971 and 2100.
public class NumberOfDaysBetweenTwoDays {

	// 1) 闰年：  普通年能被4整除就是， 千年能被400整除就是
	// 2） 闰年多一天，即 2月份为29天
	// 3） 算天数的时候，1月1号是第一天，即1天
	public int daysBetweenDates(String date1, String date2) {
        int[] days = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] val1 = date1.split("\\-");
        String[] val2 = date2.split("\\-");
        
        if (Integer.parseInt(val1[0]) <= Integer.parseInt(val2[0]))
            return calculateDiff(val1, val2, days);
        else
            return calculateDiff(val2, val1, days);
    }
    
    public int calculateDiff(String[] val1, String[] val2, int[] days) {
        int diff = 0;
       
        int y1 = Integer.parseInt(val1[0]);
        int y2 = Integer.parseInt(val2[0]);
        for (int i = y1; i < y2; i++) {
            if (isLeap(i))
                diff += 366;
            else
                diff += 365;
        }

        // 接下来对月份和日期，====> 直接把month 和 day都转换成 day,算两者之差
        //   ====> 对月份，是从 1月份开始的，1月1号是1天，不是31天 ！！！！
        int m1 = Integer.parseInt(val1[1]);
        int m2 = Integer.parseInt(val2[1]);
        
        int d1 = 0;
        int d2 = 0;
        for (int i = 1; i < m1; i++) { // 这里不能算到当前月，当前月的天数即后面的 day值！！！！
            if (isLeap(y1) && i == 2)
                d1 += 29; // 闰年是29天，在2月份
            else 
                d1 += days[i - 1];
        }
        d1 += Integer.parseInt(val1[2]);
        for (int i = 1; i < m2; i++) {
            if (isLeap(y2) && i == 2)
                d2 += 29;
            else
                d2 += days[i - 1];
        }
        d2 += Integer.parseInt(val2[2]);
        
        diff = diff - (d1 - d2);
        
        return Math.abs(diff);
    }
    
    public boolean isLeap (int year) {
    	// 被4整除 （且非千年，即%100 ！= 0），或者是千年且能被400整除 （||有一个为真就可以为真） （此时%4肯定==0， 因为%400==0）
        if (year % 4 == 0 && (year % 400 == 0 || year % 100 != 0))
            return true;
        else 
            return false;
    }
}
