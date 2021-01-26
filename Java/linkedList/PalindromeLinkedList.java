package linkedList;

import java.util.ArrayList;
import java.util.List;

public class PalindromeLinkedList {
	public boolean isPalindrom(List<Integer> list) {
        if (list.size() <= 1) return true;
        int i = 0, j = list.size() - 1;
        while (i < j) {
            if (list.get(i).equals(list.get(j))) return false;
            i++;
            j--;
        }
        return true;
    }
	public static void main(String[] args) {
		List<Integer> c = new ArrayList<>();
		c.add(-129);
		c.add(-129);
		new PalindromeLinkedList().isPalindrom(c);
	}
}
