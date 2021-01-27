package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
//A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
//
//Example:
//Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
//
//Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
//
//Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
// "dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
//"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
//Note:
//The number of elements of the given array will not exceed 10,000
//The length sum of elements in the given array will not exceed 600,000.
//All the input string will only include lower case letters.
//The returned elements order does not matter.

// 跟 wordBreak是一类问题
public class ConcatenatedWords {

	public List<String> findAllConcatenatedWordsInADict(String[] words) {
        if (words.length == 0) return new ArrayList<>();
        List<String> res = new ArrayList<>();
        Set<String> dic = new HashSet<>(); // 放到 set, 因为 list.contains()是O（n）, 而set.contains()是O（1）
        Collections.addAll(dic, words);
        for (String word : words) {
            if (word.length() == 0) continue;
            // 这里选用 Boolean对象，因为 boolean[]默认是false， 而false可以是结果值的
            //boolean[] memo = new boolean[word.length() + 1];
            Boolean[] memo = new Boolean[word.length() + 1];
            if (wordBreak(dic, word, 0, memo))
                res.add(word);
        }
        return res;
    }
    
    public boolean wordBreak(Set<String> words, String word,
                             int idx, Boolean[] memo) {
        if (idx == word.length()) {
            return true;
        }
        if (memo[idx] != null) // 不能用 boolean[]
            return memo[idx];
        for (int i = idx; i < word.length(); i++) {
            String str = word.substring(idx, i + 1);
            if (words.contains(str) &&
                !word.equals(str)) { // ！！！这里只需要判断不是自己的word就可以了，
            					//这样的话就能保证至少还得1个（总的至少2个）才能组合成target word了
                if (wordBreak(words, word, i + 1, memo)) {
                    memo[idx] = true;
                    return true;
                }
            }
        }
        memo[idx] = false;
        return false;
    }
    
    class TrieNode {
        String val; // 这里的value其实没用，每次遍历的时候只需看是否找到即可
        Map<Character, TrieNode> children;
        boolean isWord;
        
        public TrieNode() {
            this.children = new HashMap<>();
            this.val = "";
            this.isWord = false;
        }
    }
    TrieNode root = null;
    public void buildTrie(TrieNode root, String[] words) {
        for (String word : words) {
            TrieNode curr = root;
            char[] cs = word.toCharArray();
            for (int i = 0; i < cs.length; i++) {
                if (curr.children.containsKey(cs[i])) {
                    curr = curr.children.get(cs[i]);
                } else {
                	
                    //TrieNode node = new TrieNode();
                	
                	// !!!!!!!下面这个错误，会miss掉一些word, 比如先 “cats”, 再 “cat”, 
                	// 循环会在if那里跳出，所以 cat不会被记录到word中， 因为最后一个 t 也是已访问过了，所以
                	// 一定要在for外，对任何结束循环的标记为 word
                    // if (i == word.length() - 1) {
                    //     node.isWord = true;
                    //     node.val = word;
                    // }
                	
                	// 对任何出 for的，标记为 word, 不能像上面那样，除非再 if 也加上同样的判断
                    curr.children.put(cs[i], new TrieNode());
                    curr = curr.children.get(cs[i]);
                }
                
            }
            curr.isWord = true;
            curr.val = word;
        }
    }
    
    // 这里遍历的过程是多次从开始的遍历 ==> 递归
    public boolean searchTrie(char[] cs, int start, int count) {        
        //char[] cs = s.toCharArray();
        if (start == cs.length && count >= 2)
            return true;
        if (start == cs.length)
            return false;
        TrieNode curr = root; // 每次递归进来即重复一次从头开始的搜索，所以这里要重置 root
        for (int i = start; i < cs.length; i++) {
            if (curr.children.containsKey(cs[i])) {
                TrieNode child = curr.children.get(cs[i]);
                if (child.isWord) {
                	// 找到一个word, 递归从下一个char开始看是不是满足 （一条path下来可以包含多个word）
                    if(searchTrie(cs, i + 1, count + 1))
                    	return true;
                }
                curr = child;
            } else 
                return false;
        }
        return false;
        
    }
    public List<String> findAllConcatenatedWordsInADict1(String[] words) {
        if (words.length == 0)
            return new ArrayList<>();
        root = new TrieNode();
        buildTrie(root, words);
        List<String> res = new ArrayList<>();
        for (String word : words) {
            if (searchTrie(word.toCharArray(), 0, 0))
                res.add(word);
        }
        return res;
        
    }
    
    
    // BFS 
    public List<String> findAllConcatenatedWordsInADictBFS(String[] words) {
        if (words == null || words.length == 0) {
            return new LinkedList<>();
        }
        Set<String> set = new HashSet<>();
        for (String s : words) {
            set.add(s);
        }
        List<String> result = new LinkedList<>();
        
        for (String word : words) {
            if (word.length() == 0)
                continue;
            int[] visited = new int[word.length()];
            visited[0] = 1; // 这里同样需要一个visited, 比如 [aaaaa]这种的走第一/二...个a,都会又符合的后面的a要判断，所以visited就可以省去那些
            if (isConcatenate(set, word, visited)) {
                result.add(word);
            }
                
        }
        return result;
    }
    
    public boolean isConcatenate(Set<String> set, String word, int[] visited) {
        Queue<Integer> queue = new LinkedList<>();// 放的是索引，不是可拼接的word
        queue.offer(0);
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int i = queue.poll();
                int j = i;
                while (j < word.length()) {
                    if (!word.substring(i, j+1).equals(word) && 
                        set.contains(word.substring(i, j+1))) {
                    	// !!!!!!只有合并完后能到最后一个字符才可以，否则还是false
                    	// [a, b, ab, abc] ==> ab是，abc不是，因为没有一个word可以到最后
                    	// 所以不能直接简单在最后判断count>=2
                        if (j == word.length() - 1 && count >= 1)
                            return true; // 但是能被>=2完整拼接成的一定会到这里
                        if (j < word.length() - 1 && visited[j + 1] == 0) {
                            visited[j + 1] = 1;
                            queue.offer(j + 1);
                        }     
                    }
                    j++;
                }
                size--;
            }
            if (queue.size() > 0)
                count++;
        }
        // return count>=2//错误！！！！， 还要看这些可拼接的最后是不是能拼成一个完整的word
        return false;
    }
    
    public static void main(String[] args) {
    	String[] words = new String[] {"cat","cats","catsdogcats","dog"};
    	new ConcatenatedWords().findAllConcatenatedWordsInADict1(words);
    }
}
