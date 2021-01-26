package triePrefixTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordSearchII {

	public class TrieNode {
        Map<Character, TrieNode> children;
        char val;
        String word;
        public TrieNode(char val) {
            this.children = new HashMap<>();
            this.word = "";
            this.val = val;
        }
    }
    public void insert(String word, TrieNode node) {
        TrieNode curr = node;
        for (int i = 0; i < word.length(); i++) {
            if (curr.children != null) {
               if (curr.children.containsKey(word.charAt(i))) {
                   curr = curr.children.get(word.charAt(i));
                   continue;
               } else {
                   TrieNode tmp = new TrieNode(word.charAt(i));
                   curr.children.put(word.charAt(i), tmp);
                   curr = tmp;
               }
                
            }
        }
        curr.word = word;
    }
    
    
    // public boolean search(String prefix, TrieNode root) {
    //     TrieNode curr = root;
    //     for (int i = 0; i < prefix.length(); i++) {
    //         if (curr.children != null) {
    //             if (curr.children.containsKey(prefix.charAt(i))) {
    //                 curr = curr.children.get(prefix.charAt(i));
    //             } else {
    //                 return false;
    //             }
    //         }
    //     }
    //     return curr.isWord;
    // }
    
    public TrieNode getChild(char c, TrieNode node) {
        if (node.children != null) {
            if (node.children.containsKey(c)) {
                return node.children.get(c);
            } else {
                return null;
            }
        }
        return null;
      
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        // build Trie
        TrieNode root = new TrieNode(' ');
        for (String word : words) {
            insert(word, root);
        }
        // traverse 2d dic
        List<String> res = new ArrayList<>();
        // starting from any cell
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                helper(i, j, board, root, res);
            }
        }
        
        return res;
    }
    
    public void helper(int row, int col, char[][] board, TrieNode node, List<String> res) {
    	// BASE CASE - FOR EACH CANDIDATE, THEY WOULD DO THE SAME FOLLOWING STEPS
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] == '#') {
            return ;
        }
        char c = board[row][col];
        node = getChild(c, node); // current node, each candidate CELL is a TrieNode!!!!!!!!!, rather than SEARCH COMPLETE WORD
        if (node == null) return;
        if (node.word.length() > 0) {
            res.add(node.word);
            node.word = ""; // REMOVE DUPLICATE WORD, when it was found again, but wont be added
        }
        board[row][col] = '#'; // SET CURRENT CELL TO BE VISITED
        ////////////////////// STARTING CHECKING EXTRA CANDIDATES RECURSIVELY
        // up
        helper(row - 1, col, board, node, res);
        // down
        helper(row + 1, col, board, node, res);
        // left
        helper(row, col - 1, board, node, res);
        // right
        helper(row, col + 1, board, node, res);
        //backtrack !!!!!!!
        board[row][col] = c;
    }
}
