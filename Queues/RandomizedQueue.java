import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at 
 * random from items in the data structure. 
 * 
 * @param <Item> the type of element managed by the Deque
 * @author kristinpeterson 
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] items;
	private int size;
	
	/**
	 * Default Constructor: instantiates an empty RandomizedQueue
	 */
	public RandomizedQueue()
	{
		if(items instanceof Object[])
			items = (Item[]) new Object[1];
        size = 0;  
	}
   
	/**
	 * Checks if the RandomizedQueue is empty
	 * 
	 * @return true if the RandomizedQueue is empty
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
	
    // 
	/**
	 * Return the number of items on the RandomizedQueue
	 * 
	 * @return the number of items in the RandomizedQueue
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Add the given item to the end of the RandomizedQueue
	 * 
	 * @param item the item to add to the end of the RandomizedQueue
	 * @throws NullPointerException if the given item to add is null
	 */
	public void enqueue(Item item) throws NullPointerException
	{
		if(item==null) throw new NullPointerException("Cannot add null Item");
		if(items.length==size){
			resize(items.length*2);
			items[size]=item;
			size++;
		} else {
			items[size]=item;
			size++;
		}
	}

	/**
	 * Delete and return a random item
	 * 
	 * @return the random item that has been deleted from the RandomizedQueue
	 * @throws NoSuchElementException if the RandomizedQueue is empty
	 */
	public Item dequeue() throws NoSuchElementException
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("Cannot dequeue from an empty RandomizedQueue");
        int randomIndex = StdRandom.uniform(size);        
        Item returnItem = items[randomIndex];
        if(size-1==randomIndex){
            items[randomIndex]=null;                    
        } else {
            items[randomIndex]=items[size-1];
            items[size-1]=null;
        }        
        if (size==items.length/4){
            resize(items.length/2);
        }
        size--;
        return returnItem;
	}

	/**
	 * Return (but do not delete) a random item from the RandomizedQueue
	 * 
	 * @return a random item from the RandomizedQueue
	 * @throws NoSuchElementException if the RandomizedQueue is empty
	 */
	public Item sample() throws NoSuchElementException
	{
		if(isEmpty()) throw new java.util.NoSuchElementException("Cannot sample an empty RandomizedQueue");
		return items[StdRandom.uniform(size)];
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator()
	{
		return new RandomQueueIterator();
	}
	
	/*
	 * PRIVATE INNER CLASSES
	 */
	
	// Iterator for RandomizedQueue
	private class RandomQueueIterator implements Iterator<Item>
	{
		private int i = 0;
		private int[] indices;
		
		public RandomQueueIterator()
		{
			indices = new int[size];
			for(int j=0;j<indices.length;j++)
			{
				indices[j] = j;
			}
			StdRandom.shuffle(indices);
		}
		
		public boolean hasNext()
		{
			return i<size;
		}
		
		public Item next() throws java.util.NoSuchElementException
		{
			if(!hasNext()) throw new java.util.NoSuchElementException("No more items in iteration.");
			return items[indices[i++]];
		}
		
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException("remove() is not supported");
		}
	}
	
	// Helper method to resize array of items to the given capacity
    private void resize(int capacity)
    {        
    	Item[] copy = (Item[]) new Object[capacity];
    	for(int i=0;i<size;i++){
    		copy[i] = items[i];
    	}
    	items = copy;
    }
}

