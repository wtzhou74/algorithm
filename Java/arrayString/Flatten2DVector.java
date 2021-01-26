package arrayString;
//
//Design and implement an iterator to flatten a 2d vector. It should support the following operations: next and hasNext.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Example:
//
//Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
//
//iterator.next(); // return 1
//iterator.next(); // return 2
//iterator.next(); // return 3
//iterator.hasNext(); // return true
//iterator.hasNext(); // return true
//iterator.next(); // return 4
//iterator.hasNext(); // return false
//
// Follow up:
// As an added challenge, try to code it using only iterators in C++ or iterators in Java.
public class Flatten2DVector {

	////////////Leveraging Iterator
	
	Iterator<Integer> iter;
	public Flatten2DVector(int[][] v) {
	// reference: https://stackify.com/streams-guide-java-8/
		this.iter = Arrays.asList(v).stream()
				.flatMapToInt(i -> Arrays.stream(i))
				.iterator();
	}
	public int next() {
		return iter.next();
	}
	public boolean hasNext() {
		return iter.hasNext();
	}
	
	
	// Iterator sol2
//  private Queue<Iterator<Integer>> queue;
//  private Iterator<Integer> iter;
//  public Flatten2DVector(int[][] v) {
//      this.queue = new LinkedList<>();
//      flatten(v, queue);
//  }
//  
//  private void flatten(int[][] v, Queue<Iterator<Integer>> queue) {
//	  iter = null;
//      for (int[] array : v) {
//    	  if (array.length > 0) {
//    		  List<Integer> list = new ArrayList<>();
//	          for (int item : array) {
//	              list.add(item);
//	          }
//
//	    	  queue.offer(list.iterator());
//	      }
//      }
//  }
//  
//  public int next() {
//      if ((iter == null || !iter.hasNext()) && !queue.isEmpty()) {
//    	  iter = queue.poll();
//      }
//      return iter.next();
//  }
//  
//  public boolean hasNext() {
//      return (iter != null && iter.hasNext()) || !queue.isEmpty();
//  }
	
	
	
	////////////Applying Queue
//	private Queue<Integer> queue;
//    public Flatten2DVector(int[][] v) {
//        this.queue = new LinkedList<>();
//        flatten(v, queue);
//    }
//    
//    private void flatten(int[][] v, Queue<Integer> queue) {
//        for (int[] array : v) {
//            for (int item : array) {
//                queue.offer(item);
//            }
//        }
//    }
//    
//    public int next() {
//        return queue.poll();
//    }
//    
//    public boolean hasNext() {
//        return queue.size() > 0 ? true : false;
//    }
	
}
