package triePrefixTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



/*
 * NOTE:
 * 1. REVERSE(A) is PREFIX of B, then A + B might be a Palindrom, and vice versa.
 * 2. EMPTY input => root.isWord = TRUE => it can be the PREFIX of ANY ITEMs.
 * 3. A + Empty MIGHT BE a Palindrom
 * 
 * **/
public class PalindromPairsTest {
	public class TrieNode {
        Map<Character, TrieNode> children;
        char val;
        boolean isWord;
        int index;
        public TrieNode(char val) {
            this.children = new HashMap<>();
            this.val = val;
            this.index = -1;
            this.isWord = false;
        }
    }
    
    public void insert(String word, TrieNode root, int index) {
        for (int i = 0; i < word.length(); i++) {
            if (root.children != null) {
                if (root.children.containsKey(word.charAt(i))) {
                    root = root.children.get(word.charAt(i));
                } else {
                    TrieNode node = new TrieNode(word.charAt(i));
                    root.children.put(word.charAt(i), node);
                    root = node;
                }
            }
        }
        root.index = index;
        root.isWord = true;
    }
    public Set<Integer> search(String word, TrieNode root, int index, List<List<Integer>> res) {
    	Set<Integer> can = new HashSet<>();
        if (word.length() == 0) {
        	can.add(Integer.MAX_VALUE);// if word is EMPTY, then it can be the PREFIX OF ANY STRING
        }
    	for (int i = 0; i < word.length(); i++) {
    		if (root.isWord) can.add(root.index);// For empty string, BUT DO NOT RETURN, OTHERWISE, CHILDREN NODES WILL MISSED
            if (root.children != null) {
                if (root.children.containsKey(word.charAt(i))) {
                    if  (root.children.get(word.charAt(i)).index > -1
                    		&& root.children.get(word.charAt(i)).index != index) {
                        can.add(root.children.get(word.charAt(i)).index);
                    }
                    root = root.children.get(word.charAt(i));
                }
            }
        }
        return can;
    }
    public List<List<Integer>> palindromePairs(String[] words) {
        if (words.length == 0) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        // //TLE
        // for (int i = 0; i < words.length; i++) {
        //     for (int j = 0; j < words.length; j++) {
        //         if (i != j) {
        //             String temp = words[i] + words[j];
        //             //StringBuffer sb = new StringBuffer(temp);
        //             if (isPalindrome(temp)) {
        //                 List<Integer> s = new ArrayList<>();
        //                 s.add(i);
        //                 s.add(j);
        //                 res.add(s);
        //             }
        //         }
        //     }
        // }
//         Map<String, Integer> map = new HashMap<>();
//         for (int i = 0; i < words.length; i++) {
//             map.put(words[i], i);
//         }
//         for (int i = 0; i < words.length; i++) {
//             for (int j = 0; j <= words[i].length(); j++) {
//                 String sub1 = words[i].substring(0, j);
//                 String sub2 = words[i].substring(j);
//                 // empty string and len(string) = 1 are palindrome
//                 if (isPalindrome(sub1)) {
//                     String reverseSub2 = new StringBuffer(sub2).reverse().toString();
//                     if (map.containsKey(reverseSub2) && map.get(reverseSub2) != i) {
//                         List<Integer> s = new ArrayList<>();                        
//                         s.add(map.get(reverseSub2));
//                         s.add(i);
//                         res.add(s);
//                     }
//                 }
                
//                 if (isPalindrome(sub2)) {
//                     String reverseSub1 = new StringBuffer(sub1).reverse().toString();
//                     if (map.containsKey(reverseSub1) && map.get(reverseSub1) != i && sub2.length() != 0) {
//                         List<Integer> s = new ArrayList<>();
//                         s.add(i);
//                         s.add(map.get(reverseSub1));                       
//                         res.add(s);
//                     }
//                 }
//             }
//         }
        
        // TRIE
        // build trie
        TrieNode root = new TrieNode(' ');
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            insert(words[i], root, i);
            map.put(i, words[i]);
        }
        for (int i = 0; i < words.length; i++) {
        	// Reverse the word,and to see whether it is the PREFIX of other words.
        	String revWord = new StringBuffer(words[i]).reverse().toString();
        	
        	Set<Integer> candidateInd = search(revWord, root, i, res);
        	for (Integer can : candidateInd) {
        		if (can == Integer.MAX_VALUE) {
            		for (int j = 0; j < words.length; j++) {
            			if (j == i) continue;
            			if (isPalindrome(words[j] + words[i])) {
            				res.add(Arrays.asList(j, i));
            			}
            		}
            	} else if (can >= 0) {
                	if (isPalindrome(map.get(can) + words[i])) {
                		res.add(Arrays.asList(can, i));
                	}
                }
        	}
        	
        }
        return res;
    }
    
    public boolean isPalindrome(String word) {
        if (word.length() < 2) {
            return true;
        }
        int i = 0, j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) {
                return false;
            }
        }
        return true;
    }
    
    public List<List<Integer>> test(String[] words) {
    	Trie root = new Trie('-', -1, 0);
        List<List<Integer>> palindromes = new LinkedList<List<Integer>>();

        for (int i = 0; i < words.length; i++) {
            root.addWord(words[i], i);
        }
        // System.out.println(root);
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StringBuffer buff = new StringBuffer(words[i]);
            buff.reverse();
            String wordReverse = buff.toString();
            
            Set<Integer> potentialPalindromes = root.getWords(wordReverse, i);
            // System.out.println("potential: " + potentialPalindromes);
            for (int otherPart: potentialPalindromes) {
                if (otherPart == i) continue;
                
                String otherWord = words[otherPart];
                if (isPalindrome(otherWord + word)) {
                    palindromes.add(Arrays.asList(new Integer[]{otherPart, i}));
                }
            }        
        }
        return palindromes;
    }
    public static void main(String[] args) {
    	String[] words = new String[] {"a","b","c","ab","ac","aa"};
    	//String[] words = new String[] {"a",""};
    	new PalindromPairsTest().palindromePairs(words);
    	//new PalindromPairsTest().test(words);
    }
    
    class Trie {
        Boolean isEndOfWord = false;
        
        char letter;
        int wordIndex;
        int level;

        Map<Character, Trie> children = new HashMap<Character, Trie>();
        
        // While you could recursively traverse and collect them all, storing the list in each index saves time at the cost of memory.
        List<Integer> allWordsHenceForth = new LinkedList<Integer>();

        public Trie(char letter, int wordIndex, int level) {
            this.letter = letter;
            this.wordIndex = wordIndex;
            this.level = level;
        }
        
        public Set<Integer> getWords(String word, int wordIndex) {
            Trie cur = this;
            Set<Integer> cands = new HashSet<Integer>();
            for (char c: word.toCharArray()) {
                if (cur == null) break;
                if (cur.isEndOfWord) {
                    cands.add(cur.wordIndex);
                }

                cur = cur.children.get(c);
            }
            if (cur != null) {
                cands.addAll(cur.allWordsHenceForth);
            }
            
            cands.remove(wordIndex);

            return cands;
        }
        
        public void addWord(String word, int wordIndex) {
            
            Trie cur = this;
            int levelx = 0;
            allWordsHenceForth.add(wordIndex);
            for (char c: word.toCharArray()) {
                final int lx = ++levelx;
                cur = cur.children.computeIfAbsent(c, k -> new Trie(c, wordIndex, lx));
                cur.allWordsHenceForth.add(wordIndex);
            }
            cur.isEndOfWord = true;
            cur.wordIndex = wordIndex;
        }
        
    }
}

