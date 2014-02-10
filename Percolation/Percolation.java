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
	private int N;  // number of nodes in grid = (NxN)
	private boolean[] open;  // true == open, false == blocked
	private int virtualTop;

	/**
	 * Initializes an empty Percolation grid of N by N WeightedQuickUnionFindUF objects
	 * with 2 additional nodes designated as a virtual top and virtual bottom.  These
	 * virtual nodes allow for more efficient determination of percolation status.
	 * <p>
	 * All nodes are initially set to be "blocked," as recorded in the open array of 
	 * boolean values
	 * @param N the number of nodes in a single row/column
	 */
	public Percolation(int N)
	{
		this.N = N;
		grid = new WeightedQuickUnionUF((N*N)+2);
		open = new boolean[(N*N)+2];
		virtualTop = xyTo1D(N, N) + 1;
	}
	
	/**
	 * Opens a site located at (row i, column j), if it is not already opened.
	 * i & j are 1-indexed. 
	 * @param i the row of the node to open
	 * @param j the column of the node to open
	 */
	public void open(int i, int j)
	{	
		if (isOpen(i, j))
			return;
		validateIndices(i,j);
		// opens site at (i, j)
		open[xyTo1D(i,j)] = true;
		connectWithSurroundingOpenSites(i, j);
	}
	
	/**
	 * Checks if a given node is open.
	 * @param i the row of the node being checked
	 * @param j the column of the node being checked
	 * @return true if the node is open
	 */
	public boolean isOpen(int i, int j)
	{	
		validateIndices(i, j);
		return open[xyTo1D(i, j)];
	}
	
	/**
	 * Checks if a given node is full.
	 * @param i the row of the node being checked
	 * @param j the column of the node being checked
	 * @return true if the node is full
	 */
	public boolean isFull(int i, int j)
	{	
		validateIndices(i, j);
		return grid.connected(virtualTop, xyTo1D(i, j));	
	}
	
	/**
	 * Checks if the Percolation object percolates, by checking if the virtual top node
	 * is connected to the virtual bottom node. 
	 * @return true if the system does percolate
	 */
	public boolean percolates()
	{
		for(int i = 1; i <= N; i++)
		{
			if(grid.connected(virtualTop, xyTo1D(N, i)))
				return true;
		}
		return false;
	}
	
	//	PRIVATE HELPER METHODS
	
	//	Returns WeightedQuickUnionUF object index (0-indexed) based on given 2d grid coordinates (1-indexed)
	//	@param i the row of the given node
	//	@param j the column of the given node
	private int xyTo1D(int i, int j)
	{
		validateIndices(i,j);
		return (int) (( N * (i - 1) + j) - 1);  
	}
	
	//	Validates a given nodes row/column values.
	//	@param i the row of the node to validate
	//	@param j the column of the node to validate
	//	@throws	IndexOutOfBoundsException if the node is not within the Percolation object grid
	private void validateIndices(int i, int j) throws IndexOutOfBoundsException
	{
		if (!isValid(i, j))
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
		
		return i >= 0 && j >= 0 && i < N && j < N;
	}
	
	//	Connects node at given row/column with valid surrounding open sites (left/right/top/bottom).
	//	If the node is in the first row it is connected to the virtual top node,
	//	if the node is in the last row it is connected to the virtual bottom node.
	//	@param i the row of the node to connect
	private void connectWithSurroundingOpenSites(int i, int j)
	{
		int index = xyTo1D(i, j);
		
		if (i == 1)
			grid.union(virtualTop, index);  // connecting to virtualTop if index is in first row
		if (isValid(i, j-1) && isOpen(i, j-1))
			grid.union(xyTo1D(i,j-1), index);  // connecting index to left node if open
		if (isValid(i, j+1) && isOpen(i, j+1))
			grid.union(xyTo1D(i,j+1), index);  // connecting index to right node if open
		if (isValid(i-1, j) && isOpen(i-1, j))
			grid.union(xyTo1D(i-1,j), index);  // connecting index to top node if open
		if (isValid(i+1, j) && isOpen(i+1, j))
			grid.union(xyTo1D(i+1, j), index);  // connecting index to bottom node if open
	}
}
