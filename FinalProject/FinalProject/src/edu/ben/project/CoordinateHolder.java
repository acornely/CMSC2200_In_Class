package edu.ben.project;

/**
 * Functions as temporary storage for move coordinates. Contains a Group object,
 * a current row and column, and a destination row and column. Is used when a
 * Group must move from one location to another. Current and destination
 * coordinates can be stored to be accessed when required by a button listener
 * or other scenario.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class CoordinateHolder {

	private int currentRow;
	private int currentCol;
	private int destinationRow;
	private int destinationCol;
	private Group currentGroup;

	/**
	 * Constructor. Sets initial values to zero and currentGroup to null.
	 */
	public CoordinateHolder() {
		reset();
	}

	/**
	 * Resets all values to 0 and currentGroup to null.
	 */
	public void reset() {
		currentRow = 0;
		currentCol = 0;
		destinationRow = 0;
		destinationCol = 0;
		currentGroup = null;
	}

	/**
	 * Getter. Returns current row.
	 * 
	 * @return The current row.
	 */
	public int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Setter. Sets value of current row.
	 * 
	 * @param currentRow
	 *            Value to be used.
	 */
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	/**
	 * Getter. Returns current column.
	 * 
	 * @return The current column.
	 */
	public int getCurrentCol() {
		return currentCol;
	}

	/**
	 * Setter. Sets value of current column.
	 * 
	 * @param currentCol
	 *            Value to be used.
	 */
	public void setCurrentCol(int currentCol) {
		this.currentCol = currentCol;
	}

	/**
	 * Getter. Returns destination row.
	 * 
	 * @return The destination row.
	 */
	public int getDestinationRow() {
		return destinationRow;
	}

	/**
	 * Setter. Sets value of destination row.
	 * 
	 * @param destinationRow
	 *            The value to be used.
	 */
	public void setDestinationRow(int destinationRow) {
		this.destinationRow = destinationRow;
	}

	/**
	 * Getter. Returns destination column.
	 * 
	 * @return The destination column.
	 */
	public int getDestinationCol() {
		return destinationCol;
	}

	/**
	 * Setter. Sets value of destination column.
	 * 
	 * @param destinationCol
	 *            The value to be used.
	 */
	public void setDestinationCol(int destinationCol) {
		this.destinationCol = destinationCol;
	}

	/**
	 * Getter. Returns the current Group object in use.
	 * 
	 * @return The assigned Group object or null.
	 */
	public Group getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * Setter. Sets the currentGroup to a new Group or null.
	 * 
	 * @param currentGroup
	 *            A Group object, value may be null.
	 */
	public void setCurrentGroup(Group currentGroup) {
		this.currentGroup = currentGroup;
	}

	/**
	 * Creates and returns a formatted String.
	 * 
	 * @return A formatted string representation of the current state of
	 *         CoordinateHolder.
	 */
	@Override
	public String toString() {
		return currentGroup + ", Current Row: " + currentRow + ", Current Col: " + currentCol + ", Destination Row: "
				+ destinationRow + ", Destination Col: " + destinationCol;
	}

}
