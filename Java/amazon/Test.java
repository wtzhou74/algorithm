package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

	public List<String> getPartition(String s, int k) {
		Set<String> res = new HashSet<>();
		//Set<Character> set = new HashSet<>();
		
		int i = 0, j = 0;
		while (j < s.length()) {
			if (j - i + 1 < k) {
				//set.add(s.charAt(j));
				j++;
			} else {
				Set<Character> set = new HashSet<>();
				for (int m = i; m <= j; m++) {
					set.add(s.charAt(m));
				}
				if (set.size() == k - 1)
					res.add(s.substring(i, j + 1));
				i++;
				j++;
			}
		}
		return new ArrayList<>(res);
	}
	
	public List<String> getPartition1(String s, int k) {
		Set<String> res = new HashSet<>();
		//Set<Character> set = new HashSet<>();
		Map<Character, Integer> map = new HashMap<>();
		int i = 0, j = 0;
		while (j < s.length()) {
			map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
			if (j - i + 1 < k) {
				//map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
				j++;
			} else {
				if (map.size() == k - 1) {
					res.add(s.substring(i, j + 1));
				}
				int num = map.get(s.charAt(i)) - 1;
				if (num == 0)
					map.remove(s.charAt(i));
				else 
					map.put(s.charAt(i), num);
				i++;
				j++;
				//if (j < s.length()) 
					//map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
			}
		}
		return new ArrayList<>(res);
	}
	
	public static void main(String[] args) {
		String s = "abcdefgh";
		new Test().getPartition(s, 5);
	}
}
