package triePrefixTree;

import java.util.HashMap;
import java.util.Map;

// Array(26), list, or set can be used to creat TrieNode
// reference to: https://leetcode.com/explore/learn/card/trie/150/introduction-to-trie/1046/
public class TrieNode {

	Map<Character, TrieNode> children;
	char val; // can be other data type, e.g. MapSumPairs
	boolean isWord;
	
	public TrieNode(char val) {
		this.val = val;
		this.children = new HashMap<>();
		this.isWord = false;
	}
}
