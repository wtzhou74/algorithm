package queueAndStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots:
 *  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, 
 * the wheels of the lock will stop turning and you will be unable to open it.
 * Given a target representing the value of the wheels that will unlock the lock, 
 * return the minimum total number of turns required to open the lock, or -1 if it is impossible.
 * */
public class OpenLock {

	public static int openLockSol(String[] deadends, String target)
	{
		Set<String> visited = new HashSet<String>();
		// HashSet provide more efficient contains()
		Set<String> deadStrs = new HashSet<String>(Arrays.asList(deadends));
		
		Queue<String> queue = new LinkedList<String>();
		
		// initial status
		queue.offer("0000");
		visited.add("0000");//level 0
		int level = 0;
		while(!queue.isEmpty())
		{
			int size = queue.size();
			while(size > 0)
			{
				String current = queue.poll();
				if (current.equals(target)) return level;
				
				// DONOT check visited.contains(current); it is always false except the initial one
				if (deadStrs.contains(current)) 
				{
					size--;
					continue;
				}
				for(int i = 0; i < 4; i++)
				{
					StringBuilder str = new StringBuilder(current);
					char c = str.charAt(i);
					// go up
					String lock1 = str.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + str.substring(i + 1);
					// go down
					String t = "" + '9';
					String temp = str.substring(0, i) + (c == '0' ? '9' : c - '0' - 1);//cause wrong number 57 since '9' will be converted to ASCII 57. !!!!!
					String lock2 = str.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + str.substring(i + 1);
					
					if (!visited.contains(lock1) && !deadStrs.contains(lock1))
					{
						queue.offer(lock1);
						visited.add(lock1);
					}
					// Because it is a circular wheels rotating freely and wrap around.
					if (!visited.contains(lock2) && !deadStrs.contains(lock2))
					{
						queue.offer(lock2);
						visited.add(lock2);
					}
				}
				 size--;
			}
			
			// level 1: 1000/9000: 0100/0900: 0010/0090: 0001/0009
			level++;
		}
		
		return -1;
		
	}
	
	public int openLock(String[] deadends, String target) {
        Queue<String> q = new LinkedList<>();
        Set<String> deads = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        q.offer("0000");
        visited.add("0000");
        int level = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            while(size != 0) {
                String s = q.poll();
                if(deads.contains(s)) {
                    size --;
                    continue;
                }
                if(s.equals(target)) return level;
                StringBuilder sb = new StringBuilder(s);
                for(int i = 0; i < 4; i ++) {
                    char c = sb.charAt(i);
                    String s1 = sb.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + sb.substring(i + 1);
                    String s2 = sb.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + sb.substring(i + 1);
                    if(!visited.contains(s1) && !deads.contains(s1)) {
                        q.offer(s1);
                        visited.add(s1);
                    }
                    if(!visited.contains(s2) && !deads.contains(s2)) {
                        q.offer(s2);
                        visited.add(s2);
                    }
                }
                size --;
            }
            level ++;
        }
        return -1;
    }
}
