package twoPointers;

import java.util.HashMap;
import java.util.Map;

//Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
//
//Example:
//
//Input: S = "ADOBECODEBANC", T = "ABC"
//Output: "BANC"
//Note:
//
//If there is no such window in S that covers all characters in T, return the empty string "".
//If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
public class MinimumWindowSubstring {

	public String minWindowOptimized(String s, String t) {
        if (s.length() == 0 || s.length() < s.length()) return "";
        int l = 0, r = 0;
        int counter = t.length(); // 这个很关键，用来记录当前是否有solution （counter==0）
        int[] aux = new int[128]; // ASCII values作为数组下标
        for (char c : t.toCharArray()) {
            aux[c]++;
        }
        int len = Integer.MAX_VALUE;
        // String res = "";
        int lboard = 0, rboard=0; //a记录满足条件的window，这样就不用每次 substring()
        while (r < s.length()) {
            if (aux[s.charAt(r)] > 0) //a只有是T中的字符，才影响counter； 碰到 >0 也就是意味着这是需要的字符
                counter--;
            aux[s.charAt(r)]--;//a然后对所有字符都 -- 操作，这样非T中字符不会影响判断结果 （负数说明当前串内这个字符有多余的了）
            r++;
            while (counter == 0) { //a counter == 0，说明找到一个solution,
            						// a同时接下来,就要判断是否有更优的solution，在这个window内（counter==0要成立）
                // if (r - l < len) {
                //     res = s.substring(l, r); //a这里不 r + 1是因为 r++在上面已经先做了
                //     len = r - l;
                // }
                if (r - l < len) { //a只记录两个边界，不先直接取substring
                    lboard = l;
                    rboard = r;
                    len = rboard - lboard;
                }
                if (aux[s.charAt(l)] == 0) //a对于aux中有等于0的字符，这个肯定是T中的， 
                							//a其他不在T中但等于0的不会在这里碰到，否则之前就 --操作过了，这块是回操作，只会碰到已操作过的字符
                    counter++;//a此时加回去，意味着我们需要这个字符了
                aux[s.charAt(l)]++;//把需要的这个字符需要的个数加 1, 意味着接下来要找这个字符 
                					//a（其他不是T中的字符，加到0也没关系，因为 left走了，不会再回到这个字符了）
                l++;
            }
        }
        return s.substring(lboard, rboard);
    }
	
	// a两个map的solution
	// a1） 确定包含了 T的字串  ； 2） 确定了子串后再取其最短的
	public String minWindow2(String s, String t) {
		if (s.length() == 0 || s.length() < s.length()) return "";
        int i = 0, j = 0;
        Map<Character,Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> aux = new HashMap<>();
        int len = Integer.MAX_VALUE;
        String res = "";
        while (j < s.length()) {
            char c = s.charAt(j);
            if (!map.containsKey(c)) {
                j++;
                continue;
            }
            aux.put(c, aux.getOrDefault(c, 0) + 1); // 这里不是putIfAbsent(),否则不会被更新
            boolean flag = false;
            if (aux.size() == map.size()) {
            	// a判断是否找到
                int temp = aux.size();
                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    if (entry.getValue() > aux.get(entry.getKey())) {
                        break;
                    }
                    temp--;
                }
                if(temp == 0)
                    flag = true;
            }
            if (flag) {
            	// a持续移动 i,直到找到 solution 为止
            	// 1） 当前字符不在T中， 2）在，但不影响结果，因为我们要找的是“最短的”， e.g. [AAABC], T = [AABC],第一个A无效
                while (!map.containsKey(s.charAt(i))
                      || (map.containsKey(s.charAt(i)) && aux.get(s.charAt(i)) > map.get(s.charAt(i)))) {
                    if (aux.containsKey(s.charAt(i)) && aux.get(s.charAt(i)) > map.get(s.charAt(i)))
                        aux.put(s.charAt(i), aux.get(s.charAt(i)) - 1);//a对无效的包含字符，要remove掉
                    i++;
                }
                if (len > j - i + 1) {
                	res = s.substring(i, j + 1);
                	len = j - i + 1;
                }
                aux.put(s.charAt(i), aux.get(s.charAt(i)) - 1);
                i++;
            }
            j++; 
        }
        
        // d对 j == length 没必要，因为 == len - 1时候已判断，不过这点经常要引起注意
        
//	        if (aux.size() == map.size()) {
//	            int temp = aux.size();
//	            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//	                if (entry.getValue() > aux.get(entry.getKey())) {
//	                    break;
//	                }
//	                temp--;
//	            }
//	            if (temp == 0) {
//	                while (!map.containsKey(s.charAt(i))
//	                      || (map.containsKey(s.charAt(i)) && aux.get(s.charAt(i)) > map.get(s.charAt(i)))) {
//	                    if (aux.containsKey(s.charAt(i)))
//	                        aux.put(s.charAt(i), aux.get(s.charAt(i)) - 1);
//	                    i++;
//	                }
//	                if (len > j - i + 1)
//	                    res = s.substring(i, j + 1);
//	            }
//	            
//	        }
        
