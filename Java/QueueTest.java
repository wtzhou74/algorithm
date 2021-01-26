import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {

	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(3);
		queue.poll();
		queue.contains(3);
		queue.clear();
		
	}
}
