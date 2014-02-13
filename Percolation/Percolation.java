/**
 * The Percolation class builds an N x N grid of WeightedQuickUnionUF objects
 * in order to demonstrate a percolation system. All nodes in the N x N grid are 
 * "blocked" when a Percolation object is initialized.  As nodes in the grid
 * are "opened," if they are connected to the top row they are also considered to 
 * be "full".  
 * <p>
 * Example: percolation of water being poured through the top of a 
 * porous object, or electrical currents running through a grid of conductors/insulators.
 * <p>
 * @author kristinpeterson
 *
 */
public class Percolation {
	
	private WeightedQuickUnionUF grid;
	private WeightedQuickUnionUF fullness;
	private int N;						// number of nodes in grid = (NxN)
	private boolean[] open;				// true == open, false == blocked
	private int virtualTop;
	private int virtualBottom;
	   
	/**
	 * Initializes an empty Percolation grid of N by N WeightedQuickUnionFindUF objects
	 * with 2 additional nodes designated as a virtual top and virtual bottom.  These
	 * virtual nodes allow for more efficient determination of percolation status.
	 * <p>
	 * All nodes are initially set to be "blocked," as recorded in the open array of 
	 * boolean values
	 * @param N the number of nodes in a single row/column
	 * @throws IllegalArgumentException if N <= 0
	 */
	public Percolation(int N) throws IllegalArgumentException
	{
		if(N<=0) throw new IllegalArgumentException("N cannot be less than or equal to 0.");
		this.N = N;
		grid = new WeightedQuickUnionUF(N*N+2);
		fullness = new WeightedQuickUnionUF(N*N+1);
		open = new boolean[N*N];
		virtualTop = xyTo1D(N,N) + 1;
		virtualBottom = xyTo1D(N,N) + 2;
	}
	
	/**
	 * Opens a site located at (row i, column j), if it is not already opened.
	 * i & j are 1-indexed. 
	 * @param i the row of the node to open
	 * @param j the column of the node to open
	 */
	public void open(int i, int j)
	{	
		if (isOpen(i,j))
			return;
		validateIndices(i,j);
		// opens site at (i, j)
		open[xyTo1D(i,j)] = true;
		connectWithSurroundingOpenSites(i,j);
	}
	
	/**
	 * Checks if a given node is open.
	 * @param i the row of the node being checked
	 * @param j the column of the node being checked
	 * @return true if the node is open
	 */
	public boolean isOpen(int i, int j)
	{	
		validateIndices(i,j);
		return open[xyTo1D(i,j)] == true;
	}
	
	/**
	 * Checks if a given node is full.
	 * @param i the row of the node being checked
	 * @param j the column of the node being checked
	 * @return true if the node is full
	 */
	public boolean isFull(int i, int j)
	{	
		validateIndices(i,j);
		return fullness.connected(xyTo1D(i,j), virtualTop);	
	}
	
	/**
	 * Checks if the Percolation object percolates, by checking if the virtual top node
	 * is connected to the virtual bottom node. 
	 * @return true if the system does percolate
	 */
	public boolean percolates()
	{
		return grid.connected(virtualTop, virtualBottom);
	}
	
	//	PRIVATE HELPER METHODS
	
	//	Returns WeightedQuickUnionUF object index (0-indexed) based on given 2d grid coordinates (1-indexed)
	//	@param i the row of the given node
	//	@param j the column of the given node
	private int xyTo1D(int i, int j)
	{
		validateIndices(i,j);
		return (int) ((N*(i-1)+j)-1);  
	}
	
	//	Validates a given nodes row/column values.
	//	@param i the row of the node to validate
	//	@param j the column of the node to validate
	//	@throws	IndexOutOfBoundsException if the node is not within the Percolation object grid
	private void validateIndices(int i, int j) throws IndexOutOfBoundsException
	{
		if(!isValid(i,j))
			throw new IndexOutOfBoundsException("Invalid indices provided.");
	}
	
	//	Checks if a given node row/column pair is within the Percolation object grid.
	//	@param i the row of the node to validate
	//	@param j the column of the node to validate
	//	@return true if indices are valid
	private boolean isValid(int i, int j)
	{
		// convert i & j to from 1-indexed row/col to array index values
		i -= 1;
		j -= 1;
		return i>=0 && j>=0 && i<N && j<N;
	}
	
	//	Connects node at given row/column with valid surrounding open sites (left/right/top/bottom).
	//	If the node is in the first row it is connected to the virtual top node,
	//	if the node is in the last row it is connected to the virtual bottom node.
	//	@param i the row of the node to connect
	private void connectWithSurroundingOpenSites(int i, int j)
	{
		int index = xyTo1D(i,j);
		
		if(i == 1)
		{
			// connecting node to virtualTop if it is in first row
			// in grid & fullness UF objects
			grid.union(virtualTop, index);
			fullness.union(virtualTop, index);
		}
		if(i == N)
		{
			// connecting index node to virtualBottom if it is in last row
			// ONLY for grid UF object
			// Not connecting for fullness UF object to prevent backwash
			grid.union(virtualBottom, index);
		}
		if(isValid(i,j-1) && isOpen(i,j-1))
		{
			// connecting index node to it's left node if open (for grid & fullness)
			grid.union(xyTo1D(i,j-1), index);
			fullness.union(xyTo1D(i,j-1), index);
		}
		if(isValid(i,j+1) && isOpen(i,j+1))
		{
			// connecting index node to right node if open (for grid & fullness)
			grid.union(xyTo1D(i,j+1), index);
			fullness.union(xyTo1D(i,j+1),index);
		}
		if(isValid(i-1,j) && isOpen(i-1,j))
		{
			// connecting index node to top node if open (for grid & fullness)
			grid.union(xyTo1D(i-1,j), index);
			fullness.union(xyTo1D(i-1,j),index);
		}
		if(isValid(i+1,j) && isOpen(i+1,j))
		{
			// connecting index node to bottom node if open (for grid & fullness)
			grid.union(xyTo1D(i+1,j), index);
			fullness.union(xyTo1D(i+1,j),index);
		}
	}
}
