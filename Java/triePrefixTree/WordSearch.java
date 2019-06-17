package triePrefixTree;

import java.util.ArrayList;
import java.util.List;

/**
 * SAME AS NUM OF ISLANDS
 * */
public class WordSearch {

	static TrieNode5 root;
    static List<String> result = new ArrayList<>();
    public static List<String> findWords(char[][] board, String[] words) {
        buildTrie(words);
        // word can start from any one of cell
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                searchChar(board, root, i, j);
            }
        }
        
        return result;
    }
    
    public static void searchChar(char[][] board, TrieNode5 node, int row, int column) {
        if (node == null) return ;
        //if (row < 0 || column < 0 || row >= board.length || column >= board[0].length) return ;
        char c = board[row][column];
        // for (char c : chars)
            if (c == '#' || !node.children.containsKey(c)) {
                return ;
            } 
            node = node.children.get(c);
        //
        if (node.word != null) {
            result.add(node.word);
            node.word = null;//remove duplicate word found
        }
        
//         char c = board[i][j];
//     if (c == '#' || p.next[c - 'a'] == null) return;
//     p = p.next[c - 'a'];
//     if (p.word != null) {   // found one
//         res.add(p.word);
//         p.word = null;     // de-duplicate
//     }
        
        // !!!!!! HAVE TO set the node VISITED, also see number of islands, otherwise it will traverse the 
        // same node more than TWICE for one word, for the following case, aaa will be found
        board[row][column] = '#'; // combine board[row][column] = c; can process case: [["a","a"]]; ["aaa"]
        
        if (column > 0) searchChar(board, node, row, column - 1);
        
        if (column < board[0].length - 1) searchChar(board, node, row, column + 1);
        
        if (row > 0) searchChar(board, node, row - 1, column);
         
        if (row < board.length - 1) searchChar(board, node, row + 1, column);
        
        board[row][column] = c;// !!!! recover its original character by replace # with original character
    
    }
    
	public static void buildTrie(String[] words) {
        root = new TrieNode5(' ');
        for (String word : words) {
            insert(word);
        }
        //System.out.println("");
    }
    
    public static void insert(String word) {
    	TrieNode5 curr = root;
        for (char c : word.toCharArray()) {
            if (!curr.children.containsKey(c)) {
            	TrieNode5 node = new TrieNode5(c);
                curr.children.put(c, node);
            }
            curr = curr.children.get(c);
        }
        curr.word = word;
    }
    
    public static void main(String[] args) {
    	String[] a = {"aaa"};
    	char[][] table = {{'a', 'a'}};
    	
    	findWords(table, a);
    }
}
