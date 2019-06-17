package triePrefixTree;

import java.util.HashMap;
import java.util.Map;

/**
 * Implement a MapSum class with insert, and sum methods.
 * For the method insert, you'll be given a pair of (string, integer). 
 * The string represents the key and the integer represents the value. 
 * If the key already existed, then the original key-value pair will be overridden to the new one.
 * For the method sum, you'll be given a string representing the prefix, 
 * and you need to return the sum of all the pairs' value whose key starts with the prefix.
 * */
public class MapSumPairs {
	
	TrieNode1 root;
	    /** Initialize your data structure here. */
	    public MapSumPairs() {
	        root = new TrieNode1();
	    }
	    
	    public void insert(String key, int val) {
	    	TrieNode1 curr = root;
	        for (int i = 0; i < key.length(); i++) {
	            char c = key.charAt(i);
	            if (!curr.children.containsKey(c)) {
	            	TrieNode1 node = new TrieNode1();
	                curr.children.put(c, node);
	            }
	            curr = curr.children.get(c);
	        }
	        curr.isWord = true; 
	        curr.val = val;
	                
	    }
	    
	    public int sum(String prefix) {
	    	TrieNode1 curr = root;
	        for (int i = 0; i < prefix.length(); i++) {
	            char c = prefix.charAt(i);
	            if (!curr.children.containsKey(c)) {
	                return 0;
	            }
	            curr = curr.children.get(c);
	        }
	        return helper(curr);
	        
	    }
	    // bottom-up DFS
	    public int helper(TrieNode1 node) {
	        if (node == null) return 0;
	        int sum = 0;
	        for (char c : node.children.keySet()) {
	            sum += helper(node.children.get(c));
	        }
	        return sum + node.val;
	    }
	
}
class TrieNode1 {
        Map<Character, TrieNode1> children;
        int val;
        boolean isWord;
        
        public TrieNode1() {
            this.children = new HashMap<>();
            this.val = 0;// the value of node is DIGIT
            this.isWord = false;
        }
    }

	/**
	 * Your MapSum object will be instantiated and called as such:
	 * MapSum obj = new MapSum();
	 * obj.insert(key,val);
	 * int param_2 = obj.sum(prefix);
	 */

