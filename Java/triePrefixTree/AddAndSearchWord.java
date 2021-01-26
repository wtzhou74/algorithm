package triePrefixTree;

/**
 * WORD DICTIONARY
 * 
 * search(word) can search a literal word or a regular expression string containing only 
 * letters a-z or .. A . means it can represent any one letter.
 * 
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 * 
 * */
public class AddAndSearchWord {

	TrieNode3 root;
    /** Initialize your data structure here. */
    public AddAndSearchWord() {
        root = new TrieNode3(' ');
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        if (word.isEmpty()) return;
        TrieNode3 curr = root;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
            	TrieNode3 node = new TrieNode3(c);
                curr.children[c - 'a'] = node;
            }
            curr = curr.children[c - 'a'];
        }
        curr.isWord = true;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        if (word.isEmpty()) return false;
        //TrieNode3 curr = root;
        return match(word.toCharArray(), root, 0);
    }
    
    // traverse EACH LEVEL
    public boolean match(char[] chars, TrieNode3 node, int level) {
        if (node == null) return false;
        if (level == chars.length) return node.isWord;
        //TrieNode curr = node;
        char c = chars[level];// the Index of String is the value of LEVEL
        if (c == '.') {
            for (TrieNode3 child : node.children) {
            	// IF FALSE; DONOT RETURN, go check next one; return false only if all children are false
                if (match(chars, child, level + 1)) return true; 
            }
            return false;
        } else {
        	// The index of NODE is c - 'a'
            return match(chars, node.children[c - 'a'], level + 1);
        }
    }
}
