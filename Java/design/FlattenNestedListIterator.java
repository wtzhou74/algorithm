package design;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FlattenNestedListIterator implements Iterator<Integer> {

	private Queue<Integer> queue = new LinkedList<>();
    
    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        // flatten it and move the intergers to the queue
        for (NestedInteger nestedInt : nestedList) {
            if (nestedInt.isInteger()) {
                queue.add(nestedInt.getInteger());
            } else {
            	// current method is a CONSTRUCTOR method, so, we do the recursive by defining a new method
                recursiveFlat(nestedInt.getList());
            }
        }
    }
    
    public void recursiveFlat (List<NestedInteger> nestedList) {
        for (NestedInteger nestedInt : nestedList) {
            if (nestedInt.isInteger())
                queue.add(nestedInt.getInteger());
            else 
                recursiveFlat(nestedInt.getList());
        }
    }

    @Override
    public Integer next() {
        return hasNext() ? queue.poll() : null;
    }

    @Override
    public boolean hasNext() {
        if (queue == null || queue.size() == 0) return false;
        return true;
    }
}
