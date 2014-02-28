import java.util.Iterator;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization 
 * of a stack and a queue that supports inserting and removing items from 
 * either the front or the back of the data structure. 
 * 
 * @param <Item> the type of element managed by the Deque
 * @author kristinpeterson
 */
public class Deque<Item> implements Iterable<Item> {

	private Node first, last;
	private int size;
	
	/**
	 * Default Constructor: instantiates an empty Deque
	 */
	public Deque()
	{
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Checks if the the Deque is empty.
	 * 
	 * @return true if Deque is empty
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}

	
	/**
	 * Return the number of items in the Deque
	 * 
	 * @return the number of Items in the Deque
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Insert the given item at the front of the Deque
	 * 
	 * @param item the item to add to the front of the Deque
	 * @throws NullPointerException if attempting to add null item
	 */
	public void addFirst(Item item) throws NullPointerException
	{
		if(item==null) throw new NullPointerException("Cannot add null item.");
		Node newFirst = new Node();
		if(first==null){
			newFirst.next = null;
	    } else {
	    	newFirst.next = first;
	    	first.prev = newFirst;
	    }
	    newFirst.prev = null;
	    first = newFirst;
	    first.item = item;
	    size++;
	    if(last==null){
	    	last = first;
	    }
	}

	/**
	 * Insert the given item at the end of the Deque
	 * 
	 * @param item the item to add to the end of the Deque
	 * @throws NullPointerException if attempting to add null item
	 */
	public void addLast(Item item) throws NullPointerException
	{
		if(item==null) throw new NullPointerException("Cannot add null item.");
		Node newLast = new Node();
		if(last==null)
		{
			newLast.prev = null;
		} else {
			newLast.prev = last;
			last.next = newLast;
		}
		newLast.next = null;
		last = newLast;
		last.item = item;
		size++;
		if(first==null)
		{
			first = last;
		}
	}

	/**
	 * Delete and return the item at the front of the Deque
	 * 
	 * @return the item that was just removed from the front of the Deque
	 * @throws java.util.NoSuchElementException if attempting to remove item from empty Deque
	 */
	public Item removeFirst() throws java.util.NoSuchElementException
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("Cannot remove element from an empty Deque.");
		Item returnItem = first.item;
		if(size==1)
		{
			first = null;
			last = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		size--;
		return returnItem;
	}

	/**
	 * Delete and return the item at the end of the Deque
	 * 
	 * @return the item that was just removed from the end of the Deque
	 * @throws java.util.NoSuchElementException if attempting to remove item from empty Deque
	 */
	public Item removeLast() throws java.util.NoSuchElementException
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("Cannot remove element from an empty Deque.");
		Item returnItem = last.item;
		if(size==1)
		{
			first = null;
			last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		size--;
		return returnItem;
	}

	/**
	 * Return an iterator over items in order from front to end
	 * 
	 * @return the Deque iterator
	 * @throws UnsupportedOperationException if attempting to use remove() method in the iterator
	 * @throws java.util.NoSuchElementException if next() is called when the Deque is empty
	 */
	public Iterator<Item> iterator() throws UnsupportedOperationException, java.util.NoSuchElementException
	{
		return new DequeIterator();
	}
	
	/*
	 * PRIVATE INNER CLASSES
	 */
	
	// Node object, represents a single node in the Deque
	private class Node
	{
		Node prev = null;
		Item item = null;
		Node next = null;
		
		/*
		 * Default constructor.
		 */
		public Node()
		{
		}
		
		/*
		 * Constructor to make easy copies of nodes.
		 */
		public Node(Node that)
		{
			this.prev = that.prev;
			this.item = that.item;
			this.next = that.next;
		}
	}
	
	// Deque iterator
	private class DequeIterator implements Iterator<Item>
	{
		private Node current = first;
		
		public boolean hasNext()
		{
			return current != null;
		}
		
		public Item next() throws java.util.NoSuchElementException
		{
			if(!hasNext()) throw new java.util.NoSuchElementException("No more items in iteration.");
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException("remove() is not supported");
		}
	}
}
