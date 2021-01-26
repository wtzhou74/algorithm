package triePrefixTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

//Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.
//
//Return list of lists of the suggested products after each character of searchWord is typed. 
//
// 
//
//Example 1:
//
//Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
//Output: [
//["mobile","moneypot","monitor"],
//["mobile","moneypot","monitor"],
//["mouse","mousepad"],
//["mouse","mousepad"],
//["mouse","mousepad"]
//]
//Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
//After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
//After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
//Example 2:
//
//Input: products = ["havana"], searchWord = "havana"
//Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
//Example 3:
//
//Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
//Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
//Example 4:
//
//Input: products = ["havana"], searchWord = "tatiana"
//Output: [[],[],[],[],[],[],[]]
// 
//
//Constraints:
//
//1 <= products.length <= 1000
//There are no repeated elements in products.
//1 <= Σ products[i].length <= 2 * 10^4
//All characters of products[i] are lower-case English letters.
//1 <= searchWord.length <= 1000
//All characters of searchWord are lower-case English letters.

public class SearchSuggestionsSystem {
	
	// TreeSet Solution
	public List<List<String>> treeSetSol(String[] products, String searchWord) {
		List<List<String>> res = new ArrayList<>();
		//Arrays.sort(products);
		TreeSet<String> set = new TreeSet<>();
		for (String product : products) {
			set.add(product);
		}
		for (int i = 1; i <= searchWord.length(); i++) {
			List<String> list = new ArrayList<>();
			String prefix = searchWord.substring(0, i);
			TreeSet<String> subset = (TreeSet<String>)set.subSet(prefix, prefix + "~"); // Fetch ALL products whose prefix is substring(0, i) // O(NlgN)
			if (subset.size() > 0) {
				int count = 0;
				for (String s : subset) {
					if (++count <= 3) {
						list.add(s);
					}
				}
			}
			res.add(list);
		}
		return res;
	} 

	// Trie Solution
	public class TrieNode {
        String val;
        Map<Character, TrieNode> children;
        boolean isWord;
        
        public TrieNode() {
            this.val = "";
            this.children = new HashMap<>();
            this.isWord = false;
        }
    }
    
    public TrieNode buildTrie(String[] products) {
        TrieNode root = new TrieNode();
        //TrieNode curr = root;
        for (String product : products) {
        	TrieNode curr = root; // reset curr for next word
            for (int i = 0; i < product.length(); i++) {
                if (!curr.children.containsKey(product.charAt(i))) {
                    curr.children.put(product.charAt(i), new TrieNode());
                }
                curr = curr.children.get(product.charAt(i));
            }
            curr.isWord = true;
            curr.val = product;
        }
        return root;
    }
    
//    // find all matched strings with given prefix
//    public TreeSet<String> search(TrieNode root, String prefix) {
//        TreeSet<String> set = new TreeSet<>();
//        if (root == null) return set;
//        TrieNode curr = root;
//        int i = 0;
//        for (char c : prefix.toCharArray()) {
//            if (curr == null) return set;
//            if (curr.children.containsKey(c)) {
//            	//NOTE: i == prefix.length() - 1 MUST BE ADDED
//            	// Otherwise: prefix = "baggage", word = "bag", the "bag" will be added to the final result which CANNOT BE matched word, unless prefix = "bag" or "ba" or "b"
//                if (curr.children.get(c).isWord && i == prefix.length() - 1) { 
//                    set.add(curr.children.get(c).val);
//                }                    
//                curr = curr.children.get(c);
//            }
//            else 
//                return set;
//            i++;
//        }
//        // DFS to get all matched strings
//        if (curr != null) {            
//            Stack<TrieNode> stack = new Stack<>();
//            stack.push(curr);
//            while (!stack.isEmpty()) {
//                TrieNode node = stack.pop();
//                if (node.isWord)
//                    set.add(node.val);
//                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
//                    stack.push(entry.getValue());
//                }
//            }
//        }
//        return set;
//    }
    

    // Applying List instead, we can use Collections.sort(list) to sort the matched products  ===> O(NlgN)
    public List<String> search(TrieNode root, String prefix) {
        List<String> set = new ArrayList<>();
        if (root == null) return set;
        TrieNode curr = root;
        int i = 0;
        for (char c : prefix.toCharArray()) {
            if (curr == null) return set;
            if (curr.children.containsKey(c)) {
            	//NOTE: i == prefix.length() - 1 MUST BE ADDED
            	// Otherwise: prefix = "baggage", word = "bag", the "bag" will be added to the final result which CANNOT BE matched word, unless prefix = "bag" or "ba" or "b"
//                if (curr.children.get(c).isWord && i == prefix.length() - 1) { // DO NOT DO IT HERE, IT WILL BE ADDED WHEN DOING DFS, the case here is the branch point itself is one of matched product
//                    set.add(curr.children.get(c).val);
//                }                    
                curr = curr.children.get(c);
            }
            else 
                return set;
            i++;
        }
        // DFS to get all matched strings
        if (curr != null) {// curr is the branch point, all its children will be the final solution, but this curr itself can be one of matched product           
            Stack<TrieNode> stack = new Stack<>();
            stack.push(curr);
            while (!stack.isEmpty()) {
                TrieNode node = stack.pop();
                if (node.isWord)
                    set.add(node.val);
                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                    stack.push(entry.getValue());
                }
            }
        }
        return set;
    }
    
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        TrieNode root = buildTrie(products);
        for (int i = 1; i <= searchWord.length(); i++) {
            List<String> list = new ArrayList<>(); // Collections.sort(list);
            List<String> matchedStrs = search(root, searchWord.substring(0, i));
            Collections.sort(matchedStrs);
            if (matchedStrs.size() > 0) {
                int count = 0;
                for (String s : matchedStrs) {
                    list.add(s);
                    if (++count == 3) break;
                }
            }
            res.add(list);
        }
        
        return res;
        
    }
    
    public static void main(String[] args) {
    	String[] products = new String[] {"mobile","mouse","moneypot","monitor","mousepad"};
    	new SearchSuggestionsSystem().suggestedProducts(products, "mouse");
    }
}
