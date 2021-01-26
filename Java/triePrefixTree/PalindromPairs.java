package triePrefixTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalindromPairs {
	
//	public static List<List<Integer>> palindromePairs(String[] words) {
//		List<List<Integer>> result = new ArrayList<>();
//        if (words == null | words.length < 2) return result;
//        Map<String, Integer> map = new HashMap<>();
//        for (int i = 0; i < words.length; i++) map.put(words[i], i);
//        for (int i = 0; i < words.length; i++) {
//            // j can be equals length since empty string is also palindrome
//            for (int j = 0; j <= words[i].length(); j++) {
//                String str1 = words[i].substring(0, j);
//                String str2 = words[i].substring(j);// if j==length; the substring is ""; and it is palindrome
//                
//                // empty string "" and single Character or same chars are both palindrome
//                if (isPalindrome(str1)) {
//                    String revOfStr2 = new StringBuilder(str2).reverse().toString();
//                    // existed but not the same as current one
//                    if (map.containsKey(revOfStr2) && map.get(revOfStr2) != i) {
//                        List<Integer> temp = new ArrayList<>();
//                        // NOTICE the sequence
//                        temp.add(map.get(revOfStr2));
//                        temp.add(i);
//                        result.add(temp);
//                    }
//                }
//                if (isPalindrome(str2)) {
//                    String revOfStr1 = new StringBuilder(str1).reverse().toString();
//                    // existed but not the same as current one
//                    // reduct replication with str2.length != 0 since if == 0, then will get same pair, it can be done with SET
//                    if (map.containsKey(revOfStr1) && map.get(revOfStr1) != i && str2.length() != 0) {
//                        List<Integer> temp = new ArrayList<>();
//                        // NOTICE the sequence
//					  	  temp.add(i);
//                        temp.add(map.get(revOfStr1));
//                        result.add(temp);
//                    }
//                }
//            }
//        }
//        return result;
//	}

	// TIME-CONSUMING
//	public static List<List<Integer>> palindromePairs(String[] words) {
//        List<List<Integer>> result = new ArrayList<>();
//        for (int i = 0; i < words.length; i++) {
//            for (int j = i + 1; j < words.length; j++) {
//                if (isPalindrome(words[i] + words[j])) {
//                    List<Integer> temp = new ArrayList<>();
//                    temp.add(i);
//                    temp.add(j);
//                    result.add(temp);
//                }
//                if (isPalindrome(words[j] + words[i])) {
//                    List<Integer> temp = new ArrayList<>();
//                    temp.add(i);
//                    temp.add(j);
//                    result.add(temp);
//                }
//                    
//            }
//        }
//        return result;
//    }
    
//    public static boolean isPalindrome(String str) {
//        if (str.isEmpty()) {
//            return true;
//        }
//        char[] chars = str.toCharArray();
//        int i = 0, j = str.length() - 1;
//        while (i <= j) {
//            if (chars[i] != chars[j]) return false;
//            i++;
//            j--;
//        }
//        return true;
//    }
    
//    public static void main(String[] args) {
//    	String[] t = {"abcd","dcba","lls","s","sssll"};
//    	System.out.println(t[0].substring(5));
//    	//StringBuilder str = new StringBuilder();str.re
//    	palindromePairs(t);
//    }
    
    // Trie Solution
    private static class TrieNode {
        TrieNode[] children;
        int index;
        List<Integer> list;
        	
        TrieNode() {
        	children = new TrieNode[26];
        	index = -1;
        	list = new ArrayList<>();
        }
    }
        
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();

        TrieNode root = new TrieNode();
    		
        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }
    		
        for (int i = 0; i < words.length; i++) {
            search(words, i, root, res);
        }
        
        return res;
    }
        
    private void addWord(TrieNode root, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            int j = word.charAt(i) - 'a';
    				
            if (root.children[j] == null) {
                root.children[j] = new TrieNode();
            }
    				
            if (isPalindrome(word, 0, i)) {
                root.list.add(index);
            }
    				
            root = root.children[j];
        }
        	
        root.list.add(index);
        root.index = index;
    }
        
    private void search(String[] words, int i, TrieNode root, List<List<Integer>> res) {
        for (int j = 0; j < words[i].length(); j++) {	
        	if (root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
        	    res.add(Arrays.asList(i, root.index));
        	}
        		
        	root = root.children[words[i].charAt(j) - 'a'];
          	if (root == null) return;
        }
        	
        for (int j : root.list) {
        	if (i == j) continue;
        	res.add(Arrays.asList(i, j));
        }
    }
        
    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
        	if (word.charAt(i++) != word.charAt(j--)) return false;
        }
        	
        return true;
    }
}
