package arrayString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

//You have an array of logs.  Each log is a space delimited string of words.
//
//For each log, the first word in each log is an alphanumeric identifier.  Then, either:
//
//Each word after the identifier will consist only of lowercase letters, or;
//Each word after the identifier will consist only of digits.
//We will call these two varieties of logs letter-logs and digit-logs.  It is guaranteed that each log has at least one word after its identifier.
//
//Reorder the logs so that all of the letter-logs come before any digit-log.  The letter-logs are ordered lexicographically ignoring identifier, with the identifier used in case of ties.  The digit-logs should be put in their original order.
//
//Return the final order of the logs.
//
// 
//
//Example 1:
//
//Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
//Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
// 
//
//Constraints:
//
//0 <= logs.length <= 100
//3 <= logs[i].length <= 100
//logs[i] is guaranteed to have an identifier, and a word after the identifier.

// 1） 按identifier后的内容排序
// 2） 如果identifier后的内容一样，按identifier排序
public class ReorderLogFiles {

	// TreeMap
	public String[] treeMap(String[] logs) {
		TreeMap<String, String> letterLogs = new TreeMap<>();
		List<String> digitLogs = new ArrayList<>();
		String[] res = new String[logs.length];
		
		for (String log : logs) {
			String identifier = log.substring(0, log.indexOf(" "));
			String rest = log.substring(log.indexOf(" ")).trim();
			
			boolean isDigitLog = Character.isDigit(rest.charAt(0));
			
			if (isDigitLog) digitLogs.add(log);
			else 
				letterLogs.put(rest + identifier, log); // If the rest is same, then sorted by identifier !!!!!!
		}
		
		int i = 0;
		for (Map.Entry<String, String> item : letterLogs.entrySet()) {
			res[i] = item.getValue();
			i++;
		}
		for (String log : digitLogs) {
            res[i] = log;
            i++;
        }
		return res;
	}
	// Priority Queue
	public String[] reorderLogFiles(String[] logs) {
        if (logs.length == 0) return new String[0];
        String[] res = new String[logs.length];
        List<String> digitLogs = new LinkedList<>();
        List<String> letterLogs = new LinkedList<>();
        for (String log : logs) {
            char c = log.charAt(log.indexOf(" ") + 1);
            if (c <= '9' && c >= '0') {
                digitLogs.add(log);
            } else {
                letterLogs.add(log);
            }
        }
        PriorityQueue<String> heap = new PriorityQueue<>(new Comparator<String>() {
            public int compare(String s1, String s2) {
                int idxOf1Word1 = s1.indexOf(" ");
                String subString1 = s1.substring(idxOf1Word1 + 1);
                int idxOf1Word2 = s2.indexOf(" ");
                String subString2 = s2.substring(idxOf1Word2 + 1);
                
                if (subString1.equals(subString2)) {
                	return s1.substring(0, idxOf1Word1).compareTo(s2.substring(0, idxOf1Word2)); // sorted by IDENTIFIER if contents are same
                } else
                	return subString1.compareTo(subString2);
            }
        });
        
        for (String log : letterLogs) {
            heap.offer(log);
        }
        int i = 0;
        while (!heap.isEmpty()) {
            res[i] = heap.poll();
            i++;
        }
        for (String log : digitLogs) {
            res[i] = log;
            i++;
        }
        return res;
        
    }
	
	public String[] reorderLogFiles2(String[] logs) {

        if (logs.length == 0) return new String[0];
        List<String> digitLogs = new ArrayList<>();
        //Map<String, String> map = new HashMap<>();
        PriorityQueue<String[]> pq = new PriorityQueue<>(new Comparator<String[]>(){
            public int compare(String[] s1, String[] s2) {
                if (s1[1].equals(s2[1]))//内容一样
                    return s1[0].compareTo(s2[0]);//按identifier排序
                else return s1[1].compareTo(s2[1]);
            }
        });
        for (String log : logs) {
            String[] items = log.split(" ");
            if (isDigitLog(items[1]))
                digitLogs.add(log);
            else {
                // map.put(items[0], log.substring(items[0].length() + 1, log.length()));
                pq.offer(new String[] {
                    items[0], log.substring(items[0].length() + 1, log.length())
                });
            }
        }
        //map = sortByValue(map);
        String[] res = new String[logs.length];
        int i = 0;
        // for (Map.Entry<String, String> entry : map.entrySet()) {
        //     res[i++] = entry.getKey() + " " + entry.getValue(); //这个Key就不对了，因为identifier是可以重复的
        // }
        while (!pq.isEmpty()) {
            String[] strarr = pq.poll();
            res[i++] = strarr[0] + " " + strarr[1];
        }
        for (String log : digitLogs) {
            res[i++] = log;
        }
        return res;
    }
    
    public boolean isDigitLog(String item) {
        if (Character.isDigit(item.charAt(0)))
            return true;
        return false;
    }
	
	public static void main(String[] args) {
		String[] logs = new String[] {"a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo","a2 act car"};
		 new ReorderLogFiles().treeMap(logs);
	}
}
