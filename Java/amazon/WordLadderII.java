package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

//Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:
//
//Only one letter can be changed at a time
//Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
//Note:
//
//Return an empty list if there is no such transformation sequence.
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
//Output:
//[
//  ["hit","hot","dot","dog","cog"],
//  ["hit","hot","lot","log","cog"]
//]
//Example 2:
//
//Input:
//beginWord = "hit"
//endWord = "cog"
//wordList = ["hot","dot","dog","lot","log"]
//
//Output: []
//
//Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
public class WordLadderII {

	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        if (beginWord.equals(endWord))
            return new ArrayList<>();
        // DFS 的解法 TLE  ===> 参考下面的BFS + DFS
        findPath(beginWord, endWord, wordList, new ArrayList<>());
        return new ArrayList<>(res);
    }
    
    Set<List<String>> res = new HashSet<>(); // 会有重复
    int min = Integer.MAX_VALUE; // 这里只保留最短的
    // DFS TLE
    public void findPath(String beginWord, String endWord,
                         List<String> wordList, List<String> words) {
        if (beginWord.equals(endWord)) {
            words.add(endWord);
            if (min >= words.size()) {
                if (min > words.size()) res.clear();
                List<String> temp = new ArrayList<>(); 
                // ！！！！！直接res.add(words), words加入的只是指针，所以随着 words的改变，res中的值也改变了，
                // 所以这里要重新赋值
                for (int i = 0; i < words.size(); i++) {
                    temp.add(words.get(i));
                }
                min = words.size();
                res.add(temp);
                return;
            }
            return;
        }
        words.add(beginWord);
        // 如果每个都符合条件，需要递归， O（n^n）
        for (int i = 0; i < wordList.size(); i++) {
            if (!words.contains(wordList.get(i)) && isValid(beginWord, wordList.get(i))) {
                //words.add(wordList.get(i));
                findPath(wordList.get(i), endWord, wordList, words);
                words.remove(words.size() - 1);
            }
        }
    }
    
    // BFS + DFS（遍历BFS的结果）
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        if (beginWord.equals(endWord))
            return new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        List<List<String>> aux = new ArrayList<>();
        Set<String> set = new HashSet<>();
        set.add(beginWord);
        int count = 0;
        int minLevel = Integer.MAX_VALUE;
        boolean isFound = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            if (count > minLevel) break; // 后面再有满足条件的就没必要判断了，因为要找最短的path
            List<String> level = new ArrayList<>();
            while (size > 0) {
                String word = queue.poll();
                level.add(word); // 这样是只添加上一层，但如果接下的一层是endword,
                				// 所以要注意在找到 endWord的时候，要同时加上其对应的上一层
                for (String w : wordList) {
                    if (!set.contains(w) && isValid(word, w)) {
                        queue.offer(w);
                        if (w.equals(endWord)) {
                            isFound = true;
                            minLevel = count;
                            break;
                        }
                        set.add(w);
                    }
                }
                
                size--;
            }
            if (isFound) {
            	aux.add(level); // ！！！！由于上面的逻辑，在找到 endWord的时候，要同时加上其对应的上一层
            	aux.add(Arrays.asList(endWord));
            } else {
                aux.add(level);
            }
        }
        dfs(aux, 0, new ArrayList<>());
        return result;
    }
    
    List<List<String>> result = new ArrayList<>();
    public void dfs(List<List<String>> levels, int s, List<String> level) {
        if (s == levels.size()) {  
        	result.add(new ArrayList<>(level));
            return ;
        }
        // 但是这里直接dfs是不对的， 比如 [hit, hot, [dot, lot], [dog, log], cog]
        //					只能 dot, dog， 不能 dot, log
        // 所以这里要判断从下一个list中取的值是否是Valid的
        
        // 注意这里的逻辑如果要用于取所有的路径是不对的，因为 每条路径的长度是不一样的，所以需要加额外的判断
        // 而这里找最短的话（只记录最短的），如果有多条，其长度肯定是一样的
        List<String> nodes = levels.get(s);
    	for (int j = 0; j < nodes.size(); j++) {
    		if (level.size() == 0 || // 判断是初始元素，或者下一个元素是否是valid的，不是什么都能取
    				isValid(level.get(level.size() - 1), nodes.get(j))) {
    			level.add(nodes.get(j));
        		dfs(levels, s + 1, level);
        		level.remove(level.size() - 1);
    		}
    	}
        
    	// 针对 每个组（list）做递归 !!!!, 相当于图中， 每个点下的多个adjacent， 所以下面不对
//        for (int i = s; i < levels.size(); i++) {
//        	for (int j = 0; j < levels.get(i).size(); j++) {
//        		level.add(levels.get(i).get(j));
//        		dfs(levels, i + 1, level);
//        	}
//        }
        
    }
    
    public boolean isValid(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                count++;
            if (count > 1)
                return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
    	List<String> wordList = Arrays.asList("hot","dot","dog","lot","loe","coe", "cog");
    	String beginWord = "hit";
    	String endWord = "cog";
    	new WordLadderII().findLadders2(beginWord, endWord, wordList);
    }
}
