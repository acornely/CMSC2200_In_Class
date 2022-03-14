package edu.ben.project;

/**
 * Stores the coordinates of a quest item. Stores row, col, and item type.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class QuestItem {

	private int row;
	private int col;
	private String itemType;

	/**
	 * Sets the initial values of row and col.
	 * 
	 * @param row
	 *            Row coordinate.
	 * @param col
	 *            Column coordinate.
	 */
	public QuestItem(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Sets the initial values of row, col, and itemType.
	 * 
	 * @param row
	 *            Row coordinate.
	 * @param col
	 *            Column coordinate.
	 * @param itemType
	 *            Item type string.
	 */
	public QuestItem(int row, int col, String itemType) {
		this.row = row;
		this.col = col;
		this.itemType = itemType;
	}

	/**
	 * Getter for row value.
	 * 
	 * @return Current row value.
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
	 * @return Current column value.
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
	 * Gets the current item type string
	 * 
	 * @return Returns a string representing the current item type.
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * Sets a new item type string.
	 * 
	 * @param itemType
	 *            A new item type string.
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * Returns a string representing current row and col values.
	 * 
	 * @return A formatted string for row and col values.
	 */
	@Override
	public String toString() {
		return "Type: " + itemType + ", Row: " + row + ", Col: " + col;
	}
}
