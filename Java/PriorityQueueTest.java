import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		sortMapByValueWithPQ();
		// Reference to : TopNBuzzwords.java
		Map<String, int[]> map = new HashMap<>(); 
		// sorted by the occurrences of String in order of most to least, if equal, then sort it in alphabetic
		PriorityQueue<String> heap = new PriorityQueue<>(new Comparator<String>() {
			public int compare(String str1, String str2) {
				int[] freq1 = map.get(str1);
				int[] freq2 = map.get(str2);
				if (freq1[0] != freq2[0]) {// min heap  ==> poll when size > K, the smallest will pop first
					return freq1[0] - freq2[0];
				}
				// now freq1[0] == freq2[0]
				if (freq1[1] != freq2[1]) {
					return freq1[1] - freq2[1];
				}
				return str2.compareTo(str1);// if the occurrences are same, we should keep the toys in alphabetical order, so, we should sort it in REVERSED ORDER.
			}
		});
		
		long x = 1, y = 2;
		Long.compare(x, y); // Long a, b; a.compareTo(b);
		
		PriorityQueue<String> pq = new PriorityQueue<>();
		pq.remove("a");
		pq.contains("a");
		pq.clear();
		
		

		
		////////////////////////////////////////  Integer ----
		Integer i = 5;
		Integer j = 6;
		PriorityQueue<Integer> heap0 = new PriorityQueue<>(new Comparator<Integer> () {
        	public int compare(Integer a, Integer b) {
        		return b - a; //reversed order
        	}
        });
		
//		new PriorityQueue<>((o1, o2) -> {  // 这里是大括号开始，匿名函数 ！！！（a -b 只针对直接的整形数据比较）
//            if (o1[0] == o2[0]) return o1[1] - o2[1];
//            return o1[0] - o2[0];
//        })
		heap0.add(j);
		heap0.add(i);
		
		
		while (!heap0.isEmpty()) {
			System.out.println(heap0.poll()); // 6, 5 
		}
		
		// Alternative
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a); // a是集合中的元素，if int[][] s, then a is s[i] 
		PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);
		
		//PriorityQueue<Double> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a)); // 只有 int可以直接 （a, b） -> a - b 因为默认Comparator.compare() return int

		
		/////////////////////////////// String
		String a = "abc";
		String b = "acd";
		
		PriorityQueue<String> heap1 = new PriorityQueue<>(new Comparator<String> () {
        	public int compare(String a1, String a2) {
        		return a1.compareTo(a2);// sort alphabetically
        	}
        });
		
		heap1.add(a);
		heap1.add(b);
		while (!heap1.isEmpty()) {
			System.out.println(heap1.poll()); // abc, acd
		}
		
		
		///////////////////////////////////////////// String1
		// ReorderLogFiles.java
		PriorityQueue<String> heap3 = new PriorityQueue<>(new Comparator<String>() {
            public int compare(String s1, String s2) {
                int idxOf1Word1 = s1.indexOf(" ");
                String subString1 = s1.substring(idxOf1Word1 + 1);
                int idxOf1Word2 = s2.indexOf(" ");
                String subString2 = s2.substring(idxOf1Word2 + 1);
                
                if (subString1.equals(subString2)) {
                	return s1.substring(0, idxOf1Word1).compareTo(s2.substring(0, idxOf1Word2));
                } else
                	return subString1.compareTo(subString2);
            }
        });
	}
	
	public static void sortMapByValueWithPQ () {
		Map<String, Integer> testMap = new HashMap<>();
		testMap.put("a", 10);
		testMap.put("b", 9);
		testMap.put("c", 5);
		testMap.put("d", 20);
		
		PriorityQueue<Map.Entry<String, Integer>> pq = 
				new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
		for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
			pq.offer(entry);
		}
		
		while (!pq.isEmpty()) {
			Map.Entry<String, Integer> item = pq.poll();
			System.out.println(item.getKey() + " " + item.getValue());
		}
	}
}