        return res;
    }
	
	public String minWindow(String s, String t) {
        if (s.length() == 0 || s.length() < t.length()) return "";
        String res = "";
        Map<Character, Integer> init = new HashMap<>();
        for (Character c : t.toCharArray()) {
            Integer val = init.putIfAbsent(c, 1);
            if (val != null) {
                init.put(c, val + 1);
            }
        }
        Map<Character, Integer> counter = new HashMap<>();
        int left = 0, right = 0;
        int size = 0;
        boolean found = false;
        while (found == true || right < s.length()) {// if found is true, we still need to check the substring if left++, even thought right == s.length()!!!!
        	if (!found) { // Does not find, moving right
        		if (init.containsKey(s.charAt(right))) {
                    Integer num = counter.get(s.charAt(right));
                    if (num == null || num == 0) {
                        size++;
                        counter.put(s.charAt(right), 1);
                    } else {
                        counter.put(s.charAt(right), num + 1);
                    }
                }
                right++;
        	}
            
        	// find all required chars, how check if it covers ALL (calculate DUPLICATEs) characters in T
            if (size == init.size()) {
            	found = true;
                for (Map.Entry<Character, Integer> entry : init.entrySet()) {
                    if (entry.getValue() > counter.get(entry.getKey())) {
                        found = false;
                        break;
                    }
                }                                
            }
            // move left if a solution was found
            if (found) {
            	String temp = s.substring(left, right);// right++ before
                if (res.length() == 0 || temp.length() < res.length()) res = temp;
                if (counter.get(s.charAt(left)) != null) {
                    counter.put(s.charAt(left), counter.get(s.charAt(left)) - 1);
                    if (counter.get(s.charAt(left)) == 0) {
                    	size--;
                    	found = false;
                    }
                }
                left++;
            }
        }
        return res;
    }
	
	
	// Using int[] count = new int[128] to keep record of occurrence of each elem, rather than MAP
	// head/left + windowSize , instead of using right - left
	
	// Add char till find the first solution
	// move left to check if better solution exists
	// move right again...
	public String optimizedSol(String s, String t) {
		int[] count = new int[128];
        for (char a : t.toCharArray()) count[a]++; // ASCII values

        int counter = t.length(), left = 0, right = 0;
        int head = -1, window = Integer.MAX_VALUE;
        while (right < s.length()) {
        	if (count[s.charAt(right)] > 0) {
        		//count[s.charAt(right)]--;
        		counter--;
        	}
        	count[s.charAt(right)]--;// For chars not in T should also be --, then they can be identified when moving left below!!!!!
        	right++;
        	// find a candidate
        	while (counter == 0) {
        		if (window > right - left) {
        			head = left;// keep currect left, the last left might be not the left of solution
        			window = right - left;//window size
        		}
        		// move left to check if the substring it is still a solution
        		if (count[s.charAt(left)] == 0) {// < 0. for those chars (and duplicate chars in T) which are not in T, their value are less than 0
        			// Except for the initial solution, each subsequent elem in T can only affect counter INDEPENDENTLY. 
        			counter++;// == 0 means current char is one elem of the solution, so need to be found in the subsequent substring to get a solution if move it here
        		}
        		count[s.charAt(left)]++; // for duplicates, like ABBBABC, and t = "ABC"
    			left++;
        		
        	}
        }
        return head == -1 ? "" :  s.substring(head, head + window); // using head and WINDOW SIZE instead of keep record of RIGHT - LEFT
	}
	
	
	public String minWindow3(String s, String t) {
        if (s == null || s.length() == 0 
            || t == null || t.length() == 0)
            return "";
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        String result = "";
        int count = t.length();
        int i = 0, j = 0;
        while (i < s.length()) {
            if (map.containsKey(s.charAt(i)) && map.get(s.charAt(i)) > 0) {
                count--;                
            }
            map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
            i++;
            
            while (count == 0) {
                System.out.println(s.substring(j, i + 1));
                if (result.length() == 0 || i - j < result.length()) {
                    result = s.substring(j, i);
                }
                if (map.containsKey(s.charAt(j)) && map.get(s.charAt(j)) == 0) {
                    count++;
                }
                if (map.containsKey(s.charAt(j)))
                    map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
                j++;
            }
        }
        return result;
    }
	public static void main(String[] args) {
		new MinimumWindowSubstring().minWindowOptimized("AAADOBECODEBANC", "ABC");
	}
}
