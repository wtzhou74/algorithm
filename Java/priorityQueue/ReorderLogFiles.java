package priorityQueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

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
	
	public static void main(String[] args) {
		String[] logs = new String[] {"a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo","a2 act car"};
		 new ReorderLogFiles().treeMap(logs);
	}
}
