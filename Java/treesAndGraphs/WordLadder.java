package treesAndGraphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
//
//Only one letter can be changed at a time.
//Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
//Note:
//
//Return 0 if there is no such transformation sequence.
//All words have the same length.
//All words contain only lowercase alphabetic characters.
//You may assume no duplicates in the word list.
//You may assume beginWord and endWord are non-empty and are not the same.
//Example 1:
//
//Input:
//beginWord = "hit",
//endWord = "cog",
//wordList = ["hot","dot","dog","lot","log","cog"]
//
//Output: 5
//
//Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//return its length 5.
//Example 2:
//
//Input:
//beginWord = "hit"
//endWord = "cog"
//wordList = ["hot","dot","dog","lot","log"]
//
//Output: 0
//
//Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.



// BETTER SOLUTION ===> Pre-process the given wordList => find all possible GENERIC/INTERMEDIATE states see Solution of WordLadder

public class WordLadder {

	// BFS of N-Array tree/ Graph  // 这里由于 beginWord，endWord都是已知的，那么可以从上下两个方向进行BFS，直到相遇
	public int bfs(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;
        Set<String> set = new HashSet<>(wordList); // set.contains(): O(1); list.contains(): O(n)
//        Map<String, Boolean> visited = new HashMap<>(); // Not necessary for set, and set.removing(visitedWord)
//        visited.put(beginWord, false);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();// size of current level
            level++;
            while (size > 0) {// for each level
            	size--;
            	// Starting check elems of next level
                String word = queue.poll();
                //visited.put(word, true);
                
                // check ALL WORDS in the list is time-consuming  ==> TLE for big size of WordList ==> OPTIMIZATION (set.contains() => O(1))
//                for (int j = 0; j < wordList.size(); j++) {
//                	
//                	// WRONG, should be checked if the candidate is valid.
////                	if (wordList.get(j).equals(endWord)) 
////                	{
////                		return level + 1;
////                	} 
//                	
//                    if ((visited.get(wordList.get(j)) == null || !visited.get(wordList.get(j)))
//                            && isAvailable(word, wordList.get(j))) {
//                    	
//                    	if (wordList.get(j).equals(endWord)) 
//                    	{
//                    		return level + 1;
//                    	}
//                        queue.offer(wordList.get(j));
//                        visited.put(wordList.get(j), false);
//                    }
//                }
                
                for (int i = 0; i < word.length(); i++) {
                	for (char j = 'a'; j <= 'z'; j++) {
                		char[] chars = word.toCharArray();
                		chars[i] = j;
                		//String temp = chars.toString();
                		String temp = new String(chars);
                		/* if (set.contains(temp) && (visited.get(temp) == null || !visited.get(temp)) { */
                		if (set.contains(temp)){
                			if (temp.equals(endWord)) {
                				System.out.println(level);
                				return level + 1;// 个数
                			}
                			set.remove(temp);// Must be added, otherwise TLE for big size of WordList; 会找回去包括自己
                			//visited.put(temp, false);
                			queue.offer(temp);
                		}
                	}
                }
            }          
        }
        return 0;
    }
	
	
	public int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        if (wordList.size() == 0)
            return 0;
        Queue<String> q = new LinkedList<>();
        Map<String, Boolean> visited = new HashMap<>(); //这个可以直接用 set来remove掉已访问的
        					// 参考上面的解法，但这里不能对 wordList直接处理，而是判断字符，否则不能直接remove
        for (int i = 0; i < wordList.size(); i++) {
            visited.put(wordList.get(i), false);
        }
        q.offer(beginWord);
        int count = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            count++;
            while (size > 0) {
                String s = q.poll();
                for (String word : wordList) {
                    if (!visited.get(word) && diff (word, s) == 1) {
                        visited.put(word, true);
                        if (word.equals(endWord))
                            return count++;
                        q.offer(word);
                    }
                }
                size--;
            }
        }
        
        return 0;
    }
    
    public int diff(String w1, String w2) {
    	// 这个判断没必要，因为题设说了长度都一样
//        int d = Math.abs(w1.length() - w2.length());
//        if (d > 1) return d;
        int i = 0, j = 0, d = 0;
        while (i < w1.length() && j < w2.length()) {
            if (w1.charAt(i) != w2.charAt(j)) //转换成 return true/false更好
                d++;
            i++;
            j++;
        }
        return d;
    }
    
    //!!!!bi-direction的时候一定要先判断 endWord是否在wordlist中
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        if (wordList.size() == 0)
            return 0;
        // !!!!bi-direction的时候一定要先判断 endWord是否在wordlist中
        if (!wordList.contains(endWord))
            return 0;
        Queue<String> q1 = new LinkedList<>();
        Queue<String> q2 = new LinkedList<>();
        Map<String, Boolean> visited = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            visited.put(wordList.get(i), false);
        }
        q1.offer(beginWord);
        q2.offer(endWord);
        int count = 1;
        //Set<String> aux = new HashSet<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            int size1 = q1.size();
            int size2 = q2.size();
            count++;
            while (size1 > 0) {
                String s = q1.poll();
                for (String word : wordList) { // 用上面字符转换 a->z的更直观
                	if (diff(word, s) == 1) { // 满足条件下的下一层的word,如果已经在Q2了，说明相遇了
                		if (q2.contains(word))
                            return count++;
                		if (!visited.get(word)) { // 下一层是否需要加入Q,就要看这个 word是已经访问过了                          
                            visited.put(word, true);
                            q1.offer(word);
                        }
                	}
                    
                }
                size1--;
            }
            count++;
            while (size2 > 0) {
                String s = q2.poll();
                for (String word : wordList) {                    
                    if (diff(word, s) == 1) {
                    	if (q1.contains(word))
                            return count++;
                    	if (!visited.get(word)) {
                            q2.offer(word);
                            visited.put(word, true);
                        }
                    }
                    
                }
                size2--;
            }
        }
        
        return 0;
    }
	
	// Bi-direction
	public int biDirectionOfbfs(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;
        Set<String> set = new HashSet<>(wordList);
        // Map<String, Boolean> visited = new HashMap<>();
        // visited.put(beginWord, false);
        Queue<String> downQueue = new LinkedList<>();
        Queue<String> upQueue = new LinkedList<>();
        downQueue.offer(beginWord);
        upQueue.offer(endWord);
        int level = 0;
        while (!downQueue.isEmpty() || !upQueue.isEmpty()) {
            int sizeOfDownQ = downQueue.size();// size of current level
            int sizeOfUpQ = upQueue.size();
            if (sizeOfDownQ > 0) {
            	level++;
            	while (sizeOfDownQ > 0) {// for each level
                    sizeOfDownQ--;
                    String word = downQueue.poll();
                    //visited.put(word, true);
                    for (int i = 0; i < word.length(); i++) {
                    	for (char j = 'a'; j <= 'z'; j++) {
                    		char[] chars = word.toCharArray();
                    		chars[i] = j;
                    		String temp = new String(chars);
                    		if (temp.equals(endWord) || upQueue.contains(temp)) {
                				System.out.println(level);
                				return level + 1;
                			}
                    		if (!temp.equals(word) && set.contains(temp)) {
                    			
                    			// Since set's elem will be removed, THEN upQueue.contains might be NULL if you do set.contains() first.
//                    			if (temp.equals(endWord) || upQueue.contains(temp)) {
//                    				System.out.println(level);
//                    				return level + 1;
//                    			}
                                set.remove(temp);
                    			//visited.put(temp, false);
                    			downQueue.offer(temp);
                    		}
                    	}
                    }
                }
            }
            
            if (sizeOfUpQ > 0) {
            	level++;
            	while (sizeOfUpQ > 0) {// for each level
                    sizeOfUpQ--;
                    String word = upQueue.poll();
                    //visited.put(word, true);
                    for (int i = 0; i < word.length(); i++) {
                    	for (char j = 'a'; j <= 'z'; j++) {
                    		char[] chars = word.toCharArray();
                    		chars[i] = j;
                    		String temp = new String(chars);
                    		if (downQueue.contains(temp)) {
                				System.out.println(level);
                				return level + 1;
                			}
                    		if (!temp.equals(word) && set.contains(temp)) {                    			
                                set.remove(temp);
                    			//visited.put(temp, false);
                    			upQueue.offer(temp);
                    		}
                    	}
                    }
                } 
            }
            
        }
        System.out.println("0");
        return 0;
    }
	
	// TLE
	int min = Integer.MAX_VALUE;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {        
        backtracking(beginWord, endWord, wordList, new ArrayList<String>());
        System.out.println(min);
        return min == Integer.MAX_VALUE ? 0 : min + 1;
    }
    public void backtracking(String beginWord, String endWord, List<String> wordList, 
                            List<String> potentialWords) {
        if (potentialWords.size() > 0 &&
                endWord.equals(potentialWords.get(potentialWords.size() - 1))) {
        	for (int i = 0; i < potentialWords.size(); i++) {
        		System.out.println(potentialWords.get(i));
        	}
        	System.out.println("-----------------");
            min = Math.min(min, potentialWords.size());
            return;
        }
        
        for (int j = 0; j < wordList.size(); j++ ) {
            if (!potentialWords.contains(wordList.get(j)) 
            		&& isAvailable(beginWord, wordList.get(j))) {
                potentialWords.add(wordList.get(j));
                backtracking(wordList.get(j), endWord, wordList, potentialWords);
                potentialWords.remove(potentialWords.size() - 1);
            }
        }
        
    }
    public boolean isAvailable(String beginWord, String next) {
    	// Note: Only ONE LETTER can be changed at a time
    	// "lest" vs "lose" will return TRUE by running the following code, 
    	//even though there have 3 same letters out of 4 letters, pay attention to the ORDER
//        int[] chars = new int[125];
//        for (char c : beginWord.toCharArray()) {
//            chars[c]++;
//        }
//        for (char c : next.toCharArray()) {
//            chars[c]--;
//        }
//        int numOfOne = 0;
//        for (int i = 0; i < 125; i++) {
//            if (chars[i] == 1) numOfOne++;
//            if (numOfOne > 1) return false;
//        }
//        if (numOfOne == 1) return true;
//        else return false;
    	char[] beginChars = beginWord.toCharArray();
    	char[] nxtChars = next.toCharArray();
    	int numOfDiffer = 0;
    	for (int i = 0; i < beginWord.length(); i++) {
    		if (beginChars[i] != nxtChars[i]) numOfDiffer++;
    	}
    	return numOfDiffer == 1 ? true : false;
    	
    }
    
    //private int min = Integer.MAX_VALUE;
    public int ladderLength3(String beginWord, String endWord, List<String> wordList) {
        if (wordList.size() == 0)
            return 0;
        if (beginWord.equals(endWord))
            return 0;
        //int length = 0;
        // ！！！！这个写法是错误的， 会 stackoverflow， 因为 hot->dot 会来回处理，所以需要一个额外的 list记录
        //已选上的，保证下一个时虽然从idx=0，但不会取到已取到的 （双向/无向图）， 如上面 backtracking solution,但会TLE
        for (int i = 0; i < wordList.size(); i++) {
            if (diff(beginWord, wordList.get(i)) == 1) {
                int length = ladderLength3(wordList.get(i), endWord, wordList) + 1;
                min = Math.min(min, length);
            }
        }
        return min;
    }
    
//    public int diff(String w1, String w2) {
//        int d = Math.abs(w1.length() - w2.length());
//        if (d > 1) return d;
//        int i = 0, j = 0;
//        while (i < w1.length() && j < w2.length()) {
//            if (w1.charAt(i) != w2.charAt(j))
//                d++;
//            i++;
//            j++;
//        }
//        return d;
//    }
    
    public static void main(String[] args) {
    	List<String> wordList = new ArrayList<>();
//    	wordList.add("hot");
//    	wordList.add("dog");
    	
    	
    	wordList.add("hot");
    	wordList.add("dot");
    	wordList.add("dog");
    	wordList.add("lot");
    	wordList.add("log");
    	wordList.add("cog");
    	new WordLadder().ladderLength2("hit", "cog", wordList);
    }
}
