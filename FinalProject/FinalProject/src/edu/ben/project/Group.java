package edu.ben.project;

/**
 * A unit to be stored on the game board. Team and ZombieHorde are subclasses of
 * Group. Stores row, column and speed multiplier. An ID value can be set to
 * uniquely identify each Group.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class Group {

	private int row;
	private int col;
	private int speedMultiplier;

	/**
	 * Constructor. Sets initial speed multiplier value.
	 */
	public Group() {
		speedMultiplier = 1;
	}

	/**
	 * Updates current position values to values entered in parameters.
	 * 
	 * @param row
	 *            New row location.
	 * @param col
	 *            New col location.
	 */
	public void updatePosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Getter for current row value.
	 * 
	 * @return Returns current row value.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter for row value.
	 * 
	 * @param row
	 *            A new row value.
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Getter for current column value.
	 * 
	 * @return The current column value.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setter for column value.
	 * 
	 * @param col
	 *            A new column value.
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * Getter for current speed multiplier.
	 * 
	 * @return The current speed multiplier value.
	 */
	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	/**
	 * Setter for speed multiplier.
	 * 
	 * @param speedMultiplier
	 *            The new speed multiplier value.
	 */
	public void setSpeedMultiplier(int speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

}
