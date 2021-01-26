package triePrefixTree;

import java.util.HashMap;
import java.util.Map;

public class ImplementTrie {

	public class TrieNode {
		public boolean isWord;
		public Map<Character, TrieNode> children = new HashMap<>();
		
		public TrieNode() {
			this.isWord = false;
		}
		
	}
	
	private TrieNode root;
	
	// initialize trie
	
	public void insert(String[] words) {
		TrieNode root = new TrieNode();
		
		for (String word : words) {
			TrieNode cur = root;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (cur.children.get(c) == null) {
					// insert a new node if the path does not exist
					cur.children.put(c, new TrieNode());
				}
				// check next node
				cur = cur.children.get(c);
			}
			cur.isWord = true;
		}
		
	}
	
	// return if the word is in the Trie
	public boolean search(String word, TrieNode root) {
		TrieNode cur = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (cur.children.get(c) == null) {
				return false;
			} 
			cur = cur.children.get(c);
			
		}
		return cur.isWord;
	}
	
	//public static TrieNode root = new TrieNode();
	public static void main(String[] args) {
		String a = "bed";
		String b = "bek";
		new ImplementTrie().insert(new String[] {"bed", "bek"});
	}
}
