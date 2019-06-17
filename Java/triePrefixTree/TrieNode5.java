package triePrefixTree;

import java.util.HashMap;
import java.util.Map;

public class TrieNode5 {

	Map<Character, TrieNode5> children;
    char val;
    String word;
    public TrieNode5(char val) {
        this.children = new HashMap<>();
        this.val = val;
    }
}
