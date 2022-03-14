package edu.ben.project;

/**
 * Represents an individual node in A* search algorithm. Stores data for row and
 * column values. Also stores cost values to be used in algorithm.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class Node implements Comparable<Node> {

	private int row;
	private int col;
	private double gScore;
	private double hScore;
	private double fScore;
	private Node parent;

	/**
	 * Constructor. Sets the initial values of all variables. Score values are
	 * set to zero, parent is set to null.
	 * 
	 * @param numCols
	 *            The number of columns in the array to be searched.
	 * @param row
	 *            The row the Node represents.
	 * @param col
	 *            The column the Node represents.
	 */
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
		gScore = 0;
		hScore = 0;
		fScore = 0;
		parent = null;
	}

	/**
	 * Checks if passed in Node has the same row and column values as the
	 * calling Node.
	 * 
	 * @param n
	 *            The Node object to be compared.
	 * @return Returns true if passed in Node has the same row and column values
	 *         as the calling Node, else returns false. Also Returns false if
	 *         parameter is null.
	 */
	public boolean isSameRowAndCol(Node n) {
		if (n != null) {
			return row == n.getRow() && col == n.getCol();
		} else {
			return false;
		}
	}

	/**
	 * Checks if the passed in row and column values match the row and column
	 * values of the calling Node.
	 * 
	 * @param row
	 *            Row to check.
	 * @param col
	 *            Column to check.
	 * @return Returns true if row and column values match parameters, else
	 *         false.
	 */
	public boolean isSameRowAndCol(int row, int col) {
		return this.row == row && this.col == col;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns row value.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter.
	 * 
	 * @param row
	 *            New row value.
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns column value.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setter.
	 * 
	 * @param col
	 *            new column value.
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns g cost
	 */
	public double getgScore() {
		return gScore;
	}

	/**
	 * Setter.
	 * 
	 * @param gScore
	 *            New g cost value.
	 */
	public void setgScore(double gScore) {
		this.gScore = gScore;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns h score value.
	 */
	public double gethScore() {
		return hScore;
	}

	/**
	 * Setter.
	 * 
	 * @param hScore
	 *            New h cost value.
	 */
	public void sethScore(double hScore) {
		this.hScore = hScore;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns f cost value
	 */
	public double getfScore() {
		return fScore;
	}

	/**
	 * Setter.
	 * 
	 * @param fScore
	 *            New f cost value.
	 */
	public void setfScore(double fScore) {
		this.fScore = fScore;
	}

	/**
	 * Getter for parent Node.
	 * 
	 * @return Returns the parent Node, value may be null.
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Setter for parent Node.
	 * 
	 * @param parent
	 *            The new parent Node.
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * 
	 * Creates a formatted string indicating current Node data.
	 * 
	 * @return A formatted String representing Node data.
	 */
	@Override
	public String toString() {
		return "(Row: " + row + " Col: " + col + ", gScore: " + gScore + ", hScore: " + hScore + ", fScore: " + fScore
				+ ")";
	}

	/**
	 * Determines whether the passed in Node is less than, equal to, or greater
	 * than in value than the current Node.
	 */
	@Override
	public int compareTo(Node node) {

		if (fScore < node.getfScore()) {
			return -1;
		} else if (fScore > node.getfScore()) {
			return 1;
		} else {
			return 0;
		}

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			Node node = (Node) o;
			return row == node.getRow() && col == node.getCol();
		} else {
			return false;
		}
	}

}
