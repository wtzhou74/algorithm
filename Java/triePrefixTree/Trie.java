package triePrefixTree;

// Trie is a special form of N-ary tree
public class Trie {
	TrieNode root;
    
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode(' ');// initialize root node with empty character
        
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode curr = root;// start from root
        for (int i = 0; i < word.length(); i++) {
            if (curr.children != null) {
                if (curr.children.containsKey(word.charAt(i))) {
                    curr = curr.children.get(word.charAt(i));
                    continue;
                } else {
                    TrieNode node = new TrieNode(word.charAt(i));
                 // add a new node to the current children map
                    curr.children.put(word.charAt(i), node);
                    // now the curr node is the new added node
                    curr = curr.children.get(word.charAt(i));
                }
            }
        }
        //the last node (the last character of word)
        // be used to identify whether this path indicating a word
        curr.isWord = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            //if (curr.children == null) return false;
            if (curr.children.containsKey(c)) {
                curr = curr.children.get(c);
            } else return false;
        }
        return curr.isWord;// if the current node's isWord flag is true, then it is a word
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.children.containsKey(c)) {
                return false;
            } else {
                curr = curr.children.get(c);
            }
        }
        // ASSUMING ALL BRANCH IS A WORD.
        return true;
    }
}
