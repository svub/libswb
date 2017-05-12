package libswb;

import java.util.LinkedList;

public class Queue {
	  private LinkedList<ResourceNode> list = new LinkedList<ResourceNode>();

	  public void enqueue(ResourceNode item) {
	    list.addLast(item);
	  }

	  public ResourceNode dequeue() {
	    return list.poll();
	  }

	  public boolean hasItems() {
	    return !list.isEmpty();
	  }

	  public int size() {
	    return list.size();
	  }

}
