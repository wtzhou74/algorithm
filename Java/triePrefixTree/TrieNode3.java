package triePrefixTree;

public class TrieNode3 {

	char val;
	TrieNode3[] children;
	boolean isWord;
	
	public TrieNode3(char val) {
		this.val = val;
		this.children = new TrieNode3[26];
		this.isWord = false;
	}
}
