/**
 * Write a client program Subset.java that takes a command-line integer k;
 * reads in a sequence of N strings from standard input using StdIn.readString();
 * and prints out exactly k of them, uniformly at random. 
 * Each item from the sequence can be printed out at most once. You may assume that 
 * k >= 0 and no greater than the number of string N on standard input.
 * 
 * @author kristinpeterson
 */
public class Subset {

	public static void main(String[] args)
	{
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		while(!StdIn.isEmpty())
		{
			rq.enqueue(StdIn.readString());
		}
		for(int i=1;i<=k;i++)
		{
			StdOut.println(rq.dequeue());
		}
	}
}
