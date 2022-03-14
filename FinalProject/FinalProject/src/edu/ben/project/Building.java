package edu.ben.project;

/**
 * Holds coordinates for a single building.
 * 
 * @author Colom Boyle
 * @version 1
 */
public class Building {

	public static final int HOUSE = 0;
	public static final int ARMY_BASE = 1;
	public static final int HOME_BASE = 2;

	private int row;
	private int col;
	private int type;
	private QuestItem item = null;

	/**
	 * Constructor. Sets values of type, row, and col from passed in values.
	 * 
	 * @param type
	 *            Building type, use type values.
	 * @param row
	 *            Row value.
	 * @param col
	 *            Col value.
	 */
	public Building(int type, int row, int col) {
		this.type = type;
		this.row = row;
		this.col = col;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns row.
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
	 * @return Return column.
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setter.
	 * 
	 * @param col
	 *            New column value.
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * Getter.
	 * 
	 * @return Return type value.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Setter.
	 * 
	 * @param type
	 *            New type value.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns formatted string representing type, row, and col values.
	 */
	@Override
	public String toString() {
		String buildingType;
		if (type == HOUSE) {
			buildingType = "HOUSE";
		} else if (type == ARMY_BASE) {
			buildingType = "ARMY BASE";
		} else {
			buildingType = "HOME BASE";
		}
		return "(Type: " + buildingType + ", Row: " + row + ", Col: " + col + ")";
	}

	/**
	 * Returns the current quest item.
	 * 
	 * @return The current quest item.
	 */
	public QuestItem getItem() {
		return item;
	}

	/**
	 * Sets a new quest item.
	 * 
	 * @param item
	 *            The new quest item.
	 */
	public void setItem(QuestItem item) {
		this.item = item;
	}

}
